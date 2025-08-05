package injection

import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class OpenInjectionRampUsersRateRange extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io/")

  val scn: ScenarioBuilder = scenario("Get Request")
    .exec(
      http("Get Request")
        .get("v2/pet/710")
    )

  setUp(
    scn.inject(
      rampUsersPerSec(5).to(10).during(20),
    )
  ).protocols(httpProtocol)
}
