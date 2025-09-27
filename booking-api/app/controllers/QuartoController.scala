package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.Quartos
import java.util.concurrent.Executors
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import services.QuartoService

@Singleton
class QuartoController @Inject()(val cc: ControllerComponents, val quartoService: QuartoService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getTodosQuartos() = Action.async {
    quartoService.listarTodos().map { rooms =>
      Ok(rooms.mkString(", "))
    }
  }

}
