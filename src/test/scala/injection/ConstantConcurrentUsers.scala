package injection

import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ConstantConcurrentUsers extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario(name = "Get request")
    .exec(
      http("Post API").post("/v2/pet")
        .body(RawFileBody("TestData/pet.json")).asJson
    )
    .pause(4).exec(
      http(requestName = "Get API").get("/v2/pet/710")
    )

  setUp(
    scn.inject(
      constantConcurrentUsers(10).during(25)
    )
  ).protocols(httpProtocol)
}
