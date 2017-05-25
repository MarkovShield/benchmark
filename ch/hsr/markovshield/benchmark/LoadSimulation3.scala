package ch.hsr.markovshield.benchmark

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoadSimulation3 extends Simulation {

	val httpProtocol = http
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

	val scn = scenario("LoadSimulation3")
		.exec(http("request_0")
			.get("https://" + uri1 + ":443/")
			.headers(headers_0)
		.pause(11)
		.exec(http("request_1")
			.get("/howto/explorer/")
			.headers(headers_1)
		.pause(10)
		.exec(http("request_2")
			.get("/howto/start-stop/")
			.headers(headers_1)
		.pause(8)
		.exec(http("request_3")
			.get("/configuration/client-certificates/")
			.headers(headers_1)
		.pause(3)
		.exec(http("request_4")
			.get("/configuration/frontend-apache/")
			.headers(headers_1)
		.pause(10)
		.exec(http("request_5")
			.get("/configuration/pre-auth/")
			.headers(headers_1)
		.pause(6)
		.exec(http("request_6")
			.get("/admin")
			.headers(headers_1)
			.check(status.is(404))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}