error id: 7B24F233620AC68017BC6B6C3469744D
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala
### scala.reflect.internal.Types$TypeError: illegal cyclic reference involving trait API

occurred in the presentation compiler.



action parameters:
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala
text:
```scala
package services

import javax.inject._
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}
import model.data.EntidadeQuarto._
import model.data.Quarto
import model.data.Quartos
import java.time.LocalDateTime
import model.data.ReservaQuarto
import model.data.ReservasQuarto
import model.dto.NovoQuartoInventario
import model.data.Retorno
import scala.util.Try
import scala.util.Failure
import scala.util.Success
import model.dto.NovaReservaQuartoDTO
import model.dto.RetornoNovaReservaDTO
import utils.Utils
import model.dto.ReservaPeriodo

@Singleton
class ReservaService @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._

  private val db = dbConfig.db

  def listarTodos(): Future[Seq[Quarto]] = {
    db.run(Quartos.tabela.result)
  }

  def getQuartosDisponiveis(): Future[Seq[Quarto]] = {
    val dataCalculoDisponibilidade = LocalDateTime.now()

    val query = Quartos.tabela.filter { quarto =>
      (quarto.dataDisponibilidadeReserva <= dataCalculoDisponibilidade) &&
      (quarto.statusOcupacao === false)
    }

    val quartosDisponiveis = db.run(query.result)
    return quartosDisponiveis
  }

  def adicionarQuartoInventario(novoQuartoInventario: NovoQuartoInventario): Future[Retorno] = { 
    val novoQuarto = Quarto(numeroQuarto = novoQuartoInventario.numeroQuarto, andar = novoQuartoInventario.andar, tipo = novoQuartoInventario.tipo)
    val insert = Quartos.tabela.insertOrUpdate(novoQuarto)
    val retornoInsert = Try(db.run(insert))
    val retorno = retornoInsert match {
        case Success(value) => Retorno(false,"Quarto cadastrado com sucesso")
        case Failure(exception) => Retorno(true,s"Erro ao adicionar o quarto ao inventário: ${exception.getMessage()}")
      }
    return Future.successful(retorno)
  }

  def removerQuartoInventario(idQuarto: Int): Future[Retorno] = {
    val queryQuarto = Quartos.tabela.filter(_.idQuarto === idQuarto)
    .map(_.statusInventario)
    .update("INDISPONÍVEL")

    val retornoUpdate = Try(db.run(queryQuarto))
    val retorno = retornoUpdate match {
      case Success(value) => Retorno(true,"Quarto removido do inventário")
      case Failure(exception) => Retorno(false,s"Erro ao remover quarto do inventário: ${exception.getMessage()}")
    }
    return Future.successful(retorno)
  }

  def retornarQuartoAoInventário(idQuarto: Int): Future[Retorno] = {

    val quartoParaRetorno = Quartos.tabela.filter(_.idQuarto === idQuarto)
      .map(_.statusInventario)
      .update("OK")

    val retornoUdate = Try(db.run(quartoParaRetorno))
    val retorno = retornoUdate match {
      case Success(value) => Retorno(true,"Quarto retornoad ao inventário com sucesso")
      case Failure(exception) => Retorno(false,s"Erro ao retornar quarto ao inventário: ${exception.getMessage()}")
    }

    return Future.successful(retorno)
  }

  def getReservasPeriodo(reservaPeriodo: ReservaPeriodo): Future[Seq[ReservaQuarto]] = {
    if(reservaPeriodo.datasPeriodoValidas()) {
       val query = ReservasQuarto.tabela.filter { reserva =>
          (reserva.dataInicioReserva >= reservaPeriodo.getDataInicioConvertida()) ||
          (reserva.dataFimReserva <= reservaPeriodo.getDataFimConvertida())
        }
        val retornoReservas = Try(db.run(query.result)) match {
          case Success(value) => return value
          case Failure(exception) => return null
        }
        return Future.successful(retornoReservas)
     } else {
      return Future.successful(List.empty)
    }
  }

  private def calcularDataDisponibilidade(dataFimReserva: LocalDateTime): LocalDateTime = {
    val dataDisponibilidade = dataFimReserva.plusHours(4)
    return dataDisponibilidade
  }

  def criarNovaReserva(novaReserva: NovaReservaQuartoDTO): Future[RetornoNovaReservaDTO] = {

  val dataInicioInformada = novaReserva.getDataInicioReservadaAjustada()
  val dataFimInformada = novaReserva.getDataFimReservaAjustada()

  val queryQuarto = Quartos.tabela
    .filter(_.idQuarto === novaReserva.getIdQuarto())
    .result
    .headOption

  val queryReservasQuarto = ReservasQuarto.tabela.filter { reserva =>
    (reserva.idQuarto === novaReserva.getIdQuarto()) &&
    (reserva.dataInicioReserva < dataFimInformada) &&
    (reserva.dataFimReserva > dataInicioInformada)
  }.result

  db.run(queryQuarto).flatMap {
    case Some(quarto) =>
      if (dataInicioInformada.isBefore(quarto.dataDisponibilidadeReserva) ||
          dataInicioInformada.isEqual(quarto.dataDisponibilidadeReserva)) {
        Future.successful(
          RetornoNovaReservaDTO(
            reservadoComSucesso = false,
            mensagem = s"Data de reserva é inválida! Só há disponibilidade para: ${Utils.getDataHoraStringFormatada(quarto.dataDisponibilidadeReserva)}",
            dataFimReserva = ""
          )
        )
      } else {
        db.run(queryReservasQuarto).flatMap { reservasExistentes =>
          if (reservasExistentes.nonEmpty) {
            Future.successful(
              RetornoNovaReservaDTO(
                reservadoComSucesso = false,
                mensagem = "Já existe uma reserva para esse quarto nesse período.",
                dataFimReserva = ""
              )
            )
          } else {
            val insertAction =
              ReservasQuarto.tabela
                .map(r => (r.idQuarto, r.idHospede, r.dataInicioReserva, r.dataFimReserva))
                .returning(ReservasQuarto.tabela.map(_.numeroReserva))
                .into { case ((idQ, idH, di, df), numGerado) =>
                  ReservaQuarto(None, idQ, idH, Some(numGerado), di, df)
                }

            db.run(insertAction += (novaReserva.getIdQuarto(), novaReserva.getIdHospede(), dataInicioInformada, dataFimInformada)).flatMap { reservaCriada =>
              val updateAction = Quartos.tabela
                .filter(_.idQuarto === novaReserva.getIdQuarto())
                .map((campos) => (campos.))
                .update(calcularDataDisponibilidade(dataFimInformada))

              db.run(updateAction).map { _ =>
                RetornoNovaReservaDTO(
                  reservaCriada.numeroReserva,
                  reservadoComSucesso = true,
                  mensagem = "Reserva criada com sucesso",
                  dataFimReserva = Utils.getDataHoraStringFormatada(reservaCriada.dataFimReserva)
                )
              }.recover {
                case ex: Throwable =>
                  RetornoNovaReservaDTO(
                    Option(0),
                    reservadoComSucesso = false,
                    mensagem = s"Houve um erro ao atualizar o quarto: ${ex.getMessage}",
                    dataFimReserva = Utils.getDataHoraStringFormatada(reservaCriada.dataFimReserva)
                  )
              }
            }.recover {
              case ex: Throwable =>
                RetornoNovaReservaDTO(
                  Option(0),
                  reservadoComSucesso = false,
                  mensagem = s"Houve um erro ao criar a reserva: ${ex.getMessage}",
                  dataFimReserva = Utils.getDataHoraStringFormatada(dataFimInformada)
                )
            }
          }
        }
      }

    case None =>
      Future.successful(
        RetornoNovaReservaDTO(
          reservadoComSucesso = false,
          mensagem = "Erro ao criar uma nova reserva",
          dataFimReserva = ""
        )
      )
    }
  }

}

```


