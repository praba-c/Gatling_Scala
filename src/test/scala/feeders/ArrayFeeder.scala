package feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ArrayFeeder extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("http://petstore.swagger.io/")
  val arrayFeeder = array2FeederBuilder(Array(
    Map("id" -> 710, "name" -> "Dog")
  )).circular()

  def getPet(): ChainBuilder = {
    feed(arrayFeeder)
      .exec(
        http("get pet details")
          .get("v2/pet/#{id}")
      )
  }

  val scn: ScenarioBuilder = scenario("Get Pet").exec(getPet())
  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
