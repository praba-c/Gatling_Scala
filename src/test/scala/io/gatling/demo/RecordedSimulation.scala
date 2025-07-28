package io.gatling.demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://api-ecomm.gatling.io")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9")
    .originHeader("https://ecomm.gatling.io")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"Access-Control-Request-Headers" -> "content-type",
  		"Access-Control-Request-Method" -> "POST",
  		"Sec-Fetch-Dest" -> "empty",
  		"Sec-Fetch-Mode" -> "cors",
  		"Sec-Fetch-Site" -> "same-site"
  )
  
  private val headers_1 = Map(
  		"Accept" -> "application/json, text/plain, */*",
  		"Content-Type" -> "application/json",
  		"Sec-Fetch-Dest" -> "empty",
  		"Sec-Fetch-Mode" -> "cors",
  		"Sec-Fetch-Site" -> "same-site",
  		"sec-ch-ua" -> """Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows"
  )


  private val scn = scenario("RecordedSimulation")
    .exec(
      http("request_0")
        .options("/cart")
        .headers(headers_0)
        .resources(
          http("request_1")
            .post("/cart")
            .headers(headers_1)
            .body(RawFileBody("io/gatling/demo/recordedsimulation/0001_request.json"))
        ),
      pause(33),
      http("request_2")
        .options("/cart")
        .headers(headers_0)
        .resources(
          http("request_3")
            .post("/cart")
            .headers(headers_1)
            .body(RawFileBody("io/gatling/demo/recordedsimulation/0003_request.json"))
        )
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
