package feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class CustomAdvancedFeeder extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io/")
  val numIterator: Iterator[Int] = (1 to 10).iterator

  val customFeeder: Iterator[Map[String, Int]] = Iterator.continually(Map("id" -> numIterator.next()))

  def getPet(): ChainBuilder = {
    repeat(5) {
      feed(customFeeder)
        .exec(
          http("get pet")
            .get("v2/pet/#{id}")
            .check(status.saveAs("statusCode"))
        )
        .exec { session =>
          println("ID: " + session("id").as[Int] + "  STATUSCODE: " + session("statusCode").as[Int])
          session
        }
    }
  }

  val scn: ScenarioBuilder = scenario("get pet").exec(getPet())
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
