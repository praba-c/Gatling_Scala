package simulation.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder

class PostApiTest extends Simulation {
  val httpProtocol1: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario(name = "Post request")
    .exec(
      http(requestName = "Post API").post("/v2/pet")
        .body(RawFileBody("TestData/pet.json")).asJson
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol1))
}

