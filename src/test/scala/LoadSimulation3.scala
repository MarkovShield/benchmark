import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoadSimulation3 extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseURL("http://localhost")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff*""", """.*\.(t|o)tf""", """.*\.png""", """.*/img.*""", """.*/fonts.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate, br",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Upgrade-Insecure-Requests" -> "1")

    val uri1 = "localhost"

	val scn: ScenarioBuilder = scenario("LoadSimulation3")
		.exec(http("Req 0 /")
			.get("https://" + uri1 + "/")
			.headers(headers_0))
		.pause(11)
		.exec(http("Req 1 /howto/explorer/")
			.get("/howto/explorer/")
			.headers(headers_1))
		.pause(10)
		.exec(http("Req 2 /howto/start-stop/")
			.get("/howto/start-stop/")
			.headers(headers_1))
		.pause(8)
		.exec(http("Req 3 /configuration/client-certificates/")
			.get("/configuration/client-certificates/")
			.headers(headers_1))
		.pause(3)
		.exec(http("Req 4 /configuration/frontend-apache/")
			.get("/configuration/frontend-apache/")
			.headers(headers_1))
		.pause(10)
		.exec(http("Req 5 /configuration/pre-auth/")
			.get("/configuration/pre-auth/")
			.headers(headers_1))
		.pause(6)
		.exec(http("Req 6 /admin")
			.get("/admin")
			.headers(headers_1)
			.check(status.is(404)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}