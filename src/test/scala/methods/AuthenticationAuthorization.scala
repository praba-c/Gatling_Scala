package methods

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AuthenticationAuthorization extends Simulation {
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io/v2/").header("Authorization", "special")

  val scn: ScenarioBuilder = scenario("Authenticate")
    .exec(http(requestName = "Authenticate")
    .get("pet/findByStatus?status=sold")
      .check(status is 200))
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
