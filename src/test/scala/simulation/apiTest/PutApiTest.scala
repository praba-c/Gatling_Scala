package simulation.apiTest

import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class PutApiTest extends Simulation{
  val httpProtocol1: HttpProtocolBuilder = http.baseUrl("https://petstore.swagger.io")

  val scn: ScenarioBuilder = scenario(name = "Put request")
    .exec(
      http(requestName = "Put API").post("/v2/pet")
        .body(StringBody(
          """
            |{
            |  "id": 710,
            |  "category": {
            |    "id": 0,
            |    "name": "string"
            |  },
            |  "name": "Prabu",
            |  "photoUrls": [
            |    "string"
            |  ],
            |  "tags": [
            |    {
            |      "id": 0,
            |      "name": "string"
            |    }
            |  ],
            |  "status": "available"
            |}
          """.stripMargin)).asJson
    )

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol1))
}
