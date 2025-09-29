package model.dto

import java.time.LocalDateTime
import utils.Utils
import play.api.libs.json._

final case class ReservaPeriodo(
  val dataInicio: String,
  val dataFim: String
) {

  def getDataInicioConvertida(): LocalDateTime = {
    return Utils.converterStringParaDataHora(dataInicio)
  }

  def getDataFimConvertida(): LocalDateTime = {
    return Utils.converterStringParaDataHora(dataFim)
  }

  def datasPeriodoValidas(): Boolean = {
    return (dataInicio != null && !dataInicio.isEmpty() && !dataInicio.isBlank()) && (dataFim != null && !dataFim.isEmpty() && !dataFim.isBlank())
  }

}

object ReservaPeriodo {

  implicit val quartoFormat: OFormat[ReservaPeriodo] = Json.format[ReservaPeriodo]

}
