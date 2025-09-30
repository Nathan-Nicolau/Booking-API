package model.data

import java.time.LocalDate
import java.time.LocalDateTime
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.relational.RelationalProfile.ColumnOption
import EntidadeQuarto.TabelaQuarto
import play.api.libs.json._

final case class Quarto(
  idQuarto: Option[Int] = None,
  numeroQuarto: String = "",
  andar: Int = 0,
  tipo: String = "",
  statusInventario: String = "",
  dataUltimaReserva: LocalDateTime = LocalDateTime.MIN,
  dataDisponibilidadeReserva: LocalDateTime = LocalDateTime.MIN
)

object EntidadeQuarto {

  import slick.jdbc.PostgresProfile.api._

  class TabelaQuarto(tag: Tag) extends Table[Quarto](tag,"quarto") {

    def idQuarto = column[Int]("id_quarto",O.PrimaryKey, O.AutoInc)
    def numeroQuarto = column[String]("numero_quarto")
    def andar = column[Int]("andar")
    def tipo = column[String]("tipo")
    def statusInventario = column[String]("status_inventario")
    def dataUltimaReserva = column[LocalDateTime]("data_ultima_reserva")
    def dataDisponibilidadeReserva = column[LocalDateTime]("data_disponibilidade_reserva")

    //Aqui é para barrar a existência de quartos com mesmo número no mesmo andar, afinal na prática isso não ocorre
    def idxUnicoAndarNumero = index("idx_andar_numero_unico", (andar, numeroQuarto), unique = true)

    override def * = (idQuarto.?,numeroQuarto,andar,tipo,statusInventario,dataUltimaReserva,dataDisponibilidadeReserva).mapTo[Quarto]

   }

}

object Quartos {

  import slick.jdbc.PostgresProfile.api._

  val tabela = TableQuery[TabelaQuarto]

}

object Quarto {

  implicit val quartoFormat: OFormat[Quarto] = Json.format[Quarto]

}


