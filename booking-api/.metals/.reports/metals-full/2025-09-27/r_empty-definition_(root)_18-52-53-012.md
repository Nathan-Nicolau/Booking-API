error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/QuartoService.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/QuartoService.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb

found definition using fallback; symbol Inject
offset: 295
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/services/QuartoService.scala
text:
```scala
package services

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.JdbcProfile
import model.Quartos
import play.api.mvc._

@Singleton
class QuartoService @Inje@@ct()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val db = dbConfig.db

  def listarTodos(): String = Action.async {
    val query = Quartos.tabela.result
    db.run(query).map { quartos =>
      Ok(quartos.toList.toString())
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 