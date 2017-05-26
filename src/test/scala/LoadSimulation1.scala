import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadSimulation1 extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseURL("https://localhost")
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

    val uri1 = "localhost"

	val scn: ScenarioBuilder = scenario("LoadSimulation")
		.exec(http("Req 0 /")
			.get("/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 1 /configuration/pre-auth/")
			.get("http://" + uri1 + "/configuration/pre-auth/")
			.headers(headers_2))
		.pause(14)
		.exec(http("Req 2 /howto/explorer/")
			.get("/howto/explorer/")
			.headers(headers_0))
		.pause(10)
		.exec(http("Req 3 /howto/start-stop/")
			.get("/howto/start-stop/")
			.headers(headers_0))
		.pause(6)
		.exec(http("Req 4 /configuration/backend-apache/")
			.get("/configuration/backend-apache/")
			.headers(headers_0))
		.pause(9)
		.exec(http("Req 5 CRIT /private/request-header/")
			.get("/private/request-header/")
			.headers(headers_0))
		.pause(2)
		.exec(http("Req 6 /login/login.php")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "0"))
		.pause(3)
		.exec(http("Req 7 CRIT /private/1/")
			.get("/private/1/")
			.headers(headers_0)
			.resources(http("Req 8 CRIT /private/1/printheader.php")
			.get("/private/1/printheader.php")
			.headers(headers_0)))
		.pause(9)
		.exec(http("Req 9 CRIT /private/2/")
			.get("/private/2/")
			.headers(headers_0)
			.resources(http("Req 10 CRIT /private/2/chat.php")
			.get("/private/2/chat.php")
			.headers(headers_0)))
		.pause(3)
		.exec(http("Req 11 CRIT /private/2/chat.php")
			.post("/private/2/chat.php")
			.headers(headers_0)
			.formParam("message", "Test"))
		.pause(2)
		.exec(http("Req 12 /session_logout/")
			.get("/session_logout/")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(10) over (60 seconds))).protocols(httpProtocol)
}