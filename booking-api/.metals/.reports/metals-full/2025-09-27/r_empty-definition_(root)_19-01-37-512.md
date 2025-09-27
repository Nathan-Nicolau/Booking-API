error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/quartoService/listarTodos.
	 -javax/inject/quartoService/listarTodos#
	 -javax/inject/quartoService/listarTodos().
	 -play/api/mvc/quartoService/listarTodos.
	 -play/api/mvc/quartoService/listarTodos#
	 -play/api/mvc/quartoService/listarTodos().
	 -slick/jdbc/PostgresProfile.api.quartoService.listarTodos.
	 -slick/jdbc/PostgresProfile.api.quartoService.listarTodos#
	 -slick/jdbc/PostgresProfile.api.quartoService.listarTodos().
	 -quartoService/listarTodos.
	 -quartoService/listarTodos#
	 -quartoService/listarTodos().
	 -scala/Predef.quartoService.listarTodos.
	 -scala/Predef.quartoService.listarTodos#
	 -scala/Predef.quartoService.listarTodos().
offset: 551
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
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

  def getTodosQuartos() = {
    quartoService.listarTo@@dos()
  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 