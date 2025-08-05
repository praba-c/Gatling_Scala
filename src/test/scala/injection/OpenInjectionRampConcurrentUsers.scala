package injection

import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class OpenInjectionRampConcurrentUsers extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario(name = "Get request")
    .exec(
      http(requestName = "Get API").get("/v2/pet/710")
    )

  setUp(
    scn.inject(
      rampConcurrentUsers(10).to(20).during(20)
    )
  ).protocols(httpProtocol)
}
