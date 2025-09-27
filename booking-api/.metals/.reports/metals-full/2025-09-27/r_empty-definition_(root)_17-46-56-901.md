error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/Quarto.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/Quarto.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -slick/jdbc/PostgresProfile.api.Quarto#
	 -Quarto#
	 -scala/Predef.Quarto#
offset: 981
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/Quarto.scala
text:
```scala
import java.time.LocalDate
import java.time.LocalDateTime
import slick.jdbc.PostgresProfile.api._

case class Quarto(
  idQuarto: Int,
  numeroQuarto: String,
  andar: Int,
  statusOcupacao: String,
  dataUltimaReserva: LocalDate,
  dataDisponibilidadeReserva: LocalDateTime
)

object EntidadeQuarto {

  import slick.jdbc.PostgresProfile.api._

  class TabelaQuarto(tag: Tag) extends Table[Quarto](tag,"QUARTO") {

    def idQuarto = column[Int]("ID_QUARTO",O.PrimaryKey, O.AutoInc)
    def numeroQuarto = column[String]("NUMERO_QUARTO")
    def andar = column[Int]("ANDAR")
    def statusOcupacao = column[Boolean]("STATUS_OCUPACAO")
    def dataUltimaReserva = column[LocalDateTime]("DATA_ULTIMA_RESERVA")
    def dataDisponibilidadeReserva = column[LocalDateTime]("DATA_DISPONIBILIDADE_RESERVA")

    override def * = (idQuarto,numeroQuarto,andar,statusOcupacao,dataUltimaReserva,dataDisponibilidadeReserva).mapTo[Quarto]

   }

}

object Quarto { 
  val quarto = TableQuery[Qu@@arto]
}



```


#### Short summary: 

empty definition using pc, found symbol in pc: 