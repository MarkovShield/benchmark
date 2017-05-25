package ch.hsr.markovshield.benchmark

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadSimulation5 extends Simulation {

	val httpProtocol = http
		.baseURL("https://localhost")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff*""", """.*\.(t|o)tf""", """.*\.png""", """.*/img.*""", """.*/fonts.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:53.0) Gecko/20100101 Firefox/53.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

    val uri1 = "https://localhost:443"

	val scn = scenario("LoadSimulation5")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
		.pause(3)
		.exec(http("request_1")
			.get("/configuration/architecture/")
			.headers(headers_0)
		.pause(17)
		.exec(http("request_2")
			.get("/configuration/backend-apache/")
			.headers(headers_0)
		.pause(5)
		.exec(http("request_3")
			.get("/howto/explorer/")
			.headers(headers_0)
		.pause(7)
		.exec(http("request_4")
			.get("/private/request-header/")
			.headers(headers_0)
		.pause(10)
		.exec(http("request_5")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "12345")
			.formParam("appid", "0"))
		.pause(8)
		.exec(http("request_6")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "admin")
			.formParam("password", "admin")
			.formParam("appid", "0"))
		.pause(2)
		.exec(http("request_7")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "0"))
		.pause(4)
		.exec(http("request_8")
			.get("/private/1/")
			.headers(headers_0)
			.resources(http("request_9")
			.get("/private/1/printheader.php")
			.headers(headers_0)
		.pause(8)
		.exec(http("request_10")
			.get("/session_logout/")
			.headers(headers_0)
		.pause(26)
		.exec(http("request_11")
			.get("/howto/edit/")
			.headers(headers_0)
		.pause(10)
		.exec(http("request_12")
			.get("/configuration/pre-auth/")
			.headers(headers_0)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}