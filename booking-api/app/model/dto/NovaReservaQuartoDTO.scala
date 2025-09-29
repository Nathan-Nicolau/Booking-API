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
    return Utils.converterStringParaDataHora(dataInicio)
  }

  def getDataFimReservaAjustada(): LocalDateTime = {
    return Utils.converterStringParaDataHora(dataFim)
  }

}

object NovaReservaQuartoDTO {

  implicit val quartoFormat: OFormat[NovaReservaQuartoDTO] = Json.format[NovaReservaQuartoDTO]

}
