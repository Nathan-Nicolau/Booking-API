package model.dto
import play.api.libs.json._

final case class NovoQuartoInventario(
  numeroQuarto: String,
  andar: Int,
  tipo: String,
)

object NovoQuartoInventario {

  implicit val format: OFormat[NovoQuartoInventario] = Json.format[NovoQuartoInventario]

}
