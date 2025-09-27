error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala:tabela
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found

found definition using fallback; symbol tabela
offset: 401
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import _empty_.Quartos


@Singleton
class QuartoController @Inject()(val cc: ControllerComponents,val db: Database)(implicit ec: ExecutionContext) extends BaseController() {


  def getTodosQuartos() = Action.async {

    val query = Quartos.tab@@ela.result
    db.run(query).map { rooms =>
      Ok(rooms.mkString(", "))
    }

  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 