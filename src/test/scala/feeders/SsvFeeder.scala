package feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.Console.println

class SsvFeeder extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")
  val ssvFeeder = ssv("TestData/feeder/petData.ssv")

  def getPet(): ChainBuilder = {
    repeat(2) {
      feed(ssvFeeder)
        .exec(
          http("get the pet detail for #{id}")
            .get("/v2/pet/#{id}")
            .check(status.is(200),
              jsonPath("$.id") is ("#{id}"),
              status.saveAs("statusCode"))
        )
        .exec { session =>
          println("ID: " + session("id").as[String] + "  " + session("statusCode").as[Int])
          session
        }
    }
  }

  val scn: ScenarioBuilder = scenario("Get the data from SSV").exec(getPet())

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
