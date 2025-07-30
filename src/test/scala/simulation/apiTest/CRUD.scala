package simulation.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocol

class CRUD extends Simulation {
  val httpProtocol: HttpProtocol = http.baseUrl("https://petstore.swagger.io")
  val postScn: ScenarioBuilder = scenario("Post")
    .exec(session => session.set("pause", "4")).pause("#{pause}")
    .exec(
      http("Post").post("/v2/pet")
        .body(RawFileBody("TestData/pet.json")).asJson
        .check(status is 200)
  )

  val getScn: ScenarioBuilder = scenario("Get").pause(6).exec(
    http("Get").get("/v2/pet/710")
  )

  val putScn: ScenarioBuilder = scenario("Put").pause(4).exec(
    http("Put").put("/v2/pet")
      .body(RawFileBody("TestData/updatedPet.json")).asJson
  )

  val deleteScn: ScenarioBuilder = scenario("Delete").pause(6).exec(
    http("Delete").delete("/v2/pet/710")
  )

  setUp(
    postScn.inject(atOnceUsers(1)),
    getScn.inject(atOnceUsers(1)),
    putScn.inject(atOnceUsers(1)),
    deleteScn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}
