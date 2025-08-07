package parameters

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class RunTimeParameters extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io/v2")

  def minUsers: Int = System.getProperty("minUsers").toInt
  def maxUsers: Int = System.getProperty("maxUsers").toInt
  def duration: Int = System.getProperty("duration").toInt

  val scn: ScenarioBuilder = scenario("Get Pets")
    .exec(
      http("Get API")
        .get("/pet/findByStatus?status=available")
    )

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
