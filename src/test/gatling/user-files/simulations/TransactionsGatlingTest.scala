import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Transactions entity.
 */
class TransactionsGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Transactions entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all transactions")
            .get("/api/transactions")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new transactions")
            .post("/api/transactions")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "date":"2020-01-01T00:00:00.000Z"
                , "btc":"0"
                , "btcPrice":"0"
                , "fiat":"0"
                , "fee":"0"
                , "fee1":"0"
                , "btcNet":"0"
                , "btcNet1":"0"
                , "btcBefore1":"0"
                , "btcAfter1":"0"
                , "fiatBefore1":"0"
                , "fiatAfter1":"0"
                , "btcBefore":"0"
                , "btcAfter":"0"
                , "fiatBefore":"0"
                , "fiatAfter":"0"
                , "feeLevel":"0"
                , "feeLevel1":"0"
                , "origBtcPrice":"0"
                , "conversionFee":"0"
                , "convertAmount":"0"
                , "convertRateGiven":"0"
                , "convertSystemRate":"0"
                , "conversion":"Y"
                , "bidAtTransaction":"0"
                , "askAtTransaction":"0"
                , "factored":"Y"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_transactions_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created transactions")
                .get("${new_transactions_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created transactions")
            .delete("${new_transactions_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
