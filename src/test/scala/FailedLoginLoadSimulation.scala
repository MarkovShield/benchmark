import com.typesafe.config.{Config, ConfigFactory}

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class FailedLoginLoadSimulation extends Simulation {

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

	val scn: ScenarioBuilder = scenario("FailedLoginLoadSimulation")
		.exec(http("Req 0 /")
			.get("/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 1 /configuration/architecture/")
			.get("/configuration/architecture/")
			.headers(headers_0))
		.pause(17)
		.exec(http("Req 2 /configuration/backend-apache/")
			.get("/configuration/backend-apache/")
			.headers(headers_0))
		.pause(5)
		.exec(http("Req 3 /howto/explorer/")
			.get("/howto/explorer/")
			.headers(headers_0))
		.pause(7)
		.exec(http("Req 4 CRIT /private/request-header/")
			.get("/private/request-header/")
			.headers(headers_0))
		.pause(13)
		.exec(http("Req 5 /login/login.php")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "12345")
			.formParam("appid", "0"))
		.pause(8)
		.exec(http("Req 6 /login/login.php")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "admin")
			.formParam("password", "admin")
			.formParam("appid", "0"))
		.pause(6)
		.exec(http("Req 7 /login/login.php")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "2"))
		.pause(15)
		.exec(http("Req 8 CRIT /private/1/")
			.get("/private/1/")
			.headers(headers_0)
			.resources(http("Req 9 CRIT /private/1/printheader.php")
			.get("/private/1/printheader.php")
			.headers(headers_0)))
		.pause(8)
		.exec(http("Req 10 /session_logout")
			.get("/session_logout/")
			.headers(headers_0))
		.pause(26)
		.exec(http("Req 11 /howto/edit/")
			.get("/howto/edit/")
			.headers(headers_0))
		.pause(10)
		.exec(http("Req 12 /configuration/pre-auth/")
			.get("/configuration/pre-auth/")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(100) over (60 seconds))).protocols(httpProtocol)
}