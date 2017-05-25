name := "markovshield_benchmark"

enablePlugins(GatlingPlugin)

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.5" % "test,it"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.2.5" % "test,it"