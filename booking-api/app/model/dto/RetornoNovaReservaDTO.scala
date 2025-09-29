package model.dto
import play.api.libs.json._

final case class RetornoNovaReservaDTO(
  numeroReserva: Option[Int] = None,
  reservadoComSucesso: Boolean = false,
  mensagem: String = "",
  dataFimReserva: String = ""
)

object RetornoNovaReservaDTO { 

  implicit val quartoFormat: OFormat[RetornoNovaReservaDTO] = Json.format[RetornoNovaReservaDTO]

}
