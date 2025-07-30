package example

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.protocol.HttpProtocol
import utils.BasicUtilTests

class Methods extends Simulation with BasicUtilTests{

  val httpProtocol: HttpProtocol = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario("Scenario")
    .exec(postPet())
    .exec(getPet())
    .exec(updatePet())

  val scn1: ScenarioBuilder = scenario("Scenario 1")
    .exec(deletePet())
    .exec(postPet())
    .exec(getPet())
  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol),
    scn1.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
