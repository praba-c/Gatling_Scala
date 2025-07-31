package utils

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure._

trait BasicUtilTests {
  def postPet(repeatStatus: Boolean = true, repeatCount: Int = 2): ChainBuilder = {
    val postRequest = exec(
      http("Post")
        .post("/v2/pet")
        .body(RawFileBody("TestData/pet.json")).asJson
    )
    if (repeatStatus) {
      repeat(repeatCount) {
        postRequest
      }
    } else {
      postRequest
    }
  }

  def getPet(petId: Int, statusCode: Int, repeatStatus: Boolean): ChainBuilder = {
    val getRequest = exec(
      http("Get")
        .get(s"/v2/pet/${petId}")
        .check(status.is(statusCode))
        .check(status.saveAs("statusCode"))
    ).exec { session =>
      println("STATUS CODE " + session("statusCode").as[Int])
      session
    }
    if (repeatStatus) {
      repeat(2) {
        getRequest
      }
    } else {
      getRequest
    }
  }

  def updatePet(filePath: String): ChainBuilder = {
    exec(
      http("Update")
        .put("/v2/pet")
        .body(RawFileBody(filePath)).asJson
    )
  }

  def deletePet(petId: Int = 710, statusCode:Int = 200): ChainBuilder = {
    exec(
      http("Delete")
        .delete(s"/v2/pet/${petId}")
        .check(status is statusCode)
    )
  }
}
