error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala:
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -Quarto.
	 -Quarto#
	 -Quarto().
	 -scala/Predef.Quarto.
	 -scala/Predef.Quarto#
	 -scala/Predef.Quarto().
offset: 594
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala
text:
```scala
package model.dto

import java.time.LocalDateTime
import utils.Utils


final case class NovaReservaQuartoDTO(
  private val idQuarto: Int,
  private val idHospede: Int,
  private val dataInicio: String,
  private val dataFim: String
) {

  def getIdQuarto(): Int = idQuarto

  def getIdHospede(): Int = idHospede

  def getDataInicioReservadaAjustada(): LocalDateTime = {
    return Utils.convertarStringParaDataHora(dataInicio)
  }

  def getDataFimReservaAjustada(): LocalDateTime = {
    return Utils.convertarStringParaDataHora(dataFim)
  }

}

object implicit val quartoFormat: OFormat[Qua@@rto] = Json.format[Quarto] { 

  implicit val quartoFormat: OFormat[Quarto] = Json.format[Quarto]

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 