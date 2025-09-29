package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.data.Quartos
import java.util.concurrent.Executors
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import services.ReservaService
import model.dto.NovoQuartoInventario
import model.dto.ReservaPeriodo
import play.api.libs.json._
import model.dto.NovaReservaQuartoDTO

@Singleton
class ReservaController @Inject()(val cc: ControllerComponents, val reservaService: ReservaService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  //GET quartos
  def getTodosQuartos() = Action.async {
    reservaService.listarTodos().map { quartos =>
      Ok(Json.toJson(quartos))
    }
  }

  //GET quartos-disponiveis
  def getQuartosDisponiveis() = Action.async {
    reservaService.getQuartosDisponiveis().map { quartos =>
      Ok(Json.toJson(quartos))
     }
  }

  // POST /adicionar-quarto-inventario (recebe um Body do tipo NovoQuartoInventario)
  def adicionarQuartoInventario = Action.async(parse.json) { request =>
    request.body.validate[NovoQuartoInventario].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "JSON inválido"))),
      novoQuarto => reservaService.adicionarQuartoInventario(novoQuarto).map { retorno =>
        Ok(Json.toJson("" -> retorno.mensagem))
      }
    )
  }

  //PUT remover-quarto-inventario
  def removerQuartoInventario(idQuarto: Int) = Action.async {
    reservaService.removerQuartoInventario(idQuarto).map { retorno =>
      Ok(Json.toJson("" -> retorno.mensagem))
    }
  }

  //PUT retornar-quarto-inventario
  def retornarQuartoAoInventario(idQuarto: Int) = Action.async {
    reservaService.retornarQuartoAoInventário(idQuarto).map { retorno =>
      Ok(Json.toJson("" -> retorno.mensagem))
    }
  }

  // GET /reservas-periodo (recebe um body do tipo ReservaPeriodo)
  def getReservasPeriodo = Action.async(parse.json) { request =>
    request.body.validate[ReservaPeriodo].fold(
      errors => Future.successful(BadRequest(Json.obj("Erro" -> "JSON inválido"))),
      periodo => reservaService.getReservasPeriodo(periodo).map { retorno =>
        Ok(Json.toJson(retorno))
      }
    )
  }

  //POST /nova-reserva (recebe um body do tipo NovaReservaPeriodo)
  def criarNovaReserva = Action.async(parse.json) { request =>
    request.body.validate[NovaReservaQuartoDTO].fold(
      errors => Future.successful(BadRequest(Json.toJson("Erro" -> "Requisição inválida"))),
      novaReserva => reservaService.criarNovaReserva(novaReserva).map { retorno =>
        Ok(Json.toJson(retorno))
      }
    )
  }

}
