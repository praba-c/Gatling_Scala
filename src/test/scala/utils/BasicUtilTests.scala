package utils

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure._

trait BasicUtilTests {
  def postPet(): ChainBuilder = {
    exec(
      http("Post")
        .post("/v2/pet")
        .body(RawFileBody("TestData/pet.json")).asJson
    )
  }

  def getPet(): ChainBuilder = {
    exec(
      http("Get")
        .get("/v2/pet/710")
    )
  }

  def updatePet(): ChainBuilder = {
    exec(
      http("Update")
        .put("/v2/pet")
        .body(RawFileBody("TestData/updatedPet.json")).asJson
    )
  }

  def deletePet(): ChainBuilder = {
    exec(
      http("Delete")
        .delete("/v2/pet/710")
    )
  }
}
