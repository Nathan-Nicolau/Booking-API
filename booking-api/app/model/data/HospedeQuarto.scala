import java.time.LocalDateTime
import slick.lifted.ProvenShape
import EntidadeHospedeQuarto.TabelaHospedeQuarto

final case class HospedeQuarto(
  idHospede: Int,
  codigoHospede: Option[Int] = None,
  nomeCompleto: String,
  documento: String,
  hospedado: Boolean,
  dataUltimaHospedagem: LocalDateTime,
  dataCadastro: LocalDateTime
)

object EntidadeHospedeQuarto {

  import slick.jdbc.PostgresProfile.api._

  class TabelaHospedeQuarto(tag: Tag) extends Table[HospedeQuarto](tag,"hospede_quarto") {

    def idHospede = column[Int]("id_hospede", O.PrimaryKey, O.AutoInc)
    def codigoHospede = column[Int]("codigo_hospede", O.AutoInc)
    def nomeCompleto = column[String]("nome_completo")
    def documento = column[String]("documento")
    def hospedado = column[Boolean]("hospedado")
    def dataUltimaHospedagem = column[LocalDateTime]("data_ultima_hospedagem")
    def dataCadastro = column[LocalDateTime]("data_cadastro")

    override def * = (idHospede,codigoHospede.?,nomeCompleto, documento, hospedado,dataUltimaHospedagem, dataCadastro).mapTo[HospedeQuarto]

  }

}

object HospedesQuarto {

  import slick.jdbc.PostgresProfile.api._

  val tabela = TableQuery[TabelaHospedeQuarto]

}
