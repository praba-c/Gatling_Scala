package feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class CustomFeeder extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")
  val customFeeder: BatchableFeederBuilder[String] = separatedValues("TestData/feeder/petData.txt", '#')

  def getPet(): ChainBuilder = {
    feed(customFeeder)
      .exec(
        http("get the pet detail for #{id}")
          .get("/v2/pet/#{id}")
          .check(status.is(200),
            jsonPath("$.id")is("#{id}"))
      )
  }

  val scn: ScenarioBuilder = scenario("Get the data from CustomFeeder").exec(getPet())

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
