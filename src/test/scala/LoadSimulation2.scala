import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadSimulation2 extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseURL("https://localhost")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff*""", """.*\.(t|o)tf""", """.*\.png""", """.*/img.*""", """.*/fonts.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map("Upgrade-Insecure-Requests" -> "1")

    val uri1 = "localhost"

	val scn: ScenarioBuilder = scenario("LoadSimulation2")
		.exec(http("Req 0 /configuration/architecture/")
			.get("http://" + uri1 + "/configuration/architecture/")
			.headers(headers_0))
		.pause(8)
		.exec(http("Req 1 /configuration/frontend-apache/")
			.get("http://" + uri1 + "/configuration/frontend-apache/")
			.headers(headers_0))
		.pause(7)
		.exec(http("Req 2 CRIT /private/request-header/")
			.get("http://" + uri1 + "/private/request-header/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 3 /howto/edit/")
			.get("/howto/edit/")
			.headers(headers_3))
		.pause(20)
		.exec(http("Req 4 CRIT /private/chat/")
			.get("/private/chat/")
			.headers(headers_3))
		.pause(3)
		.exec(http("Req 5 CRIT /private/2/")
			.get("/private/2/")
			.headers(headers_3)
			.resources(http("Req 6 CRIT /private/2/chat.php")
			.get("/private/2/chat.php")
			.headers(headers_3)))
		.pause(8)
		.exec(http("Req 7 CRIT /private/2/chat.php")
			.post("/private/2/chat.php")
			.headers(headers_3)
			.formParam("message", "Hey"))
		.pause(3)
		.exec(http("Req 8 /session_logout/")
			.get("/session_logout/")
			.headers(headers_3))
		.pause(2)
		.exec(http("Req 9 /howto/logfile/")
			.get("/howto/logfile/")
			.headers(headers_3))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}