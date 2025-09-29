error id: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala:utils/Utils.convertarStringParaDataHora().
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala
empty definition using pc, found symbol in pc: utils/Utils.convertarStringParaDataHora().
empty definition using semanticdb

found definition using fallback; symbol convertarStringParaDataHora
offset: 422
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/model/dto/NovaReservaQuartoDTO.scala
text:
```scala
package model.dto

import java.time.LocalDateTime
import utils.Utils
import play.api.libs.json._


final case class NovaReservaQuartoDTO(
  private val idQuarto: Int,
  private val idHospede: Int,
  private val dataInicio: String,
  private val dataFim: String
) {

  def getIdQuarto(): Int = idQuarto

  def getIdHospede(): Int = idHospede

  def getDataInicioReservadaAjustada(): LocalDateTime = {
    return Utils.conve@@rtarStringParaDataHora(dataInicio)
  }

  def getDataFimReservaAjustada(): LocalDateTime = {
    return Utils.convertarStringParaDataHora(dataFim)
  }

}

object NovaReservaQuartoDTO {

  implicit val quartoFormat: OFormat[NovaReservaQuartoDTO] = Json.format[NovaReservaQuartoDTO]

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: utils/Utils.convertarStringParaDataHora().