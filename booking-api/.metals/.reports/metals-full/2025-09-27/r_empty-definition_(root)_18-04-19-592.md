file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/Quarto.
	 -play/api/mvc/Quarto.
	 -slick/jdbc/PostgresProfile.api.Quarto.
	 -model/Quarto.
	 -Quarto.
	 -scala/Predef.Quarto.
offset: 382
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.Quarto

@Singleton
class QuartoController @Inject(cc: ControllerComponents, db: Database)(implicit ec: ExecutionContext) extends BaseController(cc) {

  def getTodosQuartos() = Action.async {

    val query = Quar@@to.tabela.result;
    val quartos = db.run(query).map { quarto -> 
      quarto.numeroQuarto
    }

    return quartos

  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 