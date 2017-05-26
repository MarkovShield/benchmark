import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadSimulation4 extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseURL("https://localhost")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff*""", """.*\.(t|o)tf""", """.*\.png""", """.*/img.*""", """.*/fonts.*"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

    val uri01 = "localhost"

	val scn: ScenarioBuilder = scenario("LoadSimulation4")
		.exec(http("Req 0 /")
			.get("http://" + uri01 + "/")
			.headers(headers_0))
		.pause(6)
		.exec(http("Req 1 /configuration/pre-auth/")
			.get("/configuration/pre-auth/")
			.headers(headers_1))
		.pause(11)
		.exec(http("Req 2 /howto/start-stop/")
			.get("/howto/start-stop/")
			.headers(headers_1))
		.pause(17)
		.exec(http("Req 3 /configuration/backend-apache/")
			.get("/configuration/backend-apache/")
			.headers(headers_1))
		.pause(2)
		.exec(http("Req 4 /configuration/client-certificates/")
			.get("/configuration/client-certificates/")
			.headers(headers_1))
		.pause(18)


	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}