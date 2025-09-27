package  services

import javax.inject._
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{ExecutionContext, Future}
import model.EntidadeQuarto._
import model.Quarto
import model.Quartos

@Singleton
class QuartoService @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.profile.api._

  private val db = dbConfig.db

  def listarTodos(): Future[Seq[Quarto]] = {
    db.run(Quartos.tabela.result)
  }
}
