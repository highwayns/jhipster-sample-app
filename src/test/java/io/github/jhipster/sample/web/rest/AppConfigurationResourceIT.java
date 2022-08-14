package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.AppConfiguration;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.domain.enumeration.YesNo;
import io.github.jhipster.sample.repository.AppConfigurationRepository;
import io.github.jhipster.sample.service.dto.AppConfigurationDTO;
import io.github.jhipster.sample.service.mapper.AppConfigurationMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppConfigurationResourceIT {

    private static final String DEFAULT_DEFAULT_TIMEZONE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_TIMEZONE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERS_UNDER_MARKET_PERCENT = "AAAAAAAAAA";
    private static final String UPDATED_ORDERS_UNDER_MARKET_PERCENT = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERS_MIN_USD = "AAAAAAAAAA";
    private static final String UPDATED_ORDERS_MIN_USD = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_SENDING_FEE = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_SENDING_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_FRONTEND_BASEURL = "AAAAAAAAAA";
    private static final String UPDATED_FRONTEND_BASEURL = "BBBBBBBBBB";

    private static final String DEFAULT_FRONTEND_DIRROOT = "AAAAAAAAAA";
    private static final String UPDATED_FRONTEND_DIRROOT = "BBBBBBBBBB";

    private static final String DEFAULT_FIAT_WITHDRAW_FEE = "AAAAAAAAAA";
    private static final String UPDATED_FIAT_WITHDRAW_FEE = "BBBBBBBBBB";

    private static final YesNo DEFAULT_API_DB_DEBUG = YesNo.Y;
    private static final YesNo UPDATED_API_DB_DEBUG = YesNo.N;

    private static final String DEFAULT_API_DIRROOT = "AAAAAAAAAA";
    private static final String UPDATED_API_DIRROOT = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_HOST = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_PORT = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_SECURITY = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_SECURITY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_SMTP_SEND_FROM = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_SMTP_SEND_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_ACCOUNTNAME = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_ACCOUNTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_PASSPHRASE = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_PASSPHRASE = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_HOST = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_PORT = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_PROTOCOL = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_PROTOCOL = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHY_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHY_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_HELPDESK_KEY = "AAAAAAAAAA";
    private static final String UPDATED_HELPDESK_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MCRYPT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MCRYPT_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_CONVERSION_FEE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CONVERSION_FEE = "BBBBBBBBBB";

    private static final YesNo DEFAULT_CROSS_CURRENCY_TRADES = YesNo.Y;
    private static final YesNo UPDATED_CROSS_CURRENCY_TRADES = YesNo.N;

    private static final String DEFAULT_BTC_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_BTC_CURRENCY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSIT_BITCOIN_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_BITCOIN_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_FEE_SCHEDULE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_FEE_SCHEDULE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY_BUY_ID = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY_BUY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY_DEPOSIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY_DEPOSIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY_LOGIN_ID = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY_LOGIN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY_SELL_ID = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY_SELL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_HISTORY_WITHDRAW_ID = "AAAAAAAAAA";
    private static final String UPDATED_HISTORY_WITHDRAW_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_TYPE_ASK = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE_ASK = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_AWAITING_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_AWAITING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_CANCELLED_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_CANCELLED_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_COMPLETED_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_COMPLETED_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_TYPE_BID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TYPE_BID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_DEPOSIT_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_DEPOSIT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_PENDING_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_PENDING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_WITHDRAWAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_WITHDRAWAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTIONS_BUY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIONS_BUY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTIONS_SELL_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTIONS_SELL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WITHDRAW_FIAT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WITHDRAW_FIAT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_WITHDRAW_BTC_DESC = "AAAAAAAAAA";
    private static final String UPDATED_WITHDRAW_BTC_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_EMAIL_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FORM_EMAIL_FROM = "BBBBBBBBBB";

    private static final YesNo DEFAULT_EMAIL_NOTIFY_NEW_USERS = YesNo.Y;
    private static final YesNo UPDATED_EMAIL_NOTIFY_NEW_USERS = YesNo.N;

    private static final String DEFAULT_PASS_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_PASS_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_PASS_MIN_CHARS = "AAAAAAAAAA";
    private static final String UPDATED_PASS_MIN_CHARS = "BBBBBBBBBB";

    private static final YesNo DEFAULT_AUTH_DB_DEBUG = YesNo.Y;
    private static final YesNo UPDATED_AUTH_DB_DEBUG = YesNo.N;

    private static final String DEFAULT_BITCOIN_RESERVE_MIN = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_RESERVE_MIN = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_RESERVE_RATIO = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_RESERVE_RATIO = "BBBBBBBBBB";

    private static final String DEFAULT_BITCOIN_WARM_WALLET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BITCOIN_WARM_WALLET_ADDRESS = "BBBBBBBBBB";

    private static final YesNo DEFAULT_CRON_DB_DEBUG = YesNo.Y;
    private static final YesNo UPDATED_CRON_DB_DEBUG = YesNo.N;

    private static final String DEFAULT_QUANDL_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_QUANDL_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CRON_DIRROOT = "AAAAAAAAAA";
    private static final String UPDATED_CRON_DIRROOT = "BBBBBBBBBB";

    private static final YesNo DEFAULT_BACKSTAGE_DB_DEBUG = YesNo.Y;
    private static final YesNo UPDATED_BACKSTAGE_DB_DEBUG = YesNo.N;

    private static final String DEFAULT_BACKSTAGE_DIRROOT = "AAAAAAAAAA";
    private static final String UPDATED_BACKSTAGE_DIRROOT = "BBBBBBBBBB";

    private static final YesNo DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS = YesNo.Y;
    private static final YesNo UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS = YesNo.N;

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CLOUDFLARE_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_CLOUDFLARE_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_RECAPTCH_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_RECAPTCH_API_KEY = "BBBBBBBBBB";

    private static final YesNo DEFAULT_CLOUDFLARE_BLACKLIST = YesNo.Y;
    private static final YesNo UPDATED_CLOUDFLARE_BLACKLIST = YesNo.N;

    private static final String DEFAULT_CLOUDFLARE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CLOUDFLARE_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_RECAPTCH_API_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_RECAPTCH_API_SECRET = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS = 1;
    private static final Integer UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS = 2;

    private static final Double DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME = 1D;
    private static final Double UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME = 2D;

    private static final String DEFAULT_CRYPTO_CAPITAL_PK = "AAAAAAAAAA";
    private static final String UPDATED_CRYPTO_CAPITAL_PK = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSIT_FIAT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_FIAT_DESC = "BBBBBBBBBB";

    private static final YesNo DEFAULT_EMAIL_NOTIFY_FIAT_FAILED = YesNo.Y;
    private static final YesNo UPDATED_EMAIL_NOTIFY_FIAT_FAILED = YesNo.N;

    private static final String DEFAULT_TRADING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_WITHDRAWALS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_WITHDRAWALS_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppConfigurationRepository appConfigurationRepository;

    @Autowired
    private AppConfigurationMapper appConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppConfigurationMockMvc;

    private AppConfiguration appConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfiguration createEntity(EntityManager em) {
        AppConfiguration appConfiguration = new AppConfiguration()
            .defaultTimezone(DEFAULT_DEFAULT_TIMEZONE)
            .ordersUnderMarketPercent(DEFAULT_ORDERS_UNDER_MARKET_PERCENT)
            .ordersMinUsd(DEFAULT_ORDERS_MIN_USD)
            .bitcoinSendingFee(DEFAULT_BITCOIN_SENDING_FEE)
            .frontendBaseurl(DEFAULT_FRONTEND_BASEURL)
            .frontendDirroot(DEFAULT_FRONTEND_DIRROOT)
            .fiatWithdrawFee(DEFAULT_FIAT_WITHDRAW_FEE)
            .apiDbDebug(DEFAULT_API_DB_DEBUG)
            .apiDirroot(DEFAULT_API_DIRROOT)
            .supportEmail(DEFAULT_SUPPORT_EMAIL)
            .emailSmtpHost(DEFAULT_EMAIL_SMTP_HOST)
            .emailSmtpPort(DEFAULT_EMAIL_SMTP_PORT)
            .emailSmtpSecurity(DEFAULT_EMAIL_SMTP_SECURITY)
            .emailSmtpUsername(DEFAULT_EMAIL_SMTP_USERNAME)
            .emailSmtpPassword(DEFAULT_EMAIL_SMTP_PASSWORD)
            .emailSmtpSendFrom(DEFAULT_EMAIL_SMTP_SEND_FROM)
            .bitcoinUsername(DEFAULT_BITCOIN_USERNAME)
            .bitcoinAccountname(DEFAULT_BITCOIN_ACCOUNTNAME)
            .bitcoinPassphrase(DEFAULT_BITCOIN_PASSPHRASE)
            .bitcoinHost(DEFAULT_BITCOIN_HOST)
            .bitcoinPort(DEFAULT_BITCOIN_PORT)
            .bitcoinProtocol(DEFAULT_BITCOIN_PROTOCOL)
            .authyApiKey(DEFAULT_AUTHY_API_KEY)
            .helpdeskKey(DEFAULT_HELPDESK_KEY)
            .exchangeName(DEFAULT_EXCHANGE_NAME)
            .mcryptKey(DEFAULT_MCRYPT_KEY)
            .currencyConversionFee(DEFAULT_CURRENCY_CONVERSION_FEE)
            .crossCurrencyTrades(DEFAULT_CROSS_CURRENCY_TRADES)
            .btcCurrencyId(DEFAULT_BTC_CURRENCY_ID)
            .depositBitcoinDesc(DEFAULT_DEPOSIT_BITCOIN_DESC)
            .defaultFeeScheduleId(DEFAULT_DEFAULT_FEE_SCHEDULE_ID)
            .historyBuyId(DEFAULT_HISTORY_BUY_ID)
            .historyDepositId(DEFAULT_HISTORY_DEPOSIT_ID)
            .historyLoginId(DEFAULT_HISTORY_LOGIN_ID)
            .historySellId(DEFAULT_HISTORY_SELL_ID)
            .historyWithdrawId(DEFAULT_HISTORY_WITHDRAW_ID)
            .orderTypeAsk(DEFAULT_ORDER_TYPE_ASK)
            .requestAwaitingId(DEFAULT_REQUEST_AWAITING_ID)
            .requestCancelledId(DEFAULT_REQUEST_CANCELLED_ID)
            .requestCompletedId(DEFAULT_REQUEST_COMPLETED_ID)
            .orderTypeBid(DEFAULT_ORDER_TYPE_BID)
            .requestDepositId(DEFAULT_REQUEST_DEPOSIT_ID)
            .requestPendingId(DEFAULT_REQUEST_PENDING_ID)
            .requestWithdrawalId(DEFAULT_REQUEST_WITHDRAWAL_ID)
            .transactionsBuyId(DEFAULT_TRANSACTIONS_BUY_ID)
            .transactionsSellId(DEFAULT_TRANSACTIONS_SELL_ID)
            .withdrawFiatDesc(DEFAULT_WITHDRAW_FIAT_DESC)
            .withdrawBtcDesc(DEFAULT_WITHDRAW_BTC_DESC)
            .formEmailFrom(DEFAULT_FORM_EMAIL_FROM)
            .emailNotifyNewUsers(DEFAULT_EMAIL_NOTIFY_NEW_USERS)
            .passRegex(DEFAULT_PASS_REGEX)
            .passMinChars(DEFAULT_PASS_MIN_CHARS)
            .authDbDebug(DEFAULT_AUTH_DB_DEBUG)
            .bitcoinReserveMin(DEFAULT_BITCOIN_RESERVE_MIN)
            .bitcoinReserveRatio(DEFAULT_BITCOIN_RESERVE_RATIO)
            .bitcoinWarmWalletAddress(DEFAULT_BITCOIN_WARM_WALLET_ADDRESS)
            .cronDbDebug(DEFAULT_CRON_DB_DEBUG)
            .quandlApiKey(DEFAULT_QUANDL_API_KEY)
            .cronDirroot(DEFAULT_CRON_DIRROOT)
            .backstageDbDebug(DEFAULT_BACKSTAGE_DB_DEBUG)
            .backstageDirroot(DEFAULT_BACKSTAGE_DIRROOT)
            .emailNotifyFiatWithdrawals(DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .cloudflareApiKey(DEFAULT_CLOUDFLARE_API_KEY)
            .googleRecaptchApiKey(DEFAULT_GOOGLE_RECAPTCH_API_KEY)
            .cloudflareBlacklist(DEFAULT_CLOUDFLARE_BLACKLIST)
            .cloudflareEmail(DEFAULT_CLOUDFLARE_EMAIL)
            .googleRecaptchApiSecret(DEFAULT_GOOGLE_RECAPTCH_API_SECRET)
            .cloudflareBlacklistAttempts(DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS)
            .cloudflareBlacklistTimeframe(DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME)
            .cryptoCapitalPk(DEFAULT_CRYPTO_CAPITAL_PK)
            .depositFiatDesc(DEFAULT_DEPOSIT_FIAT_DESC)
            .emailNotifyFiatFailed(DEFAULT_EMAIL_NOTIFY_FIAT_FAILED)
            .tradingStatus(DEFAULT_TRADING_STATUS)
            .withdrawalsStatus(DEFAULT_WITHDRAWALS_STATUS);
        return appConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppConfiguration createUpdatedEntity(EntityManager em) {
        AppConfiguration appConfiguration = new AppConfiguration()
            .defaultTimezone(UPDATED_DEFAULT_TIMEZONE)
            .ordersUnderMarketPercent(UPDATED_ORDERS_UNDER_MARKET_PERCENT)
            .ordersMinUsd(UPDATED_ORDERS_MIN_USD)
            .bitcoinSendingFee(UPDATED_BITCOIN_SENDING_FEE)
            .frontendBaseurl(UPDATED_FRONTEND_BASEURL)
            .frontendDirroot(UPDATED_FRONTEND_DIRROOT)
            .fiatWithdrawFee(UPDATED_FIAT_WITHDRAW_FEE)
            .apiDbDebug(UPDATED_API_DB_DEBUG)
            .apiDirroot(UPDATED_API_DIRROOT)
            .supportEmail(UPDATED_SUPPORT_EMAIL)
            .emailSmtpHost(UPDATED_EMAIL_SMTP_HOST)
            .emailSmtpPort(UPDATED_EMAIL_SMTP_PORT)
            .emailSmtpSecurity(UPDATED_EMAIL_SMTP_SECURITY)
            .emailSmtpUsername(UPDATED_EMAIL_SMTP_USERNAME)
            .emailSmtpPassword(UPDATED_EMAIL_SMTP_PASSWORD)
            .emailSmtpSendFrom(UPDATED_EMAIL_SMTP_SEND_FROM)
            .bitcoinUsername(UPDATED_BITCOIN_USERNAME)
            .bitcoinAccountname(UPDATED_BITCOIN_ACCOUNTNAME)
            .bitcoinPassphrase(UPDATED_BITCOIN_PASSPHRASE)
            .bitcoinHost(UPDATED_BITCOIN_HOST)
            .bitcoinPort(UPDATED_BITCOIN_PORT)
            .bitcoinProtocol(UPDATED_BITCOIN_PROTOCOL)
            .authyApiKey(UPDATED_AUTHY_API_KEY)
            .helpdeskKey(UPDATED_HELPDESK_KEY)
            .exchangeName(UPDATED_EXCHANGE_NAME)
            .mcryptKey(UPDATED_MCRYPT_KEY)
            .currencyConversionFee(UPDATED_CURRENCY_CONVERSION_FEE)
            .crossCurrencyTrades(UPDATED_CROSS_CURRENCY_TRADES)
            .btcCurrencyId(UPDATED_BTC_CURRENCY_ID)
            .depositBitcoinDesc(UPDATED_DEPOSIT_BITCOIN_DESC)
            .defaultFeeScheduleId(UPDATED_DEFAULT_FEE_SCHEDULE_ID)
            .historyBuyId(UPDATED_HISTORY_BUY_ID)
            .historyDepositId(UPDATED_HISTORY_DEPOSIT_ID)
            .historyLoginId(UPDATED_HISTORY_LOGIN_ID)
            .historySellId(UPDATED_HISTORY_SELL_ID)
            .historyWithdrawId(UPDATED_HISTORY_WITHDRAW_ID)
            .orderTypeAsk(UPDATED_ORDER_TYPE_ASK)
            .requestAwaitingId(UPDATED_REQUEST_AWAITING_ID)
            .requestCancelledId(UPDATED_REQUEST_CANCELLED_ID)
            .requestCompletedId(UPDATED_REQUEST_COMPLETED_ID)
            .orderTypeBid(UPDATED_ORDER_TYPE_BID)
            .requestDepositId(UPDATED_REQUEST_DEPOSIT_ID)
            .requestPendingId(UPDATED_REQUEST_PENDING_ID)
            .requestWithdrawalId(UPDATED_REQUEST_WITHDRAWAL_ID)
            .transactionsBuyId(UPDATED_TRANSACTIONS_BUY_ID)
            .transactionsSellId(UPDATED_TRANSACTIONS_SELL_ID)
            .withdrawFiatDesc(UPDATED_WITHDRAW_FIAT_DESC)
            .withdrawBtcDesc(UPDATED_WITHDRAW_BTC_DESC)
            .formEmailFrom(UPDATED_FORM_EMAIL_FROM)
            .emailNotifyNewUsers(UPDATED_EMAIL_NOTIFY_NEW_USERS)
            .passRegex(UPDATED_PASS_REGEX)
            .passMinChars(UPDATED_PASS_MIN_CHARS)
            .authDbDebug(UPDATED_AUTH_DB_DEBUG)
            .bitcoinReserveMin(UPDATED_BITCOIN_RESERVE_MIN)
            .bitcoinReserveRatio(UPDATED_BITCOIN_RESERVE_RATIO)
            .bitcoinWarmWalletAddress(UPDATED_BITCOIN_WARM_WALLET_ADDRESS)
            .cronDbDebug(UPDATED_CRON_DB_DEBUG)
            .quandlApiKey(UPDATED_QUANDL_API_KEY)
            .cronDirroot(UPDATED_CRON_DIRROOT)
            .backstageDbDebug(UPDATED_BACKSTAGE_DB_DEBUG)
            .backstageDirroot(UPDATED_BACKSTAGE_DIRROOT)
            .emailNotifyFiatWithdrawals(UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .cloudflareApiKey(UPDATED_CLOUDFLARE_API_KEY)
            .googleRecaptchApiKey(UPDATED_GOOGLE_RECAPTCH_API_KEY)
            .cloudflareBlacklist(UPDATED_CLOUDFLARE_BLACKLIST)
            .cloudflareEmail(UPDATED_CLOUDFLARE_EMAIL)
            .googleRecaptchApiSecret(UPDATED_GOOGLE_RECAPTCH_API_SECRET)
            .cloudflareBlacklistAttempts(UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS)
            .cloudflareBlacklistTimeframe(UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME)
            .cryptoCapitalPk(UPDATED_CRYPTO_CAPITAL_PK)
            .depositFiatDesc(UPDATED_DEPOSIT_FIAT_DESC)
            .emailNotifyFiatFailed(UPDATED_EMAIL_NOTIFY_FIAT_FAILED)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS);
        return appConfiguration;
    }

    @BeforeEach
    public void initTest() {
        appConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    void createAppConfiguration() throws Exception {
        int databaseSizeBeforeCreate = appConfigurationRepository.findAll().size();
        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);
        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getDefaultTimezone()).isEqualTo(DEFAULT_DEFAULT_TIMEZONE);
        assertThat(testAppConfiguration.getOrdersUnderMarketPercent()).isEqualTo(DEFAULT_ORDERS_UNDER_MARKET_PERCENT);
        assertThat(testAppConfiguration.getOrdersMinUsd()).isEqualTo(DEFAULT_ORDERS_MIN_USD);
        assertThat(testAppConfiguration.getBitcoinSendingFee()).isEqualTo(DEFAULT_BITCOIN_SENDING_FEE);
        assertThat(testAppConfiguration.getFrontendBaseurl()).isEqualTo(DEFAULT_FRONTEND_BASEURL);
        assertThat(testAppConfiguration.getFrontendDirroot()).isEqualTo(DEFAULT_FRONTEND_DIRROOT);
        assertThat(testAppConfiguration.getFiatWithdrawFee()).isEqualTo(DEFAULT_FIAT_WITHDRAW_FEE);
        assertThat(testAppConfiguration.getApiDbDebug()).isEqualTo(DEFAULT_API_DB_DEBUG);
        assertThat(testAppConfiguration.getApiDirroot()).isEqualTo(DEFAULT_API_DIRROOT);
        assertThat(testAppConfiguration.getSupportEmail()).isEqualTo(DEFAULT_SUPPORT_EMAIL);
        assertThat(testAppConfiguration.getEmailSmtpHost()).isEqualTo(DEFAULT_EMAIL_SMTP_HOST);
        assertThat(testAppConfiguration.getEmailSmtpPort()).isEqualTo(DEFAULT_EMAIL_SMTP_PORT);
        assertThat(testAppConfiguration.getEmailSmtpSecurity()).isEqualTo(DEFAULT_EMAIL_SMTP_SECURITY);
        assertThat(testAppConfiguration.getEmailSmtpUsername()).isEqualTo(DEFAULT_EMAIL_SMTP_USERNAME);
        assertThat(testAppConfiguration.getEmailSmtpPassword()).isEqualTo(DEFAULT_EMAIL_SMTP_PASSWORD);
        assertThat(testAppConfiguration.getEmailSmtpSendFrom()).isEqualTo(DEFAULT_EMAIL_SMTP_SEND_FROM);
        assertThat(testAppConfiguration.getBitcoinUsername()).isEqualTo(DEFAULT_BITCOIN_USERNAME);
        assertThat(testAppConfiguration.getBitcoinAccountname()).isEqualTo(DEFAULT_BITCOIN_ACCOUNTNAME);
        assertThat(testAppConfiguration.getBitcoinPassphrase()).isEqualTo(DEFAULT_BITCOIN_PASSPHRASE);
        assertThat(testAppConfiguration.getBitcoinHost()).isEqualTo(DEFAULT_BITCOIN_HOST);
        assertThat(testAppConfiguration.getBitcoinPort()).isEqualTo(DEFAULT_BITCOIN_PORT);
        assertThat(testAppConfiguration.getBitcoinProtocol()).isEqualTo(DEFAULT_BITCOIN_PROTOCOL);
        assertThat(testAppConfiguration.getAuthyApiKey()).isEqualTo(DEFAULT_AUTHY_API_KEY);
        assertThat(testAppConfiguration.getHelpdeskKey()).isEqualTo(DEFAULT_HELPDESK_KEY);
        assertThat(testAppConfiguration.getExchangeName()).isEqualTo(DEFAULT_EXCHANGE_NAME);
        assertThat(testAppConfiguration.getMcryptKey()).isEqualTo(DEFAULT_MCRYPT_KEY);
        assertThat(testAppConfiguration.getCurrencyConversionFee()).isEqualTo(DEFAULT_CURRENCY_CONVERSION_FEE);
        assertThat(testAppConfiguration.getCrossCurrencyTrades()).isEqualTo(DEFAULT_CROSS_CURRENCY_TRADES);
        assertThat(testAppConfiguration.getBtcCurrencyId()).isEqualTo(DEFAULT_BTC_CURRENCY_ID);
        assertThat(testAppConfiguration.getDepositBitcoinDesc()).isEqualTo(DEFAULT_DEPOSIT_BITCOIN_DESC);
        assertThat(testAppConfiguration.getDefaultFeeScheduleId()).isEqualTo(DEFAULT_DEFAULT_FEE_SCHEDULE_ID);
        assertThat(testAppConfiguration.getHistoryBuyId()).isEqualTo(DEFAULT_HISTORY_BUY_ID);
        assertThat(testAppConfiguration.getHistoryDepositId()).isEqualTo(DEFAULT_HISTORY_DEPOSIT_ID);
        assertThat(testAppConfiguration.getHistoryLoginId()).isEqualTo(DEFAULT_HISTORY_LOGIN_ID);
        assertThat(testAppConfiguration.getHistorySellId()).isEqualTo(DEFAULT_HISTORY_SELL_ID);
        assertThat(testAppConfiguration.getHistoryWithdrawId()).isEqualTo(DEFAULT_HISTORY_WITHDRAW_ID);
        assertThat(testAppConfiguration.getOrderTypeAsk()).isEqualTo(DEFAULT_ORDER_TYPE_ASK);
        assertThat(testAppConfiguration.getRequestAwaitingId()).isEqualTo(DEFAULT_REQUEST_AWAITING_ID);
        assertThat(testAppConfiguration.getRequestCancelledId()).isEqualTo(DEFAULT_REQUEST_CANCELLED_ID);
        assertThat(testAppConfiguration.getRequestCompletedId()).isEqualTo(DEFAULT_REQUEST_COMPLETED_ID);
        assertThat(testAppConfiguration.getOrderTypeBid()).isEqualTo(DEFAULT_ORDER_TYPE_BID);
        assertThat(testAppConfiguration.getRequestDepositId()).isEqualTo(DEFAULT_REQUEST_DEPOSIT_ID);
        assertThat(testAppConfiguration.getRequestPendingId()).isEqualTo(DEFAULT_REQUEST_PENDING_ID);
        assertThat(testAppConfiguration.getRequestWithdrawalId()).isEqualTo(DEFAULT_REQUEST_WITHDRAWAL_ID);
        assertThat(testAppConfiguration.getTransactionsBuyId()).isEqualTo(DEFAULT_TRANSACTIONS_BUY_ID);
        assertThat(testAppConfiguration.getTransactionsSellId()).isEqualTo(DEFAULT_TRANSACTIONS_SELL_ID);
        assertThat(testAppConfiguration.getWithdrawFiatDesc()).isEqualTo(DEFAULT_WITHDRAW_FIAT_DESC);
        assertThat(testAppConfiguration.getWithdrawBtcDesc()).isEqualTo(DEFAULT_WITHDRAW_BTC_DESC);
        assertThat(testAppConfiguration.getFormEmailFrom()).isEqualTo(DEFAULT_FORM_EMAIL_FROM);
        assertThat(testAppConfiguration.getEmailNotifyNewUsers()).isEqualTo(DEFAULT_EMAIL_NOTIFY_NEW_USERS);
        assertThat(testAppConfiguration.getPassRegex()).isEqualTo(DEFAULT_PASS_REGEX);
        assertThat(testAppConfiguration.getPassMinChars()).isEqualTo(DEFAULT_PASS_MIN_CHARS);
        assertThat(testAppConfiguration.getAuthDbDebug()).isEqualTo(DEFAULT_AUTH_DB_DEBUG);
        assertThat(testAppConfiguration.getBitcoinReserveMin()).isEqualTo(DEFAULT_BITCOIN_RESERVE_MIN);
        assertThat(testAppConfiguration.getBitcoinReserveRatio()).isEqualTo(DEFAULT_BITCOIN_RESERVE_RATIO);
        assertThat(testAppConfiguration.getBitcoinWarmWalletAddress()).isEqualTo(DEFAULT_BITCOIN_WARM_WALLET_ADDRESS);
        assertThat(testAppConfiguration.getCronDbDebug()).isEqualTo(DEFAULT_CRON_DB_DEBUG);
        assertThat(testAppConfiguration.getQuandlApiKey()).isEqualTo(DEFAULT_QUANDL_API_KEY);
        assertThat(testAppConfiguration.getCronDirroot()).isEqualTo(DEFAULT_CRON_DIRROOT);
        assertThat(testAppConfiguration.getBackstageDbDebug()).isEqualTo(DEFAULT_BACKSTAGE_DB_DEBUG);
        assertThat(testAppConfiguration.getBackstageDirroot()).isEqualTo(DEFAULT_BACKSTAGE_DIRROOT);
        assertThat(testAppConfiguration.getEmailNotifyFiatWithdrawals()).isEqualTo(DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS);
        assertThat(testAppConfiguration.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testAppConfiguration.getCloudflareApiKey()).isEqualTo(DEFAULT_CLOUDFLARE_API_KEY);
        assertThat(testAppConfiguration.getGoogleRecaptchApiKey()).isEqualTo(DEFAULT_GOOGLE_RECAPTCH_API_KEY);
        assertThat(testAppConfiguration.getCloudflareBlacklist()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST);
        assertThat(testAppConfiguration.getCloudflareEmail()).isEqualTo(DEFAULT_CLOUDFLARE_EMAIL);
        assertThat(testAppConfiguration.getGoogleRecaptchApiSecret()).isEqualTo(DEFAULT_GOOGLE_RECAPTCH_API_SECRET);
        assertThat(testAppConfiguration.getCloudflareBlacklistAttempts()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS);
        assertThat(testAppConfiguration.getCloudflareBlacklistTimeframe()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME);
        assertThat(testAppConfiguration.getCryptoCapitalPk()).isEqualTo(DEFAULT_CRYPTO_CAPITAL_PK);
        assertThat(testAppConfiguration.getDepositFiatDesc()).isEqualTo(DEFAULT_DEPOSIT_FIAT_DESC);
        assertThat(testAppConfiguration.getEmailNotifyFiatFailed()).isEqualTo(DEFAULT_EMAIL_NOTIFY_FIAT_FAILED);
        assertThat(testAppConfiguration.getTradingStatus()).isEqualTo(DEFAULT_TRADING_STATUS);
        assertThat(testAppConfiguration.getWithdrawalsStatus()).isEqualTo(DEFAULT_WITHDRAWALS_STATUS);
    }

    @Test
    @Transactional
    void createAppConfigurationWithExistingId() throws Exception {
        // Create the AppConfiguration with an existing ID
        appConfiguration.setId(1L);
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        int databaseSizeBeforeCreate = appConfigurationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDefaultTimezoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setDefaultTimezone(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrdersUnderMarketPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setOrdersUnderMarketPercent(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrdersMinUsdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setOrdersMinUsd(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinSendingFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinSendingFee(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrontendBaseurlIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setFrontendBaseurl(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrontendDirrootIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setFrontendDirroot(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFiatWithdrawFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setFiatWithdrawFee(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApiDbDebugIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setApiDbDebug(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApiDirrootIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setApiDirroot(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupportEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setSupportEmail(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpHost(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpPort(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpSecurityIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpSecurity(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpUsername(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpPassword(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailSmtpSendFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailSmtpSendFrom(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinUsername(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinAccountnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinAccountname(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinPassphraseIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinPassphrase(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinHost(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinPort(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinProtocolIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinProtocol(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthyApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setAuthyApiKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHelpdeskKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHelpdeskKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExchangeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setExchangeName(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMcryptKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setMcryptKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyConversionFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCurrencyConversionFee(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCrossCurrencyTradesIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCrossCurrencyTrades(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBtcCurrencyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBtcCurrencyId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepositBitcoinDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setDepositBitcoinDesc(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDefaultFeeScheduleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setDefaultFeeScheduleId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHistoryBuyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHistoryBuyId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHistoryDepositIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHistoryDepositId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHistoryLoginIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHistoryLoginId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHistorySellIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHistorySellId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHistoryWithdrawIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setHistoryWithdrawId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderTypeAskIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setOrderTypeAsk(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestAwaitingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestAwaitingId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestCancelledIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestCancelledId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestCompletedIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestCompletedId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderTypeBidIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setOrderTypeBid(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestDepositIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestDepositId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestPendingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestPendingId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRequestWithdrawalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setRequestWithdrawalId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionsBuyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setTransactionsBuyId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionsSellIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setTransactionsSellId(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWithdrawFiatDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setWithdrawFiatDesc(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWithdrawBtcDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setWithdrawBtcDesc(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormEmailFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setFormEmailFrom(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailNotifyNewUsersIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailNotifyNewUsers(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassRegexIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setPassRegex(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassMinCharsIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setPassMinChars(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthDbDebugIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setAuthDbDebug(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinReserveMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinReserveMin(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinReserveRatioIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinReserveRatio(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBitcoinWarmWalletAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBitcoinWarmWalletAddress(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronDbDebugIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCronDbDebug(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuandlApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setQuandlApiKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCronDirrootIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCronDirroot(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBackstageDbDebugIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBackstageDbDebug(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBackstageDirrootIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setBackstageDirroot(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailNotifyFiatWithdrawalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailNotifyFiatWithdrawals(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setContactEmail(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloudflareApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCloudflareApiKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoogleRecaptchApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setGoogleRecaptchApiKey(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloudflareBlacklistIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCloudflareBlacklist(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloudflareEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCloudflareEmail(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoogleRecaptchApiSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setGoogleRecaptchApiSecret(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloudflareBlacklistAttemptsIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCloudflareBlacklistAttempts(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCloudflareBlacklistTimeframeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCloudflareBlacklistTimeframe(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCryptoCapitalPkIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setCryptoCapitalPk(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepositFiatDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setDepositFiatDesc(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailNotifyFiatFailedIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setEmailNotifyFiatFailed(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setTradingStatus(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWithdrawalsStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfigurationRepository.findAll().size();
        // set the field null
        appConfiguration.setWithdrawalsStatus(null);

        // Create the AppConfiguration, which fails.
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        restAppConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppConfigurations() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        // Get all the appConfigurationList
        restAppConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultTimezone").value(hasItem(DEFAULT_DEFAULT_TIMEZONE)))
            .andExpect(jsonPath("$.[*].ordersUnderMarketPercent").value(hasItem(DEFAULT_ORDERS_UNDER_MARKET_PERCENT)))
            .andExpect(jsonPath("$.[*].ordersMinUsd").value(hasItem(DEFAULT_ORDERS_MIN_USD)))
            .andExpect(jsonPath("$.[*].bitcoinSendingFee").value(hasItem(DEFAULT_BITCOIN_SENDING_FEE)))
            .andExpect(jsonPath("$.[*].frontendBaseurl").value(hasItem(DEFAULT_FRONTEND_BASEURL)))
            .andExpect(jsonPath("$.[*].frontendDirroot").value(hasItem(DEFAULT_FRONTEND_DIRROOT)))
            .andExpect(jsonPath("$.[*].fiatWithdrawFee").value(hasItem(DEFAULT_FIAT_WITHDRAW_FEE)))
            .andExpect(jsonPath("$.[*].apiDbDebug").value(hasItem(DEFAULT_API_DB_DEBUG.toString())))
            .andExpect(jsonPath("$.[*].apiDirroot").value(hasItem(DEFAULT_API_DIRROOT)))
            .andExpect(jsonPath("$.[*].supportEmail").value(hasItem(DEFAULT_SUPPORT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailSmtpHost").value(hasItem(DEFAULT_EMAIL_SMTP_HOST)))
            .andExpect(jsonPath("$.[*].emailSmtpPort").value(hasItem(DEFAULT_EMAIL_SMTP_PORT)))
            .andExpect(jsonPath("$.[*].emailSmtpSecurity").value(hasItem(DEFAULT_EMAIL_SMTP_SECURITY)))
            .andExpect(jsonPath("$.[*].emailSmtpUsername").value(hasItem(DEFAULT_EMAIL_SMTP_USERNAME)))
            .andExpect(jsonPath("$.[*].emailSmtpPassword").value(hasItem(DEFAULT_EMAIL_SMTP_PASSWORD)))
            .andExpect(jsonPath("$.[*].emailSmtpSendFrom").value(hasItem(DEFAULT_EMAIL_SMTP_SEND_FROM)))
            .andExpect(jsonPath("$.[*].bitcoinUsername").value(hasItem(DEFAULT_BITCOIN_USERNAME)))
            .andExpect(jsonPath("$.[*].bitcoinAccountname").value(hasItem(DEFAULT_BITCOIN_ACCOUNTNAME)))
            .andExpect(jsonPath("$.[*].bitcoinPassphrase").value(hasItem(DEFAULT_BITCOIN_PASSPHRASE)))
            .andExpect(jsonPath("$.[*].bitcoinHost").value(hasItem(DEFAULT_BITCOIN_HOST)))
            .andExpect(jsonPath("$.[*].bitcoinPort").value(hasItem(DEFAULT_BITCOIN_PORT)))
            .andExpect(jsonPath("$.[*].bitcoinProtocol").value(hasItem(DEFAULT_BITCOIN_PROTOCOL)))
            .andExpect(jsonPath("$.[*].authyApiKey").value(hasItem(DEFAULT_AUTHY_API_KEY)))
            .andExpect(jsonPath("$.[*].helpdeskKey").value(hasItem(DEFAULT_HELPDESK_KEY)))
            .andExpect(jsonPath("$.[*].exchangeName").value(hasItem(DEFAULT_EXCHANGE_NAME)))
            .andExpect(jsonPath("$.[*].mcryptKey").value(hasItem(DEFAULT_MCRYPT_KEY)))
            .andExpect(jsonPath("$.[*].currencyConversionFee").value(hasItem(DEFAULT_CURRENCY_CONVERSION_FEE)))
            .andExpect(jsonPath("$.[*].crossCurrencyTrades").value(hasItem(DEFAULT_CROSS_CURRENCY_TRADES.toString())))
            .andExpect(jsonPath("$.[*].btcCurrencyId").value(hasItem(DEFAULT_BTC_CURRENCY_ID)))
            .andExpect(jsonPath("$.[*].depositBitcoinDesc").value(hasItem(DEFAULT_DEPOSIT_BITCOIN_DESC)))
            .andExpect(jsonPath("$.[*].defaultFeeScheduleId").value(hasItem(DEFAULT_DEFAULT_FEE_SCHEDULE_ID)))
            .andExpect(jsonPath("$.[*].historyBuyId").value(hasItem(DEFAULT_HISTORY_BUY_ID)))
            .andExpect(jsonPath("$.[*].historyDepositId").value(hasItem(DEFAULT_HISTORY_DEPOSIT_ID)))
            .andExpect(jsonPath("$.[*].historyLoginId").value(hasItem(DEFAULT_HISTORY_LOGIN_ID)))
            .andExpect(jsonPath("$.[*].historySellId").value(hasItem(DEFAULT_HISTORY_SELL_ID)))
            .andExpect(jsonPath("$.[*].historyWithdrawId").value(hasItem(DEFAULT_HISTORY_WITHDRAW_ID)))
            .andExpect(jsonPath("$.[*].orderTypeAsk").value(hasItem(DEFAULT_ORDER_TYPE_ASK)))
            .andExpect(jsonPath("$.[*].requestAwaitingId").value(hasItem(DEFAULT_REQUEST_AWAITING_ID)))
            .andExpect(jsonPath("$.[*].requestCancelledId").value(hasItem(DEFAULT_REQUEST_CANCELLED_ID)))
            .andExpect(jsonPath("$.[*].requestCompletedId").value(hasItem(DEFAULT_REQUEST_COMPLETED_ID)))
            .andExpect(jsonPath("$.[*].orderTypeBid").value(hasItem(DEFAULT_ORDER_TYPE_BID)))
            .andExpect(jsonPath("$.[*].requestDepositId").value(hasItem(DEFAULT_REQUEST_DEPOSIT_ID)))
            .andExpect(jsonPath("$.[*].requestPendingId").value(hasItem(DEFAULT_REQUEST_PENDING_ID)))
            .andExpect(jsonPath("$.[*].requestWithdrawalId").value(hasItem(DEFAULT_REQUEST_WITHDRAWAL_ID)))
            .andExpect(jsonPath("$.[*].transactionsBuyId").value(hasItem(DEFAULT_TRANSACTIONS_BUY_ID)))
            .andExpect(jsonPath("$.[*].transactionsSellId").value(hasItem(DEFAULT_TRANSACTIONS_SELL_ID)))
            .andExpect(jsonPath("$.[*].withdrawFiatDesc").value(hasItem(DEFAULT_WITHDRAW_FIAT_DESC)))
            .andExpect(jsonPath("$.[*].withdrawBtcDesc").value(hasItem(DEFAULT_WITHDRAW_BTC_DESC)))
            .andExpect(jsonPath("$.[*].formEmailFrom").value(hasItem(DEFAULT_FORM_EMAIL_FROM)))
            .andExpect(jsonPath("$.[*].emailNotifyNewUsers").value(hasItem(DEFAULT_EMAIL_NOTIFY_NEW_USERS.toString())))
            .andExpect(jsonPath("$.[*].passRegex").value(hasItem(DEFAULT_PASS_REGEX)))
            .andExpect(jsonPath("$.[*].passMinChars").value(hasItem(DEFAULT_PASS_MIN_CHARS)))
            .andExpect(jsonPath("$.[*].authDbDebug").value(hasItem(DEFAULT_AUTH_DB_DEBUG.toString())))
            .andExpect(jsonPath("$.[*].bitcoinReserveMin").value(hasItem(DEFAULT_BITCOIN_RESERVE_MIN)))
            .andExpect(jsonPath("$.[*].bitcoinReserveRatio").value(hasItem(DEFAULT_BITCOIN_RESERVE_RATIO)))
            .andExpect(jsonPath("$.[*].bitcoinWarmWalletAddress").value(hasItem(DEFAULT_BITCOIN_WARM_WALLET_ADDRESS)))
            .andExpect(jsonPath("$.[*].cronDbDebug").value(hasItem(DEFAULT_CRON_DB_DEBUG.toString())))
            .andExpect(jsonPath("$.[*].quandlApiKey").value(hasItem(DEFAULT_QUANDL_API_KEY)))
            .andExpect(jsonPath("$.[*].cronDirroot").value(hasItem(DEFAULT_CRON_DIRROOT)))
            .andExpect(jsonPath("$.[*].backstageDbDebug").value(hasItem(DEFAULT_BACKSTAGE_DB_DEBUG.toString())))
            .andExpect(jsonPath("$.[*].backstageDirroot").value(hasItem(DEFAULT_BACKSTAGE_DIRROOT)))
            .andExpect(jsonPath("$.[*].emailNotifyFiatWithdrawals").value(hasItem(DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].cloudflareApiKey").value(hasItem(DEFAULT_CLOUDFLARE_API_KEY)))
            .andExpect(jsonPath("$.[*].googleRecaptchApiKey").value(hasItem(DEFAULT_GOOGLE_RECAPTCH_API_KEY)))
            .andExpect(jsonPath("$.[*].cloudflareBlacklist").value(hasItem(DEFAULT_CLOUDFLARE_BLACKLIST.toString())))
            .andExpect(jsonPath("$.[*].cloudflareEmail").value(hasItem(DEFAULT_CLOUDFLARE_EMAIL)))
            .andExpect(jsonPath("$.[*].googleRecaptchApiSecret").value(hasItem(DEFAULT_GOOGLE_RECAPTCH_API_SECRET)))
            .andExpect(jsonPath("$.[*].cloudflareBlacklistAttempts").value(hasItem(DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS)))
            .andExpect(jsonPath("$.[*].cloudflareBlacklistTimeframe").value(hasItem(DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME.doubleValue())))
            .andExpect(jsonPath("$.[*].cryptoCapitalPk").value(hasItem(DEFAULT_CRYPTO_CAPITAL_PK)))
            .andExpect(jsonPath("$.[*].depositFiatDesc").value(hasItem(DEFAULT_DEPOSIT_FIAT_DESC)))
            .andExpect(jsonPath("$.[*].emailNotifyFiatFailed").value(hasItem(DEFAULT_EMAIL_NOTIFY_FIAT_FAILED.toString())))
            .andExpect(jsonPath("$.[*].tradingStatus").value(hasItem(DEFAULT_TRADING_STATUS)))
            .andExpect(jsonPath("$.[*].withdrawalsStatus").value(hasItem(DEFAULT_WITHDRAWALS_STATUS)));
    }

    @Test
    @Transactional
    void getAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        // Get the appConfiguration
        restAppConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, appConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.defaultTimezone").value(DEFAULT_DEFAULT_TIMEZONE))
            .andExpect(jsonPath("$.ordersUnderMarketPercent").value(DEFAULT_ORDERS_UNDER_MARKET_PERCENT))
            .andExpect(jsonPath("$.ordersMinUsd").value(DEFAULT_ORDERS_MIN_USD))
            .andExpect(jsonPath("$.bitcoinSendingFee").value(DEFAULT_BITCOIN_SENDING_FEE))
            .andExpect(jsonPath("$.frontendBaseurl").value(DEFAULT_FRONTEND_BASEURL))
            .andExpect(jsonPath("$.frontendDirroot").value(DEFAULT_FRONTEND_DIRROOT))
            .andExpect(jsonPath("$.fiatWithdrawFee").value(DEFAULT_FIAT_WITHDRAW_FEE))
            .andExpect(jsonPath("$.apiDbDebug").value(DEFAULT_API_DB_DEBUG.toString()))
            .andExpect(jsonPath("$.apiDirroot").value(DEFAULT_API_DIRROOT))
            .andExpect(jsonPath("$.supportEmail").value(DEFAULT_SUPPORT_EMAIL))
            .andExpect(jsonPath("$.emailSmtpHost").value(DEFAULT_EMAIL_SMTP_HOST))
            .andExpect(jsonPath("$.emailSmtpPort").value(DEFAULT_EMAIL_SMTP_PORT))
            .andExpect(jsonPath("$.emailSmtpSecurity").value(DEFAULT_EMAIL_SMTP_SECURITY))
            .andExpect(jsonPath("$.emailSmtpUsername").value(DEFAULT_EMAIL_SMTP_USERNAME))
            .andExpect(jsonPath("$.emailSmtpPassword").value(DEFAULT_EMAIL_SMTP_PASSWORD))
            .andExpect(jsonPath("$.emailSmtpSendFrom").value(DEFAULT_EMAIL_SMTP_SEND_FROM))
            .andExpect(jsonPath("$.bitcoinUsername").value(DEFAULT_BITCOIN_USERNAME))
            .andExpect(jsonPath("$.bitcoinAccountname").value(DEFAULT_BITCOIN_ACCOUNTNAME))
            .andExpect(jsonPath("$.bitcoinPassphrase").value(DEFAULT_BITCOIN_PASSPHRASE))
            .andExpect(jsonPath("$.bitcoinHost").value(DEFAULT_BITCOIN_HOST))
            .andExpect(jsonPath("$.bitcoinPort").value(DEFAULT_BITCOIN_PORT))
            .andExpect(jsonPath("$.bitcoinProtocol").value(DEFAULT_BITCOIN_PROTOCOL))
            .andExpect(jsonPath("$.authyApiKey").value(DEFAULT_AUTHY_API_KEY))
            .andExpect(jsonPath("$.helpdeskKey").value(DEFAULT_HELPDESK_KEY))
            .andExpect(jsonPath("$.exchangeName").value(DEFAULT_EXCHANGE_NAME))
            .andExpect(jsonPath("$.mcryptKey").value(DEFAULT_MCRYPT_KEY))
            .andExpect(jsonPath("$.currencyConversionFee").value(DEFAULT_CURRENCY_CONVERSION_FEE))
            .andExpect(jsonPath("$.crossCurrencyTrades").value(DEFAULT_CROSS_CURRENCY_TRADES.toString()))
            .andExpect(jsonPath("$.btcCurrencyId").value(DEFAULT_BTC_CURRENCY_ID))
            .andExpect(jsonPath("$.depositBitcoinDesc").value(DEFAULT_DEPOSIT_BITCOIN_DESC))
            .andExpect(jsonPath("$.defaultFeeScheduleId").value(DEFAULT_DEFAULT_FEE_SCHEDULE_ID))
            .andExpect(jsonPath("$.historyBuyId").value(DEFAULT_HISTORY_BUY_ID))
            .andExpect(jsonPath("$.historyDepositId").value(DEFAULT_HISTORY_DEPOSIT_ID))
            .andExpect(jsonPath("$.historyLoginId").value(DEFAULT_HISTORY_LOGIN_ID))
            .andExpect(jsonPath("$.historySellId").value(DEFAULT_HISTORY_SELL_ID))
            .andExpect(jsonPath("$.historyWithdrawId").value(DEFAULT_HISTORY_WITHDRAW_ID))
            .andExpect(jsonPath("$.orderTypeAsk").value(DEFAULT_ORDER_TYPE_ASK))
            .andExpect(jsonPath("$.requestAwaitingId").value(DEFAULT_REQUEST_AWAITING_ID))
            .andExpect(jsonPath("$.requestCancelledId").value(DEFAULT_REQUEST_CANCELLED_ID))
            .andExpect(jsonPath("$.requestCompletedId").value(DEFAULT_REQUEST_COMPLETED_ID))
            .andExpect(jsonPath("$.orderTypeBid").value(DEFAULT_ORDER_TYPE_BID))
            .andExpect(jsonPath("$.requestDepositId").value(DEFAULT_REQUEST_DEPOSIT_ID))
            .andExpect(jsonPath("$.requestPendingId").value(DEFAULT_REQUEST_PENDING_ID))
            .andExpect(jsonPath("$.requestWithdrawalId").value(DEFAULT_REQUEST_WITHDRAWAL_ID))
            .andExpect(jsonPath("$.transactionsBuyId").value(DEFAULT_TRANSACTIONS_BUY_ID))
            .andExpect(jsonPath("$.transactionsSellId").value(DEFAULT_TRANSACTIONS_SELL_ID))
            .andExpect(jsonPath("$.withdrawFiatDesc").value(DEFAULT_WITHDRAW_FIAT_DESC))
            .andExpect(jsonPath("$.withdrawBtcDesc").value(DEFAULT_WITHDRAW_BTC_DESC))
            .andExpect(jsonPath("$.formEmailFrom").value(DEFAULT_FORM_EMAIL_FROM))
            .andExpect(jsonPath("$.emailNotifyNewUsers").value(DEFAULT_EMAIL_NOTIFY_NEW_USERS.toString()))
            .andExpect(jsonPath("$.passRegex").value(DEFAULT_PASS_REGEX))
            .andExpect(jsonPath("$.passMinChars").value(DEFAULT_PASS_MIN_CHARS))
            .andExpect(jsonPath("$.authDbDebug").value(DEFAULT_AUTH_DB_DEBUG.toString()))
            .andExpect(jsonPath("$.bitcoinReserveMin").value(DEFAULT_BITCOIN_RESERVE_MIN))
            .andExpect(jsonPath("$.bitcoinReserveRatio").value(DEFAULT_BITCOIN_RESERVE_RATIO))
            .andExpect(jsonPath("$.bitcoinWarmWalletAddress").value(DEFAULT_BITCOIN_WARM_WALLET_ADDRESS))
            .andExpect(jsonPath("$.cronDbDebug").value(DEFAULT_CRON_DB_DEBUG.toString()))
            .andExpect(jsonPath("$.quandlApiKey").value(DEFAULT_QUANDL_API_KEY))
            .andExpect(jsonPath("$.cronDirroot").value(DEFAULT_CRON_DIRROOT))
            .andExpect(jsonPath("$.backstageDbDebug").value(DEFAULT_BACKSTAGE_DB_DEBUG.toString()))
            .andExpect(jsonPath("$.backstageDirroot").value(DEFAULT_BACKSTAGE_DIRROOT))
            .andExpect(jsonPath("$.emailNotifyFiatWithdrawals").value(DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS.toString()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.cloudflareApiKey").value(DEFAULT_CLOUDFLARE_API_KEY))
            .andExpect(jsonPath("$.googleRecaptchApiKey").value(DEFAULT_GOOGLE_RECAPTCH_API_KEY))
            .andExpect(jsonPath("$.cloudflareBlacklist").value(DEFAULT_CLOUDFLARE_BLACKLIST.toString()))
            .andExpect(jsonPath("$.cloudflareEmail").value(DEFAULT_CLOUDFLARE_EMAIL))
            .andExpect(jsonPath("$.googleRecaptchApiSecret").value(DEFAULT_GOOGLE_RECAPTCH_API_SECRET))
            .andExpect(jsonPath("$.cloudflareBlacklistAttempts").value(DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS))
            .andExpect(jsonPath("$.cloudflareBlacklistTimeframe").value(DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME.doubleValue()))
            .andExpect(jsonPath("$.cryptoCapitalPk").value(DEFAULT_CRYPTO_CAPITAL_PK))
            .andExpect(jsonPath("$.depositFiatDesc").value(DEFAULT_DEPOSIT_FIAT_DESC))
            .andExpect(jsonPath("$.emailNotifyFiatFailed").value(DEFAULT_EMAIL_NOTIFY_FIAT_FAILED.toString()))
            .andExpect(jsonPath("$.tradingStatus").value(DEFAULT_TRADING_STATUS))
            .andExpect(jsonPath("$.withdrawalsStatus").value(DEFAULT_WITHDRAWALS_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingAppConfiguration() throws Exception {
        // Get the appConfiguration
        restAppConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();

        // Update the appConfiguration
        AppConfiguration updatedAppConfiguration = appConfigurationRepository.findById(appConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedAppConfiguration are not directly saved in db
        em.detach(updatedAppConfiguration);
        updatedAppConfiguration
            .defaultTimezone(UPDATED_DEFAULT_TIMEZONE)
            .ordersUnderMarketPercent(UPDATED_ORDERS_UNDER_MARKET_PERCENT)
            .ordersMinUsd(UPDATED_ORDERS_MIN_USD)
            .bitcoinSendingFee(UPDATED_BITCOIN_SENDING_FEE)
            .frontendBaseurl(UPDATED_FRONTEND_BASEURL)
            .frontendDirroot(UPDATED_FRONTEND_DIRROOT)
            .fiatWithdrawFee(UPDATED_FIAT_WITHDRAW_FEE)
            .apiDbDebug(UPDATED_API_DB_DEBUG)
            .apiDirroot(UPDATED_API_DIRROOT)
            .supportEmail(UPDATED_SUPPORT_EMAIL)
            .emailSmtpHost(UPDATED_EMAIL_SMTP_HOST)
            .emailSmtpPort(UPDATED_EMAIL_SMTP_PORT)
            .emailSmtpSecurity(UPDATED_EMAIL_SMTP_SECURITY)
            .emailSmtpUsername(UPDATED_EMAIL_SMTP_USERNAME)
            .emailSmtpPassword(UPDATED_EMAIL_SMTP_PASSWORD)
            .emailSmtpSendFrom(UPDATED_EMAIL_SMTP_SEND_FROM)
            .bitcoinUsername(UPDATED_BITCOIN_USERNAME)
            .bitcoinAccountname(UPDATED_BITCOIN_ACCOUNTNAME)
            .bitcoinPassphrase(UPDATED_BITCOIN_PASSPHRASE)
            .bitcoinHost(UPDATED_BITCOIN_HOST)
            .bitcoinPort(UPDATED_BITCOIN_PORT)
            .bitcoinProtocol(UPDATED_BITCOIN_PROTOCOL)
            .authyApiKey(UPDATED_AUTHY_API_KEY)
            .helpdeskKey(UPDATED_HELPDESK_KEY)
            .exchangeName(UPDATED_EXCHANGE_NAME)
            .mcryptKey(UPDATED_MCRYPT_KEY)
            .currencyConversionFee(UPDATED_CURRENCY_CONVERSION_FEE)
            .crossCurrencyTrades(UPDATED_CROSS_CURRENCY_TRADES)
            .btcCurrencyId(UPDATED_BTC_CURRENCY_ID)
            .depositBitcoinDesc(UPDATED_DEPOSIT_BITCOIN_DESC)
            .defaultFeeScheduleId(UPDATED_DEFAULT_FEE_SCHEDULE_ID)
            .historyBuyId(UPDATED_HISTORY_BUY_ID)
            .historyDepositId(UPDATED_HISTORY_DEPOSIT_ID)
            .historyLoginId(UPDATED_HISTORY_LOGIN_ID)
            .historySellId(UPDATED_HISTORY_SELL_ID)
            .historyWithdrawId(UPDATED_HISTORY_WITHDRAW_ID)
            .orderTypeAsk(UPDATED_ORDER_TYPE_ASK)
            .requestAwaitingId(UPDATED_REQUEST_AWAITING_ID)
            .requestCancelledId(UPDATED_REQUEST_CANCELLED_ID)
            .requestCompletedId(UPDATED_REQUEST_COMPLETED_ID)
            .orderTypeBid(UPDATED_ORDER_TYPE_BID)
            .requestDepositId(UPDATED_REQUEST_DEPOSIT_ID)
            .requestPendingId(UPDATED_REQUEST_PENDING_ID)
            .requestWithdrawalId(UPDATED_REQUEST_WITHDRAWAL_ID)
            .transactionsBuyId(UPDATED_TRANSACTIONS_BUY_ID)
            .transactionsSellId(UPDATED_TRANSACTIONS_SELL_ID)
            .withdrawFiatDesc(UPDATED_WITHDRAW_FIAT_DESC)
            .withdrawBtcDesc(UPDATED_WITHDRAW_BTC_DESC)
            .formEmailFrom(UPDATED_FORM_EMAIL_FROM)
            .emailNotifyNewUsers(UPDATED_EMAIL_NOTIFY_NEW_USERS)
            .passRegex(UPDATED_PASS_REGEX)
            .passMinChars(UPDATED_PASS_MIN_CHARS)
            .authDbDebug(UPDATED_AUTH_DB_DEBUG)
            .bitcoinReserveMin(UPDATED_BITCOIN_RESERVE_MIN)
            .bitcoinReserveRatio(UPDATED_BITCOIN_RESERVE_RATIO)
            .bitcoinWarmWalletAddress(UPDATED_BITCOIN_WARM_WALLET_ADDRESS)
            .cronDbDebug(UPDATED_CRON_DB_DEBUG)
            .quandlApiKey(UPDATED_QUANDL_API_KEY)
            .cronDirroot(UPDATED_CRON_DIRROOT)
            .backstageDbDebug(UPDATED_BACKSTAGE_DB_DEBUG)
            .backstageDirroot(UPDATED_BACKSTAGE_DIRROOT)
            .emailNotifyFiatWithdrawals(UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .cloudflareApiKey(UPDATED_CLOUDFLARE_API_KEY)
            .googleRecaptchApiKey(UPDATED_GOOGLE_RECAPTCH_API_KEY)
            .cloudflareBlacklist(UPDATED_CLOUDFLARE_BLACKLIST)
            .cloudflareEmail(UPDATED_CLOUDFLARE_EMAIL)
            .googleRecaptchApiSecret(UPDATED_GOOGLE_RECAPTCH_API_SECRET)
            .cloudflareBlacklistAttempts(UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS)
            .cloudflareBlacklistTimeframe(UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME)
            .cryptoCapitalPk(UPDATED_CRYPTO_CAPITAL_PK)
            .depositFiatDesc(UPDATED_DEPOSIT_FIAT_DESC)
            .emailNotifyFiatFailed(UPDATED_EMAIL_NOTIFY_FIAT_FAILED)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS);
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(updatedAppConfiguration);

        restAppConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getDefaultTimezone()).isEqualTo(UPDATED_DEFAULT_TIMEZONE);
        assertThat(testAppConfiguration.getOrdersUnderMarketPercent()).isEqualTo(UPDATED_ORDERS_UNDER_MARKET_PERCENT);
        assertThat(testAppConfiguration.getOrdersMinUsd()).isEqualTo(UPDATED_ORDERS_MIN_USD);
        assertThat(testAppConfiguration.getBitcoinSendingFee()).isEqualTo(UPDATED_BITCOIN_SENDING_FEE);
        assertThat(testAppConfiguration.getFrontendBaseurl()).isEqualTo(UPDATED_FRONTEND_BASEURL);
        assertThat(testAppConfiguration.getFrontendDirroot()).isEqualTo(UPDATED_FRONTEND_DIRROOT);
        assertThat(testAppConfiguration.getFiatWithdrawFee()).isEqualTo(UPDATED_FIAT_WITHDRAW_FEE);
        assertThat(testAppConfiguration.getApiDbDebug()).isEqualTo(UPDATED_API_DB_DEBUG);
        assertThat(testAppConfiguration.getApiDirroot()).isEqualTo(UPDATED_API_DIRROOT);
        assertThat(testAppConfiguration.getSupportEmail()).isEqualTo(UPDATED_SUPPORT_EMAIL);
        assertThat(testAppConfiguration.getEmailSmtpHost()).isEqualTo(UPDATED_EMAIL_SMTP_HOST);
        assertThat(testAppConfiguration.getEmailSmtpPort()).isEqualTo(UPDATED_EMAIL_SMTP_PORT);
        assertThat(testAppConfiguration.getEmailSmtpSecurity()).isEqualTo(UPDATED_EMAIL_SMTP_SECURITY);
        assertThat(testAppConfiguration.getEmailSmtpUsername()).isEqualTo(UPDATED_EMAIL_SMTP_USERNAME);
        assertThat(testAppConfiguration.getEmailSmtpPassword()).isEqualTo(UPDATED_EMAIL_SMTP_PASSWORD);
        assertThat(testAppConfiguration.getEmailSmtpSendFrom()).isEqualTo(UPDATED_EMAIL_SMTP_SEND_FROM);
        assertThat(testAppConfiguration.getBitcoinUsername()).isEqualTo(UPDATED_BITCOIN_USERNAME);
        assertThat(testAppConfiguration.getBitcoinAccountname()).isEqualTo(UPDATED_BITCOIN_ACCOUNTNAME);
        assertThat(testAppConfiguration.getBitcoinPassphrase()).isEqualTo(UPDATED_BITCOIN_PASSPHRASE);
        assertThat(testAppConfiguration.getBitcoinHost()).isEqualTo(UPDATED_BITCOIN_HOST);
        assertThat(testAppConfiguration.getBitcoinPort()).isEqualTo(UPDATED_BITCOIN_PORT);
        assertThat(testAppConfiguration.getBitcoinProtocol()).isEqualTo(UPDATED_BITCOIN_PROTOCOL);
        assertThat(testAppConfiguration.getAuthyApiKey()).isEqualTo(UPDATED_AUTHY_API_KEY);
        assertThat(testAppConfiguration.getHelpdeskKey()).isEqualTo(UPDATED_HELPDESK_KEY);
        assertThat(testAppConfiguration.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
        assertThat(testAppConfiguration.getMcryptKey()).isEqualTo(UPDATED_MCRYPT_KEY);
        assertThat(testAppConfiguration.getCurrencyConversionFee()).isEqualTo(UPDATED_CURRENCY_CONVERSION_FEE);
        assertThat(testAppConfiguration.getCrossCurrencyTrades()).isEqualTo(UPDATED_CROSS_CURRENCY_TRADES);
        assertThat(testAppConfiguration.getBtcCurrencyId()).isEqualTo(UPDATED_BTC_CURRENCY_ID);
        assertThat(testAppConfiguration.getDepositBitcoinDesc()).isEqualTo(UPDATED_DEPOSIT_BITCOIN_DESC);
        assertThat(testAppConfiguration.getDefaultFeeScheduleId()).isEqualTo(UPDATED_DEFAULT_FEE_SCHEDULE_ID);
        assertThat(testAppConfiguration.getHistoryBuyId()).isEqualTo(UPDATED_HISTORY_BUY_ID);
        assertThat(testAppConfiguration.getHistoryDepositId()).isEqualTo(UPDATED_HISTORY_DEPOSIT_ID);
        assertThat(testAppConfiguration.getHistoryLoginId()).isEqualTo(UPDATED_HISTORY_LOGIN_ID);
        assertThat(testAppConfiguration.getHistorySellId()).isEqualTo(UPDATED_HISTORY_SELL_ID);
        assertThat(testAppConfiguration.getHistoryWithdrawId()).isEqualTo(UPDATED_HISTORY_WITHDRAW_ID);
        assertThat(testAppConfiguration.getOrderTypeAsk()).isEqualTo(UPDATED_ORDER_TYPE_ASK);
        assertThat(testAppConfiguration.getRequestAwaitingId()).isEqualTo(UPDATED_REQUEST_AWAITING_ID);
        assertThat(testAppConfiguration.getRequestCancelledId()).isEqualTo(UPDATED_REQUEST_CANCELLED_ID);
        assertThat(testAppConfiguration.getRequestCompletedId()).isEqualTo(UPDATED_REQUEST_COMPLETED_ID);
        assertThat(testAppConfiguration.getOrderTypeBid()).isEqualTo(UPDATED_ORDER_TYPE_BID);
        assertThat(testAppConfiguration.getRequestDepositId()).isEqualTo(UPDATED_REQUEST_DEPOSIT_ID);
        assertThat(testAppConfiguration.getRequestPendingId()).isEqualTo(UPDATED_REQUEST_PENDING_ID);
        assertThat(testAppConfiguration.getRequestWithdrawalId()).isEqualTo(UPDATED_REQUEST_WITHDRAWAL_ID);
        assertThat(testAppConfiguration.getTransactionsBuyId()).isEqualTo(UPDATED_TRANSACTIONS_BUY_ID);
        assertThat(testAppConfiguration.getTransactionsSellId()).isEqualTo(UPDATED_TRANSACTIONS_SELL_ID);
        assertThat(testAppConfiguration.getWithdrawFiatDesc()).isEqualTo(UPDATED_WITHDRAW_FIAT_DESC);
        assertThat(testAppConfiguration.getWithdrawBtcDesc()).isEqualTo(UPDATED_WITHDRAW_BTC_DESC);
        assertThat(testAppConfiguration.getFormEmailFrom()).isEqualTo(UPDATED_FORM_EMAIL_FROM);
        assertThat(testAppConfiguration.getEmailNotifyNewUsers()).isEqualTo(UPDATED_EMAIL_NOTIFY_NEW_USERS);
        assertThat(testAppConfiguration.getPassRegex()).isEqualTo(UPDATED_PASS_REGEX);
        assertThat(testAppConfiguration.getPassMinChars()).isEqualTo(UPDATED_PASS_MIN_CHARS);
        assertThat(testAppConfiguration.getAuthDbDebug()).isEqualTo(UPDATED_AUTH_DB_DEBUG);
        assertThat(testAppConfiguration.getBitcoinReserveMin()).isEqualTo(UPDATED_BITCOIN_RESERVE_MIN);
        assertThat(testAppConfiguration.getBitcoinReserveRatio()).isEqualTo(UPDATED_BITCOIN_RESERVE_RATIO);
        assertThat(testAppConfiguration.getBitcoinWarmWalletAddress()).isEqualTo(UPDATED_BITCOIN_WARM_WALLET_ADDRESS);
        assertThat(testAppConfiguration.getCronDbDebug()).isEqualTo(UPDATED_CRON_DB_DEBUG);
        assertThat(testAppConfiguration.getQuandlApiKey()).isEqualTo(UPDATED_QUANDL_API_KEY);
        assertThat(testAppConfiguration.getCronDirroot()).isEqualTo(UPDATED_CRON_DIRROOT);
        assertThat(testAppConfiguration.getBackstageDbDebug()).isEqualTo(UPDATED_BACKSTAGE_DB_DEBUG);
        assertThat(testAppConfiguration.getBackstageDirroot()).isEqualTo(UPDATED_BACKSTAGE_DIRROOT);
        assertThat(testAppConfiguration.getEmailNotifyFiatWithdrawals()).isEqualTo(UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS);
        assertThat(testAppConfiguration.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testAppConfiguration.getCloudflareApiKey()).isEqualTo(UPDATED_CLOUDFLARE_API_KEY);
        assertThat(testAppConfiguration.getGoogleRecaptchApiKey()).isEqualTo(UPDATED_GOOGLE_RECAPTCH_API_KEY);
        assertThat(testAppConfiguration.getCloudflareBlacklist()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST);
        assertThat(testAppConfiguration.getCloudflareEmail()).isEqualTo(UPDATED_CLOUDFLARE_EMAIL);
        assertThat(testAppConfiguration.getGoogleRecaptchApiSecret()).isEqualTo(UPDATED_GOOGLE_RECAPTCH_API_SECRET);
        assertThat(testAppConfiguration.getCloudflareBlacklistAttempts()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS);
        assertThat(testAppConfiguration.getCloudflareBlacklistTimeframe()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME);
        assertThat(testAppConfiguration.getCryptoCapitalPk()).isEqualTo(UPDATED_CRYPTO_CAPITAL_PK);
        assertThat(testAppConfiguration.getDepositFiatDesc()).isEqualTo(UPDATED_DEPOSIT_FIAT_DESC);
        assertThat(testAppConfiguration.getEmailNotifyFiatFailed()).isEqualTo(UPDATED_EMAIL_NOTIFY_FIAT_FAILED);
        assertThat(testAppConfiguration.getTradingStatus()).isEqualTo(UPDATED_TRADING_STATUS);
        assertThat(testAppConfiguration.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppConfigurationWithPatch() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();

        // Update the appConfiguration using partial update
        AppConfiguration partialUpdatedAppConfiguration = new AppConfiguration();
        partialUpdatedAppConfiguration.setId(appConfiguration.getId());

        partialUpdatedAppConfiguration
            .defaultTimezone(UPDATED_DEFAULT_TIMEZONE)
            .ordersUnderMarketPercent(UPDATED_ORDERS_UNDER_MARKET_PERCENT)
            .fiatWithdrawFee(UPDATED_FIAT_WITHDRAW_FEE)
            .apiDbDebug(UPDATED_API_DB_DEBUG)
            .emailSmtpHost(UPDATED_EMAIL_SMTP_HOST)
            .emailSmtpSecurity(UPDATED_EMAIL_SMTP_SECURITY)
            .emailSmtpUsername(UPDATED_EMAIL_SMTP_USERNAME)
            .emailSmtpPassword(UPDATED_EMAIL_SMTP_PASSWORD)
            .emailSmtpSendFrom(UPDATED_EMAIL_SMTP_SEND_FROM)
            .bitcoinAccountname(UPDATED_BITCOIN_ACCOUNTNAME)
            .bitcoinPassphrase(UPDATED_BITCOIN_PASSPHRASE)
            .bitcoinPort(UPDATED_BITCOIN_PORT)
            .bitcoinProtocol(UPDATED_BITCOIN_PROTOCOL)
            .helpdeskKey(UPDATED_HELPDESK_KEY)
            .exchangeName(UPDATED_EXCHANGE_NAME)
            .defaultFeeScheduleId(UPDATED_DEFAULT_FEE_SCHEDULE_ID)
            .historyBuyId(UPDATED_HISTORY_BUY_ID)
            .historyDepositId(UPDATED_HISTORY_DEPOSIT_ID)
            .historyWithdrawId(UPDATED_HISTORY_WITHDRAW_ID)
            .requestCancelledId(UPDATED_REQUEST_CANCELLED_ID)
            .transactionsSellId(UPDATED_TRANSACTIONS_SELL_ID)
            .formEmailFrom(UPDATED_FORM_EMAIL_FROM)
            .authDbDebug(UPDATED_AUTH_DB_DEBUG)
            .bitcoinReserveMin(UPDATED_BITCOIN_RESERVE_MIN)
            .bitcoinReserveRatio(UPDATED_BITCOIN_RESERVE_RATIO)
            .bitcoinWarmWalletAddress(UPDATED_BITCOIN_WARM_WALLET_ADDRESS)
            .backstageDbDebug(UPDATED_BACKSTAGE_DB_DEBUG)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .cloudflareApiKey(UPDATED_CLOUDFLARE_API_KEY)
            .googleRecaptchApiSecret(UPDATED_GOOGLE_RECAPTCH_API_SECRET)
            .depositFiatDesc(UPDATED_DEPOSIT_FIAT_DESC)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS);

        restAppConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getDefaultTimezone()).isEqualTo(UPDATED_DEFAULT_TIMEZONE);
        assertThat(testAppConfiguration.getOrdersUnderMarketPercent()).isEqualTo(UPDATED_ORDERS_UNDER_MARKET_PERCENT);
        assertThat(testAppConfiguration.getOrdersMinUsd()).isEqualTo(DEFAULT_ORDERS_MIN_USD);
        assertThat(testAppConfiguration.getBitcoinSendingFee()).isEqualTo(DEFAULT_BITCOIN_SENDING_FEE);
        assertThat(testAppConfiguration.getFrontendBaseurl()).isEqualTo(DEFAULT_FRONTEND_BASEURL);
        assertThat(testAppConfiguration.getFrontendDirroot()).isEqualTo(DEFAULT_FRONTEND_DIRROOT);
        assertThat(testAppConfiguration.getFiatWithdrawFee()).isEqualTo(UPDATED_FIAT_WITHDRAW_FEE);
        assertThat(testAppConfiguration.getApiDbDebug()).isEqualTo(UPDATED_API_DB_DEBUG);
        assertThat(testAppConfiguration.getApiDirroot()).isEqualTo(DEFAULT_API_DIRROOT);
        assertThat(testAppConfiguration.getSupportEmail()).isEqualTo(DEFAULT_SUPPORT_EMAIL);
        assertThat(testAppConfiguration.getEmailSmtpHost()).isEqualTo(UPDATED_EMAIL_SMTP_HOST);
        assertThat(testAppConfiguration.getEmailSmtpPort()).isEqualTo(DEFAULT_EMAIL_SMTP_PORT);
        assertThat(testAppConfiguration.getEmailSmtpSecurity()).isEqualTo(UPDATED_EMAIL_SMTP_SECURITY);
        assertThat(testAppConfiguration.getEmailSmtpUsername()).isEqualTo(UPDATED_EMAIL_SMTP_USERNAME);
        assertThat(testAppConfiguration.getEmailSmtpPassword()).isEqualTo(UPDATED_EMAIL_SMTP_PASSWORD);
        assertThat(testAppConfiguration.getEmailSmtpSendFrom()).isEqualTo(UPDATED_EMAIL_SMTP_SEND_FROM);
        assertThat(testAppConfiguration.getBitcoinUsername()).isEqualTo(DEFAULT_BITCOIN_USERNAME);
        assertThat(testAppConfiguration.getBitcoinAccountname()).isEqualTo(UPDATED_BITCOIN_ACCOUNTNAME);
        assertThat(testAppConfiguration.getBitcoinPassphrase()).isEqualTo(UPDATED_BITCOIN_PASSPHRASE);
        assertThat(testAppConfiguration.getBitcoinHost()).isEqualTo(DEFAULT_BITCOIN_HOST);
        assertThat(testAppConfiguration.getBitcoinPort()).isEqualTo(UPDATED_BITCOIN_PORT);
        assertThat(testAppConfiguration.getBitcoinProtocol()).isEqualTo(UPDATED_BITCOIN_PROTOCOL);
        assertThat(testAppConfiguration.getAuthyApiKey()).isEqualTo(DEFAULT_AUTHY_API_KEY);
        assertThat(testAppConfiguration.getHelpdeskKey()).isEqualTo(UPDATED_HELPDESK_KEY);
        assertThat(testAppConfiguration.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
        assertThat(testAppConfiguration.getMcryptKey()).isEqualTo(DEFAULT_MCRYPT_KEY);
        assertThat(testAppConfiguration.getCurrencyConversionFee()).isEqualTo(DEFAULT_CURRENCY_CONVERSION_FEE);
        assertThat(testAppConfiguration.getCrossCurrencyTrades()).isEqualTo(DEFAULT_CROSS_CURRENCY_TRADES);
        assertThat(testAppConfiguration.getBtcCurrencyId()).isEqualTo(DEFAULT_BTC_CURRENCY_ID);
        assertThat(testAppConfiguration.getDepositBitcoinDesc()).isEqualTo(DEFAULT_DEPOSIT_BITCOIN_DESC);
        assertThat(testAppConfiguration.getDefaultFeeScheduleId()).isEqualTo(UPDATED_DEFAULT_FEE_SCHEDULE_ID);
        assertThat(testAppConfiguration.getHistoryBuyId()).isEqualTo(UPDATED_HISTORY_BUY_ID);
        assertThat(testAppConfiguration.getHistoryDepositId()).isEqualTo(UPDATED_HISTORY_DEPOSIT_ID);
        assertThat(testAppConfiguration.getHistoryLoginId()).isEqualTo(DEFAULT_HISTORY_LOGIN_ID);
        assertThat(testAppConfiguration.getHistorySellId()).isEqualTo(DEFAULT_HISTORY_SELL_ID);
        assertThat(testAppConfiguration.getHistoryWithdrawId()).isEqualTo(UPDATED_HISTORY_WITHDRAW_ID);
        assertThat(testAppConfiguration.getOrderTypeAsk()).isEqualTo(DEFAULT_ORDER_TYPE_ASK);
        assertThat(testAppConfiguration.getRequestAwaitingId()).isEqualTo(DEFAULT_REQUEST_AWAITING_ID);
        assertThat(testAppConfiguration.getRequestCancelledId()).isEqualTo(UPDATED_REQUEST_CANCELLED_ID);
        assertThat(testAppConfiguration.getRequestCompletedId()).isEqualTo(DEFAULT_REQUEST_COMPLETED_ID);
        assertThat(testAppConfiguration.getOrderTypeBid()).isEqualTo(DEFAULT_ORDER_TYPE_BID);
        assertThat(testAppConfiguration.getRequestDepositId()).isEqualTo(DEFAULT_REQUEST_DEPOSIT_ID);
        assertThat(testAppConfiguration.getRequestPendingId()).isEqualTo(DEFAULT_REQUEST_PENDING_ID);
        assertThat(testAppConfiguration.getRequestWithdrawalId()).isEqualTo(DEFAULT_REQUEST_WITHDRAWAL_ID);
        assertThat(testAppConfiguration.getTransactionsBuyId()).isEqualTo(DEFAULT_TRANSACTIONS_BUY_ID);
        assertThat(testAppConfiguration.getTransactionsSellId()).isEqualTo(UPDATED_TRANSACTIONS_SELL_ID);
        assertThat(testAppConfiguration.getWithdrawFiatDesc()).isEqualTo(DEFAULT_WITHDRAW_FIAT_DESC);
        assertThat(testAppConfiguration.getWithdrawBtcDesc()).isEqualTo(DEFAULT_WITHDRAW_BTC_DESC);
        assertThat(testAppConfiguration.getFormEmailFrom()).isEqualTo(UPDATED_FORM_EMAIL_FROM);
        assertThat(testAppConfiguration.getEmailNotifyNewUsers()).isEqualTo(DEFAULT_EMAIL_NOTIFY_NEW_USERS);
        assertThat(testAppConfiguration.getPassRegex()).isEqualTo(DEFAULT_PASS_REGEX);
        assertThat(testAppConfiguration.getPassMinChars()).isEqualTo(DEFAULT_PASS_MIN_CHARS);
        assertThat(testAppConfiguration.getAuthDbDebug()).isEqualTo(UPDATED_AUTH_DB_DEBUG);
        assertThat(testAppConfiguration.getBitcoinReserveMin()).isEqualTo(UPDATED_BITCOIN_RESERVE_MIN);
        assertThat(testAppConfiguration.getBitcoinReserveRatio()).isEqualTo(UPDATED_BITCOIN_RESERVE_RATIO);
        assertThat(testAppConfiguration.getBitcoinWarmWalletAddress()).isEqualTo(UPDATED_BITCOIN_WARM_WALLET_ADDRESS);
        assertThat(testAppConfiguration.getCronDbDebug()).isEqualTo(DEFAULT_CRON_DB_DEBUG);
        assertThat(testAppConfiguration.getQuandlApiKey()).isEqualTo(DEFAULT_QUANDL_API_KEY);
        assertThat(testAppConfiguration.getCronDirroot()).isEqualTo(DEFAULT_CRON_DIRROOT);
        assertThat(testAppConfiguration.getBackstageDbDebug()).isEqualTo(UPDATED_BACKSTAGE_DB_DEBUG);
        assertThat(testAppConfiguration.getBackstageDirroot()).isEqualTo(DEFAULT_BACKSTAGE_DIRROOT);
        assertThat(testAppConfiguration.getEmailNotifyFiatWithdrawals()).isEqualTo(DEFAULT_EMAIL_NOTIFY_FIAT_WITHDRAWALS);
        assertThat(testAppConfiguration.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testAppConfiguration.getCloudflareApiKey()).isEqualTo(UPDATED_CLOUDFLARE_API_KEY);
        assertThat(testAppConfiguration.getGoogleRecaptchApiKey()).isEqualTo(DEFAULT_GOOGLE_RECAPTCH_API_KEY);
        assertThat(testAppConfiguration.getCloudflareBlacklist()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST);
        assertThat(testAppConfiguration.getCloudflareEmail()).isEqualTo(DEFAULT_CLOUDFLARE_EMAIL);
        assertThat(testAppConfiguration.getGoogleRecaptchApiSecret()).isEqualTo(UPDATED_GOOGLE_RECAPTCH_API_SECRET);
        assertThat(testAppConfiguration.getCloudflareBlacklistAttempts()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST_ATTEMPTS);
        assertThat(testAppConfiguration.getCloudflareBlacklistTimeframe()).isEqualTo(DEFAULT_CLOUDFLARE_BLACKLIST_TIMEFRAME);
        assertThat(testAppConfiguration.getCryptoCapitalPk()).isEqualTo(DEFAULT_CRYPTO_CAPITAL_PK);
        assertThat(testAppConfiguration.getDepositFiatDesc()).isEqualTo(UPDATED_DEPOSIT_FIAT_DESC);
        assertThat(testAppConfiguration.getEmailNotifyFiatFailed()).isEqualTo(DEFAULT_EMAIL_NOTIFY_FIAT_FAILED);
        assertThat(testAppConfiguration.getTradingStatus()).isEqualTo(UPDATED_TRADING_STATUS);
        assertThat(testAppConfiguration.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAppConfigurationWithPatch() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();

        // Update the appConfiguration using partial update
        AppConfiguration partialUpdatedAppConfiguration = new AppConfiguration();
        partialUpdatedAppConfiguration.setId(appConfiguration.getId());

        partialUpdatedAppConfiguration
            .defaultTimezone(UPDATED_DEFAULT_TIMEZONE)
            .ordersUnderMarketPercent(UPDATED_ORDERS_UNDER_MARKET_PERCENT)
            .ordersMinUsd(UPDATED_ORDERS_MIN_USD)
            .bitcoinSendingFee(UPDATED_BITCOIN_SENDING_FEE)
            .frontendBaseurl(UPDATED_FRONTEND_BASEURL)
            .frontendDirroot(UPDATED_FRONTEND_DIRROOT)
            .fiatWithdrawFee(UPDATED_FIAT_WITHDRAW_FEE)
            .apiDbDebug(UPDATED_API_DB_DEBUG)
            .apiDirroot(UPDATED_API_DIRROOT)
            .supportEmail(UPDATED_SUPPORT_EMAIL)
            .emailSmtpHost(UPDATED_EMAIL_SMTP_HOST)
            .emailSmtpPort(UPDATED_EMAIL_SMTP_PORT)
            .emailSmtpSecurity(UPDATED_EMAIL_SMTP_SECURITY)
            .emailSmtpUsername(UPDATED_EMAIL_SMTP_USERNAME)
            .emailSmtpPassword(UPDATED_EMAIL_SMTP_PASSWORD)
            .emailSmtpSendFrom(UPDATED_EMAIL_SMTP_SEND_FROM)
            .bitcoinUsername(UPDATED_BITCOIN_USERNAME)
            .bitcoinAccountname(UPDATED_BITCOIN_ACCOUNTNAME)
            .bitcoinPassphrase(UPDATED_BITCOIN_PASSPHRASE)
            .bitcoinHost(UPDATED_BITCOIN_HOST)
            .bitcoinPort(UPDATED_BITCOIN_PORT)
            .bitcoinProtocol(UPDATED_BITCOIN_PROTOCOL)
            .authyApiKey(UPDATED_AUTHY_API_KEY)
            .helpdeskKey(UPDATED_HELPDESK_KEY)
            .exchangeName(UPDATED_EXCHANGE_NAME)
            .mcryptKey(UPDATED_MCRYPT_KEY)
            .currencyConversionFee(UPDATED_CURRENCY_CONVERSION_FEE)
            .crossCurrencyTrades(UPDATED_CROSS_CURRENCY_TRADES)
            .btcCurrencyId(UPDATED_BTC_CURRENCY_ID)
            .depositBitcoinDesc(UPDATED_DEPOSIT_BITCOIN_DESC)
            .defaultFeeScheduleId(UPDATED_DEFAULT_FEE_SCHEDULE_ID)
            .historyBuyId(UPDATED_HISTORY_BUY_ID)
            .historyDepositId(UPDATED_HISTORY_DEPOSIT_ID)
            .historyLoginId(UPDATED_HISTORY_LOGIN_ID)
            .historySellId(UPDATED_HISTORY_SELL_ID)
            .historyWithdrawId(UPDATED_HISTORY_WITHDRAW_ID)
            .orderTypeAsk(UPDATED_ORDER_TYPE_ASK)
            .requestAwaitingId(UPDATED_REQUEST_AWAITING_ID)
            .requestCancelledId(UPDATED_REQUEST_CANCELLED_ID)
            .requestCompletedId(UPDATED_REQUEST_COMPLETED_ID)
            .orderTypeBid(UPDATED_ORDER_TYPE_BID)
            .requestDepositId(UPDATED_REQUEST_DEPOSIT_ID)
            .requestPendingId(UPDATED_REQUEST_PENDING_ID)
            .requestWithdrawalId(UPDATED_REQUEST_WITHDRAWAL_ID)
            .transactionsBuyId(UPDATED_TRANSACTIONS_BUY_ID)
            .transactionsSellId(UPDATED_TRANSACTIONS_SELL_ID)
            .withdrawFiatDesc(UPDATED_WITHDRAW_FIAT_DESC)
            .withdrawBtcDesc(UPDATED_WITHDRAW_BTC_DESC)
            .formEmailFrom(UPDATED_FORM_EMAIL_FROM)
            .emailNotifyNewUsers(UPDATED_EMAIL_NOTIFY_NEW_USERS)
            .passRegex(UPDATED_PASS_REGEX)
            .passMinChars(UPDATED_PASS_MIN_CHARS)
            .authDbDebug(UPDATED_AUTH_DB_DEBUG)
            .bitcoinReserveMin(UPDATED_BITCOIN_RESERVE_MIN)
            .bitcoinReserveRatio(UPDATED_BITCOIN_RESERVE_RATIO)
            .bitcoinWarmWalletAddress(UPDATED_BITCOIN_WARM_WALLET_ADDRESS)
            .cronDbDebug(UPDATED_CRON_DB_DEBUG)
            .quandlApiKey(UPDATED_QUANDL_API_KEY)
            .cronDirroot(UPDATED_CRON_DIRROOT)
            .backstageDbDebug(UPDATED_BACKSTAGE_DB_DEBUG)
            .backstageDirroot(UPDATED_BACKSTAGE_DIRROOT)
            .emailNotifyFiatWithdrawals(UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .cloudflareApiKey(UPDATED_CLOUDFLARE_API_KEY)
            .googleRecaptchApiKey(UPDATED_GOOGLE_RECAPTCH_API_KEY)
            .cloudflareBlacklist(UPDATED_CLOUDFLARE_BLACKLIST)
            .cloudflareEmail(UPDATED_CLOUDFLARE_EMAIL)
            .googleRecaptchApiSecret(UPDATED_GOOGLE_RECAPTCH_API_SECRET)
            .cloudflareBlacklistAttempts(UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS)
            .cloudflareBlacklistTimeframe(UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME)
            .cryptoCapitalPk(UPDATED_CRYPTO_CAPITAL_PK)
            .depositFiatDesc(UPDATED_DEPOSIT_FIAT_DESC)
            .emailNotifyFiatFailed(UPDATED_EMAIL_NOTIFY_FIAT_FAILED)
            .tradingStatus(UPDATED_TRADING_STATUS)
            .withdrawalsStatus(UPDATED_WITHDRAWALS_STATUS);

        restAppConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AppConfiguration testAppConfiguration = appConfigurationList.get(appConfigurationList.size() - 1);
        assertThat(testAppConfiguration.getDefaultTimezone()).isEqualTo(UPDATED_DEFAULT_TIMEZONE);
        assertThat(testAppConfiguration.getOrdersUnderMarketPercent()).isEqualTo(UPDATED_ORDERS_UNDER_MARKET_PERCENT);
        assertThat(testAppConfiguration.getOrdersMinUsd()).isEqualTo(UPDATED_ORDERS_MIN_USD);
        assertThat(testAppConfiguration.getBitcoinSendingFee()).isEqualTo(UPDATED_BITCOIN_SENDING_FEE);
        assertThat(testAppConfiguration.getFrontendBaseurl()).isEqualTo(UPDATED_FRONTEND_BASEURL);
        assertThat(testAppConfiguration.getFrontendDirroot()).isEqualTo(UPDATED_FRONTEND_DIRROOT);
        assertThat(testAppConfiguration.getFiatWithdrawFee()).isEqualTo(UPDATED_FIAT_WITHDRAW_FEE);
        assertThat(testAppConfiguration.getApiDbDebug()).isEqualTo(UPDATED_API_DB_DEBUG);
        assertThat(testAppConfiguration.getApiDirroot()).isEqualTo(UPDATED_API_DIRROOT);
        assertThat(testAppConfiguration.getSupportEmail()).isEqualTo(UPDATED_SUPPORT_EMAIL);
        assertThat(testAppConfiguration.getEmailSmtpHost()).isEqualTo(UPDATED_EMAIL_SMTP_HOST);
        assertThat(testAppConfiguration.getEmailSmtpPort()).isEqualTo(UPDATED_EMAIL_SMTP_PORT);
        assertThat(testAppConfiguration.getEmailSmtpSecurity()).isEqualTo(UPDATED_EMAIL_SMTP_SECURITY);
        assertThat(testAppConfiguration.getEmailSmtpUsername()).isEqualTo(UPDATED_EMAIL_SMTP_USERNAME);
        assertThat(testAppConfiguration.getEmailSmtpPassword()).isEqualTo(UPDATED_EMAIL_SMTP_PASSWORD);
        assertThat(testAppConfiguration.getEmailSmtpSendFrom()).isEqualTo(UPDATED_EMAIL_SMTP_SEND_FROM);
        assertThat(testAppConfiguration.getBitcoinUsername()).isEqualTo(UPDATED_BITCOIN_USERNAME);
        assertThat(testAppConfiguration.getBitcoinAccountname()).isEqualTo(UPDATED_BITCOIN_ACCOUNTNAME);
        assertThat(testAppConfiguration.getBitcoinPassphrase()).isEqualTo(UPDATED_BITCOIN_PASSPHRASE);
        assertThat(testAppConfiguration.getBitcoinHost()).isEqualTo(UPDATED_BITCOIN_HOST);
        assertThat(testAppConfiguration.getBitcoinPort()).isEqualTo(UPDATED_BITCOIN_PORT);
        assertThat(testAppConfiguration.getBitcoinProtocol()).isEqualTo(UPDATED_BITCOIN_PROTOCOL);
        assertThat(testAppConfiguration.getAuthyApiKey()).isEqualTo(UPDATED_AUTHY_API_KEY);
        assertThat(testAppConfiguration.getHelpdeskKey()).isEqualTo(UPDATED_HELPDESK_KEY);
        assertThat(testAppConfiguration.getExchangeName()).isEqualTo(UPDATED_EXCHANGE_NAME);
        assertThat(testAppConfiguration.getMcryptKey()).isEqualTo(UPDATED_MCRYPT_KEY);
        assertThat(testAppConfiguration.getCurrencyConversionFee()).isEqualTo(UPDATED_CURRENCY_CONVERSION_FEE);
        assertThat(testAppConfiguration.getCrossCurrencyTrades()).isEqualTo(UPDATED_CROSS_CURRENCY_TRADES);
        assertThat(testAppConfiguration.getBtcCurrencyId()).isEqualTo(UPDATED_BTC_CURRENCY_ID);
        assertThat(testAppConfiguration.getDepositBitcoinDesc()).isEqualTo(UPDATED_DEPOSIT_BITCOIN_DESC);
        assertThat(testAppConfiguration.getDefaultFeeScheduleId()).isEqualTo(UPDATED_DEFAULT_FEE_SCHEDULE_ID);
        assertThat(testAppConfiguration.getHistoryBuyId()).isEqualTo(UPDATED_HISTORY_BUY_ID);
        assertThat(testAppConfiguration.getHistoryDepositId()).isEqualTo(UPDATED_HISTORY_DEPOSIT_ID);
        assertThat(testAppConfiguration.getHistoryLoginId()).isEqualTo(UPDATED_HISTORY_LOGIN_ID);
        assertThat(testAppConfiguration.getHistorySellId()).isEqualTo(UPDATED_HISTORY_SELL_ID);
        assertThat(testAppConfiguration.getHistoryWithdrawId()).isEqualTo(UPDATED_HISTORY_WITHDRAW_ID);
        assertThat(testAppConfiguration.getOrderTypeAsk()).isEqualTo(UPDATED_ORDER_TYPE_ASK);
        assertThat(testAppConfiguration.getRequestAwaitingId()).isEqualTo(UPDATED_REQUEST_AWAITING_ID);
        assertThat(testAppConfiguration.getRequestCancelledId()).isEqualTo(UPDATED_REQUEST_CANCELLED_ID);
        assertThat(testAppConfiguration.getRequestCompletedId()).isEqualTo(UPDATED_REQUEST_COMPLETED_ID);
        assertThat(testAppConfiguration.getOrderTypeBid()).isEqualTo(UPDATED_ORDER_TYPE_BID);
        assertThat(testAppConfiguration.getRequestDepositId()).isEqualTo(UPDATED_REQUEST_DEPOSIT_ID);
        assertThat(testAppConfiguration.getRequestPendingId()).isEqualTo(UPDATED_REQUEST_PENDING_ID);
        assertThat(testAppConfiguration.getRequestWithdrawalId()).isEqualTo(UPDATED_REQUEST_WITHDRAWAL_ID);
        assertThat(testAppConfiguration.getTransactionsBuyId()).isEqualTo(UPDATED_TRANSACTIONS_BUY_ID);
        assertThat(testAppConfiguration.getTransactionsSellId()).isEqualTo(UPDATED_TRANSACTIONS_SELL_ID);
        assertThat(testAppConfiguration.getWithdrawFiatDesc()).isEqualTo(UPDATED_WITHDRAW_FIAT_DESC);
        assertThat(testAppConfiguration.getWithdrawBtcDesc()).isEqualTo(UPDATED_WITHDRAW_BTC_DESC);
        assertThat(testAppConfiguration.getFormEmailFrom()).isEqualTo(UPDATED_FORM_EMAIL_FROM);
        assertThat(testAppConfiguration.getEmailNotifyNewUsers()).isEqualTo(UPDATED_EMAIL_NOTIFY_NEW_USERS);
        assertThat(testAppConfiguration.getPassRegex()).isEqualTo(UPDATED_PASS_REGEX);
        assertThat(testAppConfiguration.getPassMinChars()).isEqualTo(UPDATED_PASS_MIN_CHARS);
        assertThat(testAppConfiguration.getAuthDbDebug()).isEqualTo(UPDATED_AUTH_DB_DEBUG);
        assertThat(testAppConfiguration.getBitcoinReserveMin()).isEqualTo(UPDATED_BITCOIN_RESERVE_MIN);
        assertThat(testAppConfiguration.getBitcoinReserveRatio()).isEqualTo(UPDATED_BITCOIN_RESERVE_RATIO);
        assertThat(testAppConfiguration.getBitcoinWarmWalletAddress()).isEqualTo(UPDATED_BITCOIN_WARM_WALLET_ADDRESS);
        assertThat(testAppConfiguration.getCronDbDebug()).isEqualTo(UPDATED_CRON_DB_DEBUG);
        assertThat(testAppConfiguration.getQuandlApiKey()).isEqualTo(UPDATED_QUANDL_API_KEY);
        assertThat(testAppConfiguration.getCronDirroot()).isEqualTo(UPDATED_CRON_DIRROOT);
        assertThat(testAppConfiguration.getBackstageDbDebug()).isEqualTo(UPDATED_BACKSTAGE_DB_DEBUG);
        assertThat(testAppConfiguration.getBackstageDirroot()).isEqualTo(UPDATED_BACKSTAGE_DIRROOT);
        assertThat(testAppConfiguration.getEmailNotifyFiatWithdrawals()).isEqualTo(UPDATED_EMAIL_NOTIFY_FIAT_WITHDRAWALS);
        assertThat(testAppConfiguration.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testAppConfiguration.getCloudflareApiKey()).isEqualTo(UPDATED_CLOUDFLARE_API_KEY);
        assertThat(testAppConfiguration.getGoogleRecaptchApiKey()).isEqualTo(UPDATED_GOOGLE_RECAPTCH_API_KEY);
        assertThat(testAppConfiguration.getCloudflareBlacklist()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST);
        assertThat(testAppConfiguration.getCloudflareEmail()).isEqualTo(UPDATED_CLOUDFLARE_EMAIL);
        assertThat(testAppConfiguration.getGoogleRecaptchApiSecret()).isEqualTo(UPDATED_GOOGLE_RECAPTCH_API_SECRET);
        assertThat(testAppConfiguration.getCloudflareBlacklistAttempts()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST_ATTEMPTS);
        assertThat(testAppConfiguration.getCloudflareBlacklistTimeframe()).isEqualTo(UPDATED_CLOUDFLARE_BLACKLIST_TIMEFRAME);
        assertThat(testAppConfiguration.getCryptoCapitalPk()).isEqualTo(UPDATED_CRYPTO_CAPITAL_PK);
        assertThat(testAppConfiguration.getDepositFiatDesc()).isEqualTo(UPDATED_DEPOSIT_FIAT_DESC);
        assertThat(testAppConfiguration.getEmailNotifyFiatFailed()).isEqualTo(UPDATED_EMAIL_NOTIFY_FIAT_FAILED);
        assertThat(testAppConfiguration.getTradingStatus()).isEqualTo(UPDATED_TRADING_STATUS);
        assertThat(testAppConfiguration.getWithdrawalsStatus()).isEqualTo(UPDATED_WITHDRAWALS_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = appConfigurationRepository.findAll().size();
        appConfiguration.setId(count.incrementAndGet());

        // Create the AppConfiguration
        AppConfigurationDTO appConfigurationDTO = appConfigurationMapper.toDto(appConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppConfiguration in the database
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppConfiguration() throws Exception {
        // Initialize the database
        appConfigurationRepository.saveAndFlush(appConfiguration);

        int databaseSizeBeforeDelete = appConfigurationRepository.findAll().size();

        // Delete the appConfiguration
        restAppConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, appConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppConfiguration> appConfigurationList = appConfigurationRepository.findAll();
        assertThat(appConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
