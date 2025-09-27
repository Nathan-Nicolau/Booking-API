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
offset: 174
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.Quart@@o

@Singleton
class QuartoController @Inject()(val cc: ControllerComponents,val db: Database)(implicit ec: ExecutionContext) extends BaseController() {


  def getTodosQuartos() = Action.async(

    val quarto =  Quarto()
    val query = Quarto.tabela.result

  )

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 