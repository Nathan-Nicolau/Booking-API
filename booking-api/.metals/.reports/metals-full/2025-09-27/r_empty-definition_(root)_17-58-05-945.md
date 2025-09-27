file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 365
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class QuartoController @Inject(cc: ControllerComponents, db: Database): (implicit ec: ExecutionContext) extends BaseController(cc) {

  def getTodosQuartos() = Action.async {

    val query = Quart@@o.tabela.result;
    val quartos = db.run(query).map { quarto -> 
      quarto.
    }

  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 