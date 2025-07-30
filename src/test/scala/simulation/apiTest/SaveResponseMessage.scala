package simulation.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder

class SaveResponseMessage extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val getallpendingpetssscn: ScenarioBuilder = scenario("Get the pet having a pet id")
    .exec(
      http("Get API for getting the pet")
        .get("/v2/pet/findByStatus?status=pending")
        .check(status.is(200))
        .check(jsonPath("$[4].id").saveAs("petId"))
        .check(jsonPath("$[4].name").saveAs("petName"))
    )
    .pause(10).exec(
      http("GET API to get a pet")
        .get("/v2/pet/#{petId}")
        .check(
          status.in(200 to 204),
          jsonPath("$.id").is("#{petId}"),
          jsonPath("$.name").is("#{petName}")
        )
    )

  setUp(
    getallpendingpetssscn.inject(atOnceUsers(1)),
  ).protocols(httpProtocol)
}