presentation compiler configuration:
Scala version: 2.13.16
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-_GoKSJTCQCmaN12Vep7xJw== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.11.0\semanticdb-javac-0.11.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.16\scala-library-2.13.16.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\twirl\twirl-api_2.13\2.0.9\twirl-api_2.13-2.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-server_2.13\3.0.9\play-server_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-logback_2.13\3.0.9\play-logback_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-pekko-http-server_2.13\3.0.9\play-pekko-http-server_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-filters-helpers_2.13\3.0.9\play-filters-helpers_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-guice_2.13\3.0.9\play-guice_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-slick_2.13\5.2.0\play-slick_2.13-5.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-slick-evolutions_2.13\5.2.0\play-slick-evolutions_2.13-5.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\postgresql\postgresql\42.7.3\postgresql-42.7.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-xml_2.13\2.2.0\scala-xml_2.13-2.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play_2.13\3.0.9\play_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\ch\qos\logback\logback-classic\1.5.18\logback-classic-1.5.18.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-streams_2.13\3.0.9\play-streams_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-http-core_2.13\1.0.1\pekko-http-core_2.13-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\inject\guice\6.0.0\guice-6.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\inject\extensions\guice-assistedinject\6.0.0\guice-assistedinject-6.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\slick\slick_2.13\3.4.1\slick_2.13-3.4.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\slick\slick-hikaricp_2.13\3.4.1\slick-hikaricp_2.13-3.4.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play_2.13\2.9.0\play_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-jdbc-api_2.13\2.9.0\play-jdbc-api_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-jdbc-evolutions_2.13\2.9.0\play-jdbc-evolutions_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\checkerframework\checker-qual\3.42.0\checker-qual-3.42.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-build-link\3.0.9\play-build-link-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-configuration_2.13\3.0.9\play-configuration_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\slf4j-api\2.0.17\slf4j-api-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\jul-to-slf4j\2.0.17\jul-to-slf4j-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\jcl-over-slf4j\2.0.17\jcl-over-slf4j-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-actor_2.13\1.0.3\pekko-actor_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-actor-typed_2.13\1.0.3\pekko-actor-typed_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-slf4j_2.13\1.0.3\pekko-slf4j_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-serialization-jackson_2.13\1.0.3\pekko-serialization-jackson_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-core\2.14.3\jackson-core-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-annotations\2.14.3\jackson-annotations-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.14.3\jackson-datatype-jdk8-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.14.3\jackson-datatype-jsr310-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-databind\2.14.3\jackson-databind-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\dataformat\jackson-dataformat-cbor\2.14.3\jackson-dataformat-cbor-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\module\jackson-module-parameter-names\2.14.3\jackson-module-parameter-names-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\module\jackson-module-scala_2.13\2.14.3\jackson-module-scala_2.13-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-api\0.11.5\jjwt-api-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-impl\0.11.5\jjwt-impl-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-jackson\0.11.5\jjwt-jackson-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-json_2.13\3.0.5\play-json_2.13-3.0.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\guava\32.1.3-jre\guava-32.1.3-jre.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\javax\inject\javax.inject\1\javax.inject-1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\ssl-config-core_2.13\0.6.1\ssl-config-core_2.13-0.6.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-parser-combinators_2.13\1.1.2\scala-parser-combinators_2.13-1.1.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\ch\qos\logback\logback-core\1.5.18\logback-core-1.5.18.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\reactivestreams\reactive-streams\1.0.4\reactive-streams-1.0.4.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-stream_2.13\1.0.3\pekko-stream_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-parsing_2.13\1.0.1\pekko-parsing_2.13-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\parboiled\parboiled_2.13\2.5.0\parboiled_2.13-2.5.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\jakarta\inject\jakarta.inject-api\2.0.1\jakarta.inject-api-2.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\aopalliance\aopalliance\1.0\aopalliance-1.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\errorprone\error_prone_annotations\2.21.1\error_prone_annotations-2.21.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\config\1.4.5\config-1.4.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-collection-compat_2.13\2.8.1\scala-collection-compat_2.13-2.8.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\zaxxer\HikariCP\4.0.3\HikariCP-4.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-build-link\2.9.0\play-build-link-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-streams_2.13\2.9.0\play-streams_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-configuration_2.13\2.9.0\play-configuration_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\twirl-api_2.13\1.6.2\twirl-api_2.13-1.6.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-actor_2.13\2.6.21\akka-actor_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-actor-typed_2.13\2.6.21\akka-actor-typed_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-slf4j_2.13\2.6.21\akka-slf4j_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-serialization-jackson_2.13\2.6.21\akka-serialization-jackson_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-json_2.13\2.10.2\play-json_2.13-2.10.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-exceptions\3.0.9\play-exceptions-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\lz4\lz4-java\1.8.0\lz4-java-1.8.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\thoughtworks\paranamer\paranamer\2.8\paranamer-2.8.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-functional_2.13\3.0.5\play-functional_2.13-3.0.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.16\scala-reflect-2.13.16.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\j2objc\j2objc-annotations\2.8\j2objc-annotations-2.8.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-protobuf-v3_2.13\1.0.3\pekko-protobuf-v3_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-exceptions\2.9.0\play-exceptions-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-stream_2.13\2.6.21\akka-stream_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-java8-compat_2.13\1.0.0\scala-java8-compat_2.13-1.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-functional_2.13\2.10.2\play-functional_2.13-2.10.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-protobuf-v3_2.13\2.6.21\akka-protobuf-v3_2.13-2.6.21.jar [exists ]
Options:
-deprecation -unchecked -encoding utf8 -Yrangepos -Xplugin-require:semanticdb




#### Error stacktrace:

```

```
#### Short summary: 

scala.reflect.internal.Types$TypeError: illegal cyclic reference involving trait API