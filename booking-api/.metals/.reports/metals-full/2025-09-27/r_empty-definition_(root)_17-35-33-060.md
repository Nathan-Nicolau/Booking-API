error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/HomeController.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/HomeController.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -javax/inject/Quarto.
	 -javax/inject/Quarto#
	 -javax/inject/Quarto().
	 -play/api/Quarto.
	 -play/api/Quarto#
	 -play/api/Quarto().
	 -play/api/mvc/Quarto.
	 -play/api/mvc/Quarto#
	 -play/api/mvc/Quarto().
	 -Quarto.
	 -Quarto#
	 -Quarto().
	 -scala/Predef.Quarto.
	 -scala/Predef.Quarto#
	 -scala/Predef.Quarto().
offset: 345
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/HomeController.scala
text:
```scala
package controllers

import javax.inject._
import play.api._
import play.api.mvc._



@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getQuartos(): List: Quar@@to = {
    
  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 