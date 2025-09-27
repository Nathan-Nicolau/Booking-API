file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
empty definition using pc, found symbol in pc: 
semanticdb not found
empty definition using fallback
non-local guesses:
	 -javax/inject/Quarto.
	 -javax/inject/Quarto#
	 -javax/inject/Quarto().
	 -play/api/mvc/Quarto.
	 -play/api/mvc/Quarto#
	 -play/api/mvc/Quarto().
	 -slick/jdbc/PostgresProfile.api.Quarto.
	 -slick/jdbc/PostgresProfile.api.Quarto#
	 -slick/jdbc/PostgresProfile.api.Quarto().
	 -model/Quarto.
	 -model/Quarto#
	 -model/Quarto().
	 -Quarto.
	 -Quarto#
	 -Quarto().
	 -scala/Predef.Quarto.
	 -scala/Predef.Quarto#
	 -scala/Predef.Quarto().
offset: 385
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/QuartoController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model._

@Singleton
class QuartoController @Inject()(val cc: ControllerComponents,val db: Database)(implicit ec: ExecutionContext) extends BaseController() {


  def getTodosQuartos() = Action.async(

    val quarto =  Qua@@rto()
    val query = Quarto.tabela.result

  )

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 