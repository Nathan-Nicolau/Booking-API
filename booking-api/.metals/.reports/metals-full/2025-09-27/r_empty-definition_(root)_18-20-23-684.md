file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/Quartos_.
	 -javax/inject/Quartos_#
	 -javax/inject/Quartos_().
	 -play/api/mvc/Quartos_.
	 -play/api/mvc/Quartos_#
	 -play/api/mvc/Quartos_().
	 -slick/jdbc/PostgresProfile.api.Quartos_.
	 -slick/jdbc/PostgresProfile.api.Quartos_#
	 -slick/jdbc/PostgresProfile.api.Quartos_().
	 -model/Quartos_.
	 -model/Quartos_#
	 -model/Quartos_().
	 -Quartos_.
	 -Quartos_#
	 -Quartos_().
	 -scala/Predef.Quartos_.
	 -scala/Predef.Quartos_#
	 -scala/Predef.Quartos_().
offset: 174
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.Quart@@os_


@Singleton
class QuartoController @Inject()(val cc: ControllerComponents,val db: Database)(implicit ec: ExecutionContext) extends BaseController() {


  def getTodosQuartos() = Action.async {

    val query = Quartos.tabela.result
    db.run(query).map { rooms =>
      Ok(rooms.mkString(", "))
    }

  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 