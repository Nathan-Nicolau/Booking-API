package model

import java.time.LocalDate
import java.time.LocalDateTime
import slick.lifted.Tag
import slick.lifted.ProvenShape
import slick.relational.RelationalProfile.ColumnOption
import EntidadeQuarto.TabelaQuarto

final case class Quarto(
  idQuarto: Int,
  numeroQuarto: String,
  andar: Int,
  tipo: String,
  statusOcupacao: Boolean,
  statusInventario: String,
  dataUltimaReserva: LocalDateTime,
  dataDisponibilidadeReserva: LocalDateTime
)

object EntidadeQuarto {

  import slick.jdbc.PostgresProfile.api._

  class TabelaQuarto(tag: Tag) extends Table[Quarto](tag,"quarto") {

    def idQuarto = column[Int]("id_quarto",O.PrimaryKey, O.AutoInc)
    def numeroQuarto = column[String]("numero_quarto")
    def andar = column[Int]("andar")
    def tipo = column[String]("tipo")
    def statusOcupacao = column[Boolean]("status_ocupacao")
    def statusInventario = column[String]("status_inventario")
    def dataUltimaReserva = column[LocalDateTime]("data_ultima_reserva")
    def dataDisponibilidadeReserva = column[LocalDateTime]("data_disponibilidade_reserva")

    override def * = (idQuarto,numeroQuarto,andar,tipo,statusOcupacao,statusInventario,dataUltimaReserva,dataDisponibilidadeReserva).mapTo[Quarto]

   }

}

object Quartos {

  import slick.jdbc.PostgresProfile.api._

  val tabela = TableQuery[TabelaQuarto]

}


