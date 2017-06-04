import com.typesafe.config.{Config, ConfigFactory}

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class NormalUsageSimulation2 extends Simulation {

	val conf: Config = ConfigFactory.load()
	val baseUrl: String = conf.getString("baseUrl")

	val httpProtocol: HttpProtocolBuilder = http
		.baseURL(baseUrl)
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff*""", """.*\.(t|o)tf""", """.*\.png""", """.*/img.*""", """.*/fonts.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0")

	val headers_0 = Map("Accept" -> "*/*")

	val scn: ScenarioBuilder = scenario("LoadSimulation2")
		.exec(http("Req 0 /configuration/architecture/")
			.get("/configuration/architecture/")
			.headers(headers_0))
		.pause(8)
		.exec(http("Req 1 /configuration/frontend-apache/")
			.get("/configuration/frontend-apache/")
			.headers(headers_0))
		.pause(7)
		.exec(http("Req 2 CRIT /private/request-header/")
			.get("/private/request-header/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 3 /howto/edit/")
			.get("/howto/edit/")
			.headers(headers_0))
		.pause(20)
		.exec(http("Req 4 CRIT /private/chat/")
			.get("/private/chat/")
			.headers(headers_0))
		.pause(12)
		.exec(http("Req 5 CRIT /private/2/")
			.get("/private/2/")
			.headers(headers_0)
			.resources(http("Req 6 CRIT /private/2/chat.php")
			.get("/private/2/chat.php")
			.headers(headers_0)))
		.pause(8)
		.exec(http("Req 7 /howto/start-stop/")
			.get("/howto/start-stop/")
			.headers(headers_0))
		.pause(17)
		.exec(http("Req 8 CRIT /private/2/chat.php")
			.post("/private/2/chat.php")
			.headers(headers_0)
			.formParam("message", "Hey"))
		.pause(3)
		.exec(http("Req 9 /session_logout")
			.get("/session_logout/")
			.headers(headers_0))
		.pause(2)
		.exec(http("Req 10 /howto/logfile/")
			.get("/howto/logfile/")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(100) over (60 seconds))).protocols(httpProtocol)
}