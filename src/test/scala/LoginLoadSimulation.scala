import com.typesafe.config.{Config, ConfigFactory}

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class LoginLoadSimulation extends Simulation {

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

	val scn: ScenarioBuilder = scenario("LoginLoadSimulation")
		.exec(http("Req 1 /")
			.get("/")
			.headers(headers_0))
		.pause(5)
		.exec(http("Req 2 /login/login.php")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "0"))
		.pause(5)
		.exec(http("Req 3 CRIT /private/1/")
			.get("/private/1/")
			.headers(headers_0))
		.pause(9)
		.exec(http("Req 4 CRIT /private/1/printheader.php")
			.get("/private/1/printheader.php")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(100) over (60 seconds))).protocols(httpProtocol)
}