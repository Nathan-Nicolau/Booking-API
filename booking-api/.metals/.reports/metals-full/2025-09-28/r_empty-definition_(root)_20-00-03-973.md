error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala:model/data/Quartos.tabela.
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/ReservaService.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol model/data/Quartos.tabela.
empty definition using fallback
non-local guesses:

offset: 1081
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
    val query = Quartos.t@@abela.filter { quarto =>
        (quarto.dataDisponibilidadeReserva.isAfter(dataCalculoDisponibilidade)) || (quarto.)

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

  val queryQuarto = Quartos.tabela.filter(_.idQuarto === novaReserva.getIdQuarto()).result.headOption

  db.run(queryQuarto).flatMap {
    case Some(quarto) =>
      if (dataInicioInformada.isBefore(quarto.dataDisponibilidadeReserva) || dataInicioInformada.isEqual(quarto.dataDisponibilidadeReserva)) {
        Future.successful(
          RetornoNovaReservaDTO(0,false,"Data de início da reserva inválida",null)
        )
      } else {
        val novaReservaData = ReservaQuarto(
          idQuarto = novaReserva.getIdQuarto(),
          idHospede = novaReserva.getIdHospede(),
          dataInicioReserva = dataInicioInformada,
          dataFimReserva = novaReserva.getDataFimReservaAjustada()
        )

        val insertAction = ReservasQuarto.tabela.insertOrUpdate(novaReservaData)

        db.run(insertAction).flatMap { _ =>
          val updateAction = Quartos.tabela
            .filter(_.idQuarto === novaReserva.getIdQuarto())
            .map(_.dataDisponibilidadeReserva)
            .update(calcularDataDisponibilidade(novaReserva.getDataFimReservaAjustada()))

          db.run(updateAction).map { _ =>
            RetornoNovaReservaDTO(novaReservaData.numeroReserva,true,"Reserva criada com sucesso", Utils.getDataHoraStringFormatada(novaReservaData.dataFimReserva))
          }.recover {
            case ex: Throwable =>
              RetornoNovaReservaDTO(novaReservaData.numeroReserva,false,s"Houve um erro ao atualizar o quarto: ${ex.getMessage}",Utils.getDataHoraStringFormatada(novaReservaData.dataFimReserva))
          }
        }.recover {
          case ex: Throwable =>
            RetornoNovaReservaDTO(novaReservaData.numeroReserva,false,s"Houve um erro ao criar a reserva: ${ex.getMessage}",Utils.getDataHoraStringFormatada(novaReservaData.dataFimReserva))
        }
      }

    case None =>
      Future.successful(
        RetornoNovaReservaDTO(0,false,"Quarto não encontrado",null)
      )
  }
}


}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 