error id: 9C73123981407A57B65680BDB743ADCB
file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/ReservaController.scala
### java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)

occurred in the presentation compiler.



action parameters:
uri: file:///C:/Users/Gamer/Documents/Teste/Booking-API/booking-api/app/controllers/ReservaController.scala
text:
```scala
package controllers

import javax.inject._
import play.api.mvc._
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import model.data.Quartos
import java.util.concurrent.Executors
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import services.ReservaService
import model.dto.NovoQuartoInventario
import model.dto.ReservaPeriodo
import play.api.libs.json._
import model.dto.NovaReservaQuartoDTO

@Singleton
class ReservaController @Inject()(val cc: ControllerComponents, val reservaService: ReservaService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  //GET quartos
  def getTodosQuartos() = Action.async {
    reservaService.listarTodos().map { quartos =>
      Ok(Json.toJson(quartos))
    }
  }

  //GET quartos-disponiveis
  def getQuartosDisponiveis() = Action.async {
    reservaService.getQuartosDisponiveis().map { quartos =>
      Ok(Json.toJson(quartos))
     }
  }

  // POST /adicionar-quarto-inventario (recebe um Body do tipo NovoQuartoInventario)
  def adicionarQuartoInventario = Action.async(parse.json) { request =>
    request.body.validate[NovoQuartoInventario].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "JSON inválido"))),
      novoQuarto => reservaService.adicionarQuartoInventario(novoQuarto).map { retorno =>
        Ok(Json.t("mensagem" -> retorno.mensagem))
      }
    )
  }

  //PUT remover-quarto-inventario
  def removerQuartoInventario(idQuarto: Int) = Action.async {
    reservaService.removerQuartoInventario(idQuarto).map { retorno =>
      Ok(Json.toJson("" -> retorno.mensagem))
    }
  }

  //PUT retornar-quarto-inventario
  def retornarQuartoAoInventario(idQuarto: Int) = Action.async {
    reservaService.retornarQuartoAoInventário(idQuarto).map { retorno =>
      Ok(Json.toJson("mensagem" -> retorno.mensagem))
    }
  }

  // GET /reservas-periodo (recebe um body do tipo ReservaPeriodo)
  def getReservasPeriodo = Action.async(parse.json) { request =>
    request.body.validate[ReservaPeriodo].fold(
      errors => Future.successful(BadRequest(Json.obj("Erro" -> "JSON inválido"))),
      periodo => reservaService.getReservasPeriodo(periodo).map { retorno =>
        Ok(Json.toJson(retorno))
      }
    )
  }

  //POST /nova-reserva (recebe um body do tipo NovaReservaPeriodo)
  def criarNovaReserva = Action.async(parse.json) { request =>
    request.body.validate[NovaReservaQuartoDTO].fold(
      errors => Future.successful(BadRequest(Json.toJson("Erro" -> "Requisição inválida"))),
      novaReserva => reservaService.criarNovaReserva(novaReserva).map { retorno =>
        Ok(Json.toJson(retorno))
      }
    )
  }

}

```


presentation compiler configuration:
Scala version: 2.13.16
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-xvK_wNJdQL-jAmCWU99NwA== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.11.0\semanticdb-javac-0.11.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.16\scala-library-2.13.16.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\twirl\twirl-api_2.13\2.0.9\twirl-api_2.13-2.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-server_2.13\3.0.9\play-server_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-logback_2.13\3.0.9\play-logback_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-pekko-http-server_2.13\3.0.9\play-pekko-http-server_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-filters-helpers_2.13\3.0.9\play-filters-helpers_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-guice_2.13\3.0.9\play-guice_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-slick_2.13\5.2.0\play-slick_2.13-5.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-slick-evolutions_2.13\5.2.0\play-slick-evolutions_2.13-5.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\postgresql\postgresql\42.7.3\postgresql-42.7.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-xml_2.13\2.2.0\scala-xml_2.13-2.2.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play_2.13\3.0.9\play_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\ch\qos\logback\logback-classic\1.5.18\logback-classic-1.5.18.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-streams_2.13\3.0.9\play-streams_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-http-core_2.13\1.0.1\pekko-http-core_2.13-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\inject\guice\6.0.0\guice-6.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\inject\extensions\guice-assistedinject\6.0.0\guice-assistedinject-6.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\slick\slick_2.13\3.4.1\slick_2.13-3.4.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\slick\slick-hikaricp_2.13\3.4.1\slick-hikaricp_2.13-3.4.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play_2.13\2.9.0\play_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-jdbc-api_2.13\2.9.0\play-jdbc-api_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-jdbc-evolutions_2.13\2.9.0\play-jdbc-evolutions_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\checkerframework\checker-qual\3.42.0\checker-qual-3.42.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-build-link\3.0.9\play-build-link-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-configuration_2.13\3.0.9\play-configuration_2.13-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\slf4j-api\2.0.17\slf4j-api-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\jul-to-slf4j\2.0.17\jul-to-slf4j-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\slf4j\jcl-over-slf4j\2.0.17\jcl-over-slf4j-2.0.17.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-actor_2.13\1.0.3\pekko-actor_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-actor-typed_2.13\1.0.3\pekko-actor-typed_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-slf4j_2.13\1.0.3\pekko-slf4j_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-serialization-jackson_2.13\1.0.3\pekko-serialization-jackson_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-core\2.14.3\jackson-core-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-annotations\2.14.3\jackson-annotations-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.14.3\jackson-datatype-jdk8-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.14.3\jackson-datatype-jsr310-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\core\jackson-databind\2.14.3\jackson-databind-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\dataformat\jackson-dataformat-cbor\2.14.3\jackson-dataformat-cbor-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\module\jackson-module-parameter-names\2.14.3\jackson-module-parameter-names-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\fasterxml\jackson\module\jackson-module-scala_2.13\2.14.3\jackson-module-scala_2.13-2.14.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-api\0.11.5\jjwt-api-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-impl\0.11.5\jjwt-impl-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\io\jsonwebtoken\jjwt-jackson\0.11.5\jjwt-jackson-0.11.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-json_2.13\3.0.5\play-json_2.13-3.0.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\guava\32.1.3-jre\guava-32.1.3-jre.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\javax\inject\javax.inject\1\javax.inject-1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\ssl-config-core_2.13\0.6.1\ssl-config-core_2.13-0.6.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-parser-combinators_2.13\1.1.2\scala-parser-combinators_2.13-1.1.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\ch\qos\logback\logback-core\1.5.18\logback-core-1.5.18.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\reactivestreams\reactive-streams\1.0.4\reactive-streams-1.0.4.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-stream_2.13\1.0.3\pekko-stream_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-parsing_2.13\1.0.1\pekko-parsing_2.13-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\parboiled\parboiled_2.13\2.5.0\parboiled_2.13-2.5.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\jakarta\inject\jakarta.inject-api\2.0.1\jakarta.inject-api-2.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\aopalliance\aopalliance\1.0\aopalliance-1.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\errorprone\error_prone_annotations\2.21.1\error_prone_annotations-2.21.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\config\1.4.5\config-1.4.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-collection-compat_2.13\2.8.1\scala-collection-compat_2.13-2.8.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\zaxxer\HikariCP\4.0.3\HikariCP-4.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-build-link\2.9.0\play-build-link-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-streams_2.13\2.9.0\play-streams_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-configuration_2.13\2.9.0\play-configuration_2.13-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\twirl-api_2.13\1.6.2\twirl-api_2.13-1.6.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-actor_2.13\2.6.21\akka-actor_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-actor-typed_2.13\2.6.21\akka-actor-typed_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-slf4j_2.13\2.6.21\akka-slf4j_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-serialization-jackson_2.13\2.6.21\akka-serialization-jackson_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-json_2.13\2.10.2\play-json_2.13-2.10.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-exceptions\3.0.9\play-exceptions-3.0.9.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\lz4\lz4-java\1.8.0\lz4-java-1.8.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\thoughtworks\paranamer\paranamer\2.8\paranamer-2.8.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\playframework\play-functional_2.13\3.0.5\play-functional_2.13-3.0.5.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.16\scala-reflect-2.13.16.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\google\j2objc\j2objc-annotations\2.8\j2objc-annotations-2.8.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\apache\pekko\pekko-protobuf-v3_2.13\1.0.3\pekko-protobuf-v3_2.13-1.0.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-exceptions\2.9.0\play-exceptions-2.9.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-stream_2.13\2.6.21\akka-stream_2.13-2.6.21.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\modules\scala-java8-compat_2.13\1.0.0\scala-java8-compat_2.13-1.0.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\play\play-functional_2.13\2.10.2\play-functional_2.13-2.10.2.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\com\typesafe\akka\akka-protobuf-v3_2.13\2.6.21\akka-protobuf-v3_2.13-2.6.21.jar [exists ]
Options:
-deprecation -unchecked -encoding utf8 -Yrangepos -Xplugin-require:semanticdb




#### Error stacktrace:

