package ch.hsr.markovshield.benchmark

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadSimulation1 extends Simulation {

	val httpProtocol = http
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

	val scn = scenario("LoadSimulation")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
		.pause(3)
		.exec(http("request_2")
			.get("http://" + uri1 + "/configuration/pre-auth/")
			.headers(headers_2)
		.pause(14)
		.exec(http("request_3")
			.get("/configuration/pre-auth/")
			.headers(headers_0)
		.pause(10)
		.exec(http("request_5")
			.get("/howto/start-stop/")
			.headers(headers_0)
		.pause(6)
		.exec(http("request_6")
			.get("/configuration/backend-apache/")
			.headers(headers_0)
		.pause(9)
		.exec(http("request_7")
			.get("/private/request-header/")
			.headers(headers_0)
		.pause(2)
		.exec(http("request_8")
			.post("/login/login.php")
			.headers(headers_0)
			.formParam("user", "hacker")
			.formParam("password", "compass")
			.formParam("appid", "0"))
		.pause(3)
		.exec(http("request_9")
			.get("/private/1/")
			.headers(headers_0)
			.resources(http("request_10")
			.get("/private/1/printheader.php")
			.headers(headers_0)
		.pause(9)
		.exec(http("request_11")
			.get("/private/2/")
			.headers(headers_0)
			.resources(http("request_12")
			.get("/private/2/chat.php")
			.headers(headers_0)
		.pause(3)
		.exec(http("request_13")
			.post("/private/2/chat.php")
			.headers(headers_0)
			.formParam("message", "Test")
		.pause(2)
		.exec(http("request_14")
			.get("/session_logout/")
			.headers(headers_0)
			.check(status.is(302))

	setUp(scn.inject(rampUsers(10) over (10 seconds))).protocols(httpProtocol)
}