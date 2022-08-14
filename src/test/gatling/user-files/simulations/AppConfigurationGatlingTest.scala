import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the AppConfiguration entity.
 */
class AppConfigurationGatlingTest extends Simulation {

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

    val scn = scenario("Test the AppConfiguration entity")
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
            exec(http("Get all appConfigurations")
            .get("/api/app-configurations")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new appConfiguration")
            .post("/api/app-configurations")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "defaultTimezone":"SAMPLE_TEXT"
                , "ordersUnderMarketPercent":"SAMPLE_TEXT"
                , "ordersMinUsd":"SAMPLE_TEXT"
                , "bitcoinSendingFee":"SAMPLE_TEXT"
                , "frontendBaseurl":"SAMPLE_TEXT"
                , "frontendDirroot":"SAMPLE_TEXT"
                , "fiatWithdrawFee":"SAMPLE_TEXT"
                , "apiDbDebug":"Y"
                , "apiDirroot":"SAMPLE_TEXT"
                , "supportEmail":"SAMPLE_TEXT"
                , "emailSmtpHost":"SAMPLE_TEXT"
                , "emailSmtpPort":"SAMPLE_TEXT"
                , "emailSmtpSecurity":"SAMPLE_TEXT"
                , "emailSmtpUsername":"SAMPLE_TEXT"
                , "emailSmtpPassword":"SAMPLE_TEXT"
                , "emailSmtpSendFrom":"SAMPLE_TEXT"
                , "bitcoinUsername":"SAMPLE_TEXT"
                , "bitcoinAccountname":"SAMPLE_TEXT"
                , "bitcoinPassphrase":"SAMPLE_TEXT"
                , "bitcoinHost":"SAMPLE_TEXT"
                , "bitcoinPort":"SAMPLE_TEXT"
                , "bitcoinProtocol":"SAMPLE_TEXT"
                , "authyApiKey":"SAMPLE_TEXT"
                , "helpdeskKey":"SAMPLE_TEXT"
                , "exchangeName":"SAMPLE_TEXT"
                , "mcryptKey":"SAMPLE_TEXT"
                , "currencyConversionFee":"SAMPLE_TEXT"
                , "crossCurrencyTrades":"Y"
                , "btcCurrencyId":"SAMPLE_TEXT"
                , "depositBitcoinDesc":"SAMPLE_TEXT"
                , "defaultFeeScheduleId":"SAMPLE_TEXT"
                , "historyBuyId":"SAMPLE_TEXT"
                , "historyDepositId":"SAMPLE_TEXT"
                , "historyLoginId":"SAMPLE_TEXT"
                , "historySellId":"SAMPLE_TEXT"
                , "historyWithdrawId":"SAMPLE_TEXT"
                , "orderTypeAsk":"SAMPLE_TEXT"
                , "requestAwaitingId":"SAMPLE_TEXT"
                , "requestCancelledId":"SAMPLE_TEXT"
                , "requestCompletedId":"SAMPLE_TEXT"
                , "orderTypeBid":"SAMPLE_TEXT"
                , "requestDepositId":"SAMPLE_TEXT"
                , "requestPendingId":"SAMPLE_TEXT"
                , "requestWithdrawalId":"SAMPLE_TEXT"
                , "transactionsBuyId":"SAMPLE_TEXT"
                , "transactionsSellId":"SAMPLE_TEXT"
                , "withdrawFiatDesc":"SAMPLE_TEXT"
                , "withdrawBtcDesc":"SAMPLE_TEXT"
                , "formEmailFrom":"SAMPLE_TEXT"
                , "emailNotifyNewUsers":"Y"
                , "passRegex":"SAMPLE_TEXT"
                , "passMinChars":"SAMPLE_TEXT"
                , "authDbDebug":"Y"
                , "bitcoinReserveMin":"SAMPLE_TEXT"
                , "bitcoinReserveRatio":"SAMPLE_TEXT"
                , "bitcoinWarmWalletAddress":"SAMPLE_TEXT"
                , "cronDbDebug":"Y"
                , "quandlApiKey":"SAMPLE_TEXT"
                , "cronDirroot":"SAMPLE_TEXT"
                , "backstageDbDebug":"Y"
                , "backstageDirroot":"SAMPLE_TEXT"
                , "emailNotifyFiatWithdrawals":"Y"
                , "contactEmail":"SAMPLE_TEXT"
                , "cloudflareApiKey":"SAMPLE_TEXT"
                , "googleRecaptchApiKey":"SAMPLE_TEXT"
                , "cloudflareBlacklist":"Y"
                , "cloudflareEmail":"SAMPLE_TEXT"
                , "googleRecaptchApiSecret":"SAMPLE_TEXT"
                , "cloudflareBlacklistAttempts":"0"
                , "cloudflareBlacklistTimeframe":"0"
                , "cryptoCapitalPk":"SAMPLE_TEXT"
                , "depositFiatDesc":"SAMPLE_TEXT"
                , "emailNotifyFiatFailed":"Y"
                , "tradingStatus":"SAMPLE_TEXT"
                , "withdrawalsStatus":"SAMPLE_TEXT"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_appConfiguration_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created appConfiguration")
                .get("${new_appConfiguration_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created appConfiguration")
            .delete("${new_appConfiguration_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
