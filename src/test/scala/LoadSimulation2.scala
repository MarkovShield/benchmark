import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class LoadSimulation2 extends Simulation {

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

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_7 = Map("Accept" -> "*/*")

	val scn: ScenarioBuilder = scenario("LoadSimulation")
		.exec(http("Req 0 p0")
			.get("/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 1 p1")
			.get("/configuration/pre-auth/")
			.headers(headers_2))
		.pause(14)
		.exec(http("Req 2 p2")
			.get("/howto/explorer/")
			.headers(headers_0))
		.pause(10)
		.exec(http("Req 3 p3")
			.get("/howto/start-stop/")
			.headers(headers_0))
		.pause(15)
		.exec(http("Req 4 p4")
			.get("/configuration/backend-apache/")
			.headers(headers_0))
		.pause(9)
		.exec(http("Req 5 CRIT p5")
			.get("/private/request-header/")
			.headers(headers_0))
		.pause(2)
		.exec(http("Req 6 Login")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "0"))
		.pause(7)
		.exec(http("Req 7 CRIT p6")
			.get("/private/1/")
			.headers(headers_0)
			.resources(http("Req 8 CRIT p7")
			.get("/private/1/printheader.php")
			.headers(headers_0)))
		.pause(24)
		.exec(http("Req 9 CRIT p8")
			.get("/private/2/")
			.headers(headers_0)
			.resources(http("Req 10 CRIT p9")
			.get("/private/2/chat.php")
			.headers(headers_0)))
		.pause(5)
		.exec(http("Req 11 CRIT p10")
			.post("/private/2/chat.php")
			.headers(headers_0)
			.formParam("message", "Test"))
		.pause(6)
		.exec(http("Req 12 Logout")
			.get("/session_logout/")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(100) over (60 seconds))).protocols(httpProtocol)
}