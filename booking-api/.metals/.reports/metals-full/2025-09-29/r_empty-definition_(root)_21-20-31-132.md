error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/value.
	 -javax/inject/value#
	 -javax/inject/value().
	 -model/data/EntidadeQuarto.value.
	 -model/data/EntidadeQuarto.value#
	 -model/data/EntidadeQuarto.value().
	 -dbConfig/profile/api/value.
	 -dbConfig/profile/api/value#
	 -dbConfig/profile/api/value().
	 -value.
	 -value#
	 -value().
	 -scala/Predef.value.
	 -scala/Predef.value#
	 -scala/Predef.value().
offset: 1735
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
        case Success(value@@) => Retorno(false,"Quarto cadastrado com sucesso")
        case Failure(exception) => Retorno(true,s"Erro ao adicionar o quarto ao inventário: ${exception.getMessage()}")
      }
    Future.successful(retorno)

  }

  def removerQuartoInventario(idQuarto: Int): Future[Retorno] = {

  val dataAtual = LocalDateTime.now()
  //Consulta se tem alguma reserva para o quarto antes de tentar remove ele
  val reservaQuarto = ReservasQuarto.tabela.filter(_.dataFimReserva >= dataAtual)

  db.run(reservaQuarto.result).flatMap { reservas =>
    if (reservas.nonEmpty) {
      Future.successful(
        Retorno(false, "Não será possível remover esse quarto do inventário, pois ele está reservado")
      )
    } else {
      val queryQuarto = Quartos.tabela
        .filter(_.idQuarto === idQuarto)
        .map(_.statusInventario)
        .update("INDISPONÍVEL")

      db.run(queryQuarto).map { _ =>
        Retorno(true, "Quarto removido do inventário com sucesso!")
      }.recover {
        case ex: Exception =>
          Retorno(false, s"Erro ao remover quarto do inventário: ${ex.getMessage}")
      }
    }
  }
}

  def retornarQuartoAoInventario(idQuarto: Int): Future[Retorno] = {

    val quartoParaRetorno = Quartos.tabela.filter(_.idQuarto === idQuarto)
      .map(_.statusInventario)
      .update("OK")

    // val retornoUdate = Try(db.run(quartoParaRetorno))
    // val retorno = retornoUdate match {
    //   case Success(value) => Retorno(true,"Quarto retornado ao inventário com sucesso")
    //   case Failure(exception) => Retorno(false,s"Erro ao retornar quarto ao inventário: ${exception.getMessage()}")
    // }

    db.run(quartoParaRetorno).flatMap { _ =>
        Future.successful(Retorno(true,"Quarto retornado ao inventário com sucesso"))
    }.recover {
      case ex: Throwable =>
        Retorno(false,s"Erro ao retornar quarto ao inventário: ${ex.getMessage()}")
     }

  }

  def getReservasPeriodo(reservaPeriodo: ReservaPeriodo): Future[Seq[ReservaQuarto]] = {

    if(reservaPeriodo.datasPeriodoValidas()) {
       val query = ReservasQuarto.tabela.filter { reserva =>
          (reserva.dataInicioReserva >= reservaPeriodo.getDataInicioConvertida()) ||
          (reserva.dataFimReserva <= reservaPeriodo.getDataFimConvertida())
        }
        db.run(query.result).flatMap { retorno =>
          Future.successful(retorno)
        }.recover {
          case ex: Throwable =>
            List.empty
         }
    } else {
      Future.successful(List.empty)
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
                .map((quartoAtualizacao) => (quartoAtualizacao.dataDisponibilidadeReserva, quartoAtualizacao.statusOcupacao))
                .update(calcularDataDisponibilidade(dataFimInformada), true)

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


#### Short summary: 

empty definition using pc, found symbol in pc: 