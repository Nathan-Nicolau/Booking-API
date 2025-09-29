package utils

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.util.{Failure, Success, Try}

object Utils {

  private val formatterDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  private val formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def converterDataHoraString(data: LocalDateTime): String = {
    Try(formatterDataHora.format(data)) match {
      case Success(value) => value
      case Failure(_) => LocalDateTime.now().format(formatterDataHora)
    }
  }

  def converterStringParaDataHora(dataString: String): LocalDateTime = {
    val tentativaDataHora = Try(LocalDateTime.parse(dataString, formatterDataHora))
    tentativaDataHora match {
      case Success(value) => value
      case Failure(_) =>
        Try(LocalDate.parse(dataString, formatterData).atStartOfDay()) match {
          case Success(value) => value
          case Failure(_) => LocalDateTime.now()
        }
    }
  }

  def getDataHoraStringFormatada(data: LocalDateTime): String = {
    Try(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))) match {
      case Success(value) => value
      case Failure(_) => LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    }
  }

}
