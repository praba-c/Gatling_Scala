package utils

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.request.builder.HttpRequestBuilder

trait BasicUtilTests {
  def postPet(repeatStatus: Boolean = false, repeatCount: Int = 2): ChainBuilder = {
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
    val getRequest =
      http("Get")
        .get(s"/v2/pet/${petId}")
        .check(status.is(statusCode))
        .check(status.saveAs("statusCode"))

    requestToBuild(getRequest, repeatStatus, 2);
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

  def requestToBuild(request: HttpRequestBuilder, needRepeatations: Boolean, repeatCount: Int):ChainBuilder={
    if (needRepeatations) {
      repeat(2) {
        exec(request)
      }
    } else {
      exec(request)
    }
  }
}
