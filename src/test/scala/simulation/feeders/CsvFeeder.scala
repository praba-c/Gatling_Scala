package simulation.feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import utils.BasicUtilTests

class CsvFeeder extends Simulation with BasicUtilTests {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")
  val csvFeeder = csv("TestData/feeder/petData.csv").circular()



  def getPet(): ChainBuilder = {
    feed(csvFeeder)
      .exec(postPet())
      .pause(5)
      .exec(
        http("get the pet detail for #{id}")
          .get("/v2/pet/#{id}")
          .check(status.is(200),
            jsonPath("$.id")is("#{id}"))
      )
  }

  val scn: ScenarioBuilder = scenario("Get the data from CSV").exec(getPet())

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
