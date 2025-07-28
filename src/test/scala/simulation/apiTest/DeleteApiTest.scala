package simulation.apiTest

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder

class DeleteApiTest extends Simulation{
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")
  val scn: ScenarioBuilder = scenario("Delete Api request")
    .exec(http("Delete API").delete("/v2/pet/710"))
  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
