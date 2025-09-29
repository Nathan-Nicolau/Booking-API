package model.data

import java.time.LocalDateTime
import slick.lifted.ProvenShape
import EntidadeReservaQuarto.TabelaReservaQuarto
import play.api.libs.json._

final case class ReservaQuarto(
  idReservaQuarto: Option[Int] = None,
  idQuarto: Int = 0,
  idHospede: Int = 0,
  numeroReserva: Option[Int] = None,
  dataInicioReserva: LocalDateTime = LocalDateTime.MIN,
  dataFimReserva: LocalDateTime = LocalDateTime.MIN,
  dataMaximaCancelamento: LocalDateTime = LocalDateTime.MIN,
)

object EntidadeReservaQuarto {

  import slick.jdbc.PostgresProfile.api._

  class TabelaReservaQuarto(tag: Tag) extends Table[ReservaQuarto](tag,"reserva_quarto") { 

    def idReservaQuarto = column[Int]("id_reserva_quarto",O.PrimaryKey, O.AutoInc)
    def idQuarto = column[Int]("id_quarto")
    def idHospede = column[Int]("id_hospede")
    def numeroReserva = column[Int]("numero_reserva")
    def dataInicioReserva = column[LocalDateTime]("data_inicio_reserva")
    def dataFimReserva = column[LocalDateTime]("data_fim_reserva")
    def dataMaximaCancelamento = column[LocalDateTime]("data_maxima_cancelamento")

    override def * = (idReservaQuarto.?, idQuarto, idHospede,numeroReserva.?,dataInicioReserva,dataFimReserva, dataMaximaCancelamento).mapTo[ReservaQuarto]
  }

}

object ReservasQuarto {

  import slick.jdbc.PostgresProfile.api._

  val tabela = TableQuery[TabelaReservaQuarto]

}

object ReservaQuarto { 
  
  implicit val reservaFormat: OFormat[ReservaQuarto] = Json.format[ReservaQuarto]

}


