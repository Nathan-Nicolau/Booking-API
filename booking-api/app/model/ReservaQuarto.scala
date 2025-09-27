import java.time.LocalDateTime

case class ReservaQuarto(
  idReservaQuarto: Int,
  idQuarto: Int,
  idHospede: Int,
  numeroReserva: Int,
  dataInicioReserva: LocalDateTime,
  dataFimReserva: LocalDateTime,
  dataMaximaCancelamento: LocalDateTime
)
