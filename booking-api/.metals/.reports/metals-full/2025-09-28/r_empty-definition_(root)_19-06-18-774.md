error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/RetornoNovaReservaDTO.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/RetornoNovaReservaDTO.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -play/api/libs/json/Reserva#
	 -Reserva#
	 -scala/Predef.Reserva#
offset: 264
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/RetornoNovaReservaDTO.scala
text:
```scala
package model.dto
import play.api.libs.json._

final case class RetornoNovaReservaDTO(
  numeroReserva: Int,
  reservadoComSucesso: Boolean,
  mensagem: String,
  dataFimReserva: String
)

object RetornoNovaReservaDTO { 

  implicit val quartoFormat: OFormat[Retor@@noNovaReservaDTO] = Json.format[Quarto]

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 