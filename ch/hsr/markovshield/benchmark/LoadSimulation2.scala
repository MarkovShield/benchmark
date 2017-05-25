package ch.hsr.markovshield.benchmark

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadSimulation2 extends Simulation {

	val httpProtocol = http
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

	val scn = scenario("LoadSimulation2")
		.exec(http("request_0")
			.get("http://" + uri1 + "/configuration/architecture/")
			.headers(headers_0)
		.pause(8)
		.exec(http("request_1")
			.get("http://" + uri1 + "/configuration/frontend-apache/")
			.headers(headers_0)
		.pause(7)
		.exec(http("request_2")
			.get("http://" + uri1 + "/private/request-header/")
			.headers(headers_0)
		.pause(3)
		.exec(http("request_3")
			.get("/howto/edit/")
			.headers(headers_3)
		.pause(20)
		.exec(http("request_4")
			.get("/private/chat/")
			.headers(headers_3)
		.pause(3)
		.exec(http("request_5")
			.get("/private/2/")
			.headers(headers_3)
			.resources(http("request_6")
			.get("/private/2/chat.php")
			.headers(headers_3)
		.pause(8)
		.exec(http("request_7")
			.post("/private/2/chat.php")
			.headers(headers_3)
			.formParam("message", "Hey")
		.pause(3)
		.exec(http("request_8")
			.get("/session_logout/")
			.headers(headers_3)
		.pause(2)
		.exec(http("request_9")
			.get("/howto/logfile/")
			.headers(headers_3)

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}