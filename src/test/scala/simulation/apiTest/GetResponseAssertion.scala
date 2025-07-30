package simulation.apiTest

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder

class GetResponseAssertion extends Simulation{
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario("Assert response")
    .exec(
      http("Assert")
        .get(url = "/v2/pet/findByStatus?status=sold")
        .check(jsonPath("$.tags[0].id")is("-55902183"))
    )
  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
