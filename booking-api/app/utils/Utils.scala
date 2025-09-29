package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object Utils {

  private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def converterDataHoraString(data: LocalDateTime): String = {
    val dataReturn = Try(formatter.format(data)) match {
      case Success(value) => value.toString()
      case Failure(exception) => LocalDateTime.now().format(formatter)
     }
    return dataReturn

  }

  def converterStringParaDataHora(dataString: String): LocalDateTime = { 
    val localDateTimeReturn = Try(LocalDateTime.from(formatter.parse(dataString))) match {
      case Success(value) => value
      case Failure(exception) => LocalDateTime.now()
    }
    return localDateTimeReturn;
  }

  def getDataHoraStringFormatada(data: LocalDateTime): String = {
    val dataFormatada = Try(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
    return dataFormatada match {
      case Success(value) => value
      case Failure(exception) => LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    }
  }

}
