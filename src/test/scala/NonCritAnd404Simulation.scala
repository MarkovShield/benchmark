import com.typesafe.config.{Config, ConfigFactory}

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class NonCritAnd404Simulation extends Simulation {

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

	val scn: ScenarioBuilder = scenario("NonCritAnd404Simulation")
		.exec(http("Req 0 /")
			.get("/")
			.headers(headers_0))
		.pause(11)
		.exec(http("Req 1 /howto/explorer/")
			.get("/howto/explorer/")
			.headers(headers_0))
		.pause(10)
		.exec(http("Req 2 /howto/start-stop/")
			.get("/howto/start-stop/")
			.headers(headers_0))
		.pause(2)
		.exec(http("Req 3 /status")
			.get("/status")
			.headers(headers_0)
			.check(status.is(404)))
		.pause(8)
		.exec(http("Req 4 /configuration/client-certificates/")
			.get("/configuration/client-certificates/")
			.headers(headers_0))
		.pause(3)
		.exec(http("Req 5 /configuration/frontend-apache/")
			.get("/configuration/frontend-apache/")
			.headers(headers_0))
		.pause(10)
		.exec(http("Req 6 /configuration/pre-auth/")
			.get("/configuration/pre-auth/")
			.headers(headers_0))
		.pause(6)
		.exec(http("Req 7 /admin")
			.get("/admin")
			.headers(headers_0)
			.check(status.is(404)))

	setUp(scn.inject(rampUsers(100) over (60 seconds))).protocols(httpProtocol)
}