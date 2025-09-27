import java.time.LocalDateTime

case class HospedeQuarto(
  idHospede: Int,
  codigoHospede: Int,
  nomeCompleto: String,
  documento: String,
  hospedado: Boolean,
  dataUltimaHospedagem: LocalDateTime,
  dataCadastro: LocalDateTime
)