```
scala.collection.generic.CommonErrors$.indexOutOfBounds(CommonErrors.scala:23)
	scala.collection.mutable.ArrayBuffer.apply(ArrayBuffer.scala:102)
	scala.reflect.internal.Types$Type.findMemberInternal$1(Types.scala:1030)
	scala.reflect.internal.Types$Type.findMember(Types.scala:1035)
	scala.reflect.internal.Types$Type.memberBasedOnName(Types.scala:661)
	scala.reflect.internal.Types$Type.nonLocalMember(Types.scala:652)
	scala.tools.nsc.typechecker.Typers$Typer.member(Typers.scala:674)
	scala.tools.nsc.typechecker.Typers$Typer.typedSelect$1(Typers.scala:5491)
	scala.tools.nsc.typechecker.Typers$Typer.typedSelectOrSuperCall$1(Typers.scala:5660)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6289)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$adapt$15(Typers.scala:1041)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:717)
	scala.tools.nsc.typechecker.Typers$Typer.insertApply$1(Typers.scala:4702)
	scala.tools.nsc.typechecker.Typers$Typer.vanillaAdapt$1(Typers.scala:1275)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1348)
	scala.tools.nsc.typechecker.Typers$Typer.adapt(Typers.scala:1321)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6359)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$41(Typers.scala:5262)
	scala.tools.nsc.typechecker.Typers$Typer.silent(Typers.scala:703)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5264)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5296)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6288)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3565)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3990)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:4009)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5285)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5296)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6288)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedFunction(Typers.scala:6433)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction(Typers.scala:3203)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$121(Typers.scala:6226)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction$1(Typers.scala:512)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6266)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6298)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3565)
	scala.tools.nsc.typechecker.Typers$Typer.handlePolymorphicCall$1(Typers.scala:3990)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:4009)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5285)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5296)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6288)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedFunction(Typers.scala:6433)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction(Typers.scala:3203)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typed1$121(Typers.scala:6226)
	scala.tools.nsc.typechecker.Typers$Typer.typedFunction$1(Typers.scala:512)
	scala.tools.nsc.typechecker.Typers$Typer.typedOutsidePatternMode$1(Typers.scala:6266)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6298)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.typedArg(Typers.scala:3565)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgWithFormal$1(PatternTypers.scala:136)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.$anonfun$typedArgsForFormals$4(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals(PatternTypers.scala:150)
	scala.tools.nsc.typechecker.PatternTypers$PatternTyper.typedArgsForFormals$(PatternTypers.scala:131)
	scala.tools.nsc.typechecker.Typers$Typer.typedArgsForFormals(Typers.scala:202)
	scala.tools.nsc.typechecker.Typers$Typer.handleMonomorphicCall$1(Typers.scala:3921)
	scala.tools.nsc.typechecker.Typers$Typer.doTypedApply(Typers.scala:3972)
	scala.tools.nsc.typechecker.Typers$Typer.normalTypedApply$1(Typers.scala:5285)
	scala.tools.nsc.typechecker.Typers$Typer.typedApply$1(Typers.scala:5296)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6288)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.computeType(Typers.scala:6433)
	scala.tools.nsc.typechecker.Namers$Namer.assignTypeToTree(Namers.scala:1085)
	scala.tools.nsc.typechecker.Namers$Namer.methodSig(Namers.scala:1446)
	scala.tools.nsc.typechecker.Namers$Namer.memberSig(Namers.scala:1929)
	scala.tools.nsc.typechecker.Namers$Namer.typeSig(Namers.scala:1880)
	scala.tools.nsc.typechecker.Namers$Namer$MonoTypeCompleter.completeImpl(Namers.scala:834)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete(Namers.scala:2077)
	scala.tools.nsc.typechecker.Namers$LockingTypeCompleter.complete$(Namers.scala:2075)
	scala.tools.nsc.typechecker.Namers$TypeCompleterBase.complete(Namers.scala:2070)
	scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1583)
	scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1548)
	scala.reflect.internal.Symbols$Symbol.initialize(Symbols.scala:1747)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:5916)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6422)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3547)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3547)
	scala.tools.nsc.typechecker.Typers$Typer.typedTemplate(Typers.scala:2133)
	scala.tools.nsc.typechecker.Typers$Typer.typedClassDef(Typers.scala:1971)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6251)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Typers$Typer.typedStat$1(Typers.scala:6422)
	scala.tools.nsc.typechecker.Typers$Typer.$anonfun$typedStats$10(Typers.scala:3547)
	scala.tools.nsc.typechecker.Typers$Typer.typedStats(Typers.scala:3547)
	scala.tools.nsc.typechecker.Typers$Typer.typedPackageDef$1(Typers.scala:5925)
	scala.tools.nsc.typechecker.Typers$Typer.typed1(Typers.scala:6254)
	scala.tools.nsc.typechecker.Typers$Typer.typed(Typers.scala:6344)
	scala.tools.nsc.typechecker.Analyzer$typerFactory$TyperPhase.apply(Analyzer.scala:126)
	scala.tools.nsc.Global$GlobalPhase.applyPhase(Global.scala:483)
	scala.tools.nsc.interactive.Global$TyperRun.applyPhase(Global.scala:1370)
	scala.tools.nsc.interactive.Global$TyperRun.typeCheck(Global.scala:1363)
	scala.tools.nsc.interactive.Global.typeCheck(Global.scala:681)
	scala.meta.internal.pc.WithCompilationUnit.<init>(WithCompilationUnit.scala:24)
	scala.meta.internal.pc.SimpleCollector.<init>(PcCollector.scala:348)
	scala.meta.internal.pc.PcSemanticTokensProvider$Collector$.<init>(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector$lzycompute$1(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.Collector(PcSemanticTokensProvider.scala:19)
	scala.meta.internal.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:73)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticTokens$1(ScalaPresentationCompiler.scala:212)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:148)
	scala.meta.internal.pc.CompilerAccess.$anonfun$withInterruptableCompiler$1(CompilerAccess.scala:92)
	scala.meta.internal.pc.CompilerAccess.$anonfun$onCompilerJobQueue$1(CompilerAccess.scala:209)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:152)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	java.base/java.lang.Thread.run(Thread.java:833)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1 is out of bounds (min 0, max 2)