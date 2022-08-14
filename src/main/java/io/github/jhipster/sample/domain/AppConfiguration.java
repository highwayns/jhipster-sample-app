package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AppConfiguration.
 */
@Entity
@Table(name = "app_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AppConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "default_timezone", length = 255, nullable = false)
    private String defaultTimezone;

    @NotNull
    @Size(max = 255)
    @Column(name = "orders_under_market_percent", length = 255, nullable = false)
    private String ordersUnderMarketPercent;

    @NotNull
    @Size(max = 255)
    @Column(name = "orders_min_usd", length = 255, nullable = false)
    private String ordersMinUsd;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_sending_fee", length = 255, nullable = false)
    private String bitcoinSendingFee;

    @NotNull
    @Size(max = 255)
    @Column(name = "frontend_baseurl", length = 255, nullable = false)
    private String frontendBaseurl;

    @NotNull
    @Size(max = 255)
    @Column(name = "frontend_dirroot", length = 255, nullable = false)
    private String frontendDirroot;

    @NotNull
    @Size(max = 255)
    @Column(name = "fiat_withdraw_fee", length = 255, nullable = false)
    private String fiatWithdrawFee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "api_db_debug", nullable = false)
    private YesNo apiDbDebug;

    @NotNull
    @Size(max = 255)
    @Column(name = "api_dirroot", length = 255, nullable = false)
    private String apiDirroot;

    @NotNull
    @Size(max = 255)
    @Column(name = "support_email", length = 255, nullable = false)
    private String supportEmail;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_host", length = 255, nullable = false)
    private String emailSmtpHost;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_port", length = 255, nullable = false)
    private String emailSmtpPort;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_security", length = 255, nullable = false)
    private String emailSmtpSecurity;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_username", length = 255, nullable = false)
    private String emailSmtpUsername;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_password", length = 255, nullable = false)
    private String emailSmtpPassword;

    @NotNull
    @Size(max = 255)
    @Column(name = "email_smtp_send_from", length = 255, nullable = false)
    private String emailSmtpSendFrom;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_username", length = 255, nullable = false)
    private String bitcoinUsername;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_accountname", length = 255, nullable = false)
    private String bitcoinAccountname;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_passphrase", length = 255, nullable = false)
    private String bitcoinPassphrase;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_host", length = 255, nullable = false)
    private String bitcoinHost;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_port", length = 255, nullable = false)
    private String bitcoinPort;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_protocol", length = 255, nullable = false)
    private String bitcoinProtocol;

    @NotNull
    @Size(max = 255)
    @Column(name = "authy_api_key", length = 255, nullable = false)
    private String authyApiKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "helpdesk_key", length = 255, nullable = false)
    private String helpdeskKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "exchange_name", length = 255, nullable = false)
    private String exchangeName;

    @NotNull
    @Size(max = 255)
    @Column(name = "mcrypt_key", length = 255, nullable = false)
    private String mcryptKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "currency_conversion_fee", length = 255, nullable = false)
    private String currencyConversionFee;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cross_currency_trades", nullable = false)
    private YesNo crossCurrencyTrades;

    @NotNull
    @Size(max = 255)
    @Column(name = "btc_currency_id", length = 255, nullable = false)
    private String btcCurrencyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "deposit_bitcoin_desc", length = 255, nullable = false)
    private String depositBitcoinDesc;

    @NotNull
    @Size(max = 255)
    @Column(name = "default_fee_schedule_id", length = 255, nullable = false)
    private String defaultFeeScheduleId;

    @NotNull
    @Size(max = 255)
    @Column(name = "history_buy_id", length = 255, nullable = false)
    private String historyBuyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "history_deposit_id", length = 255, nullable = false)
    private String historyDepositId;

    @NotNull
    @Size(max = 255)
    @Column(name = "history_login_id", length = 255, nullable = false)
    private String historyLoginId;

    @NotNull
    @Size(max = 255)
    @Column(name = "history_sell_id", length = 255, nullable = false)
    private String historySellId;

    @NotNull
    @Size(max = 255)
    @Column(name = "history_withdraw_id", length = 255, nullable = false)
    private String historyWithdrawId;

    @NotNull
    @Size(max = 255)
    @Column(name = "order_type_ask", length = 255, nullable = false)
    private String orderTypeAsk;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_awaiting_id", length = 255, nullable = false)
    private String requestAwaitingId;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_cancelled_id", length = 255, nullable = false)
    private String requestCancelledId;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_completed_id", length = 255, nullable = false)
    private String requestCompletedId;

    @NotNull
    @Size(max = 255)
    @Column(name = "order_type_bid", length = 255, nullable = false)
    private String orderTypeBid;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_deposit_id", length = 255, nullable = false)
    private String requestDepositId;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_pending_id", length = 255, nullable = false)
    private String requestPendingId;

    @NotNull
    @Size(max = 255)
    @Column(name = "request_withdrawal_id", length = 255, nullable = false)
    private String requestWithdrawalId;

    @NotNull
    @Size(max = 255)
    @Column(name = "transactions_buy_id", length = 255, nullable = false)
    private String transactionsBuyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "transactions_sell_id", length = 255, nullable = false)
    private String transactionsSellId;

    @NotNull
    @Size(max = 255)
    @Column(name = "withdraw_fiat_desc", length = 255, nullable = false)
    private String withdrawFiatDesc;

    @NotNull
    @Size(max = 255)
    @Column(name = "withdraw_btc_desc", length = 255, nullable = false)
    private String withdrawBtcDesc;

    @NotNull
    @Size(max = 255)
    @Column(name = "form_email_from", length = 255, nullable = false)
    private String formEmailFrom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_notify_new_users", nullable = false)
    private YesNo emailNotifyNewUsers;

    @NotNull
    @Size(max = 255)
    @Column(name = "pass_regex", length = 255, nullable = false)
    private String passRegex;

    @NotNull
    @Size(max = 255)
    @Column(name = "pass_min_chars", length = 255, nullable = false)
    private String passMinChars;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_db_debug", nullable = false)
    private YesNo authDbDebug;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_reserve_min", length = 255, nullable = false)
    private String bitcoinReserveMin;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_reserve_ratio", length = 255, nullable = false)
    private String bitcoinReserveRatio;

    @NotNull
    @Size(max = 255)
    @Column(name = "bitcoin_warm_wallet_address", length = 255, nullable = false)
    private String bitcoinWarmWalletAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cron_db_debug", nullable = false)
    private YesNo cronDbDebug;

    @NotNull
    @Size(max = 255)
    @Column(name = "quandl_api_key", length = 255, nullable = false)
    private String quandlApiKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "cron_dirroot", length = 255, nullable = false)
    private String cronDirroot;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "backstage_db_debug", nullable = false)
    private YesNo backstageDbDebug;

    @NotNull
    @Size(max = 255)
    @Column(name = "backstage_dirroot", length = 255, nullable = false)
    private String backstageDirroot;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_notify_fiat_withdrawals", nullable = false)
    private YesNo emailNotifyFiatWithdrawals;

    @NotNull
    @Size(max = 255)
    @Column(name = "contact_email", length = 255, nullable = false)
    private String contactEmail;

    @NotNull
    @Size(max = 255)
    @Column(name = "cloudflare_api_key", length = 255, nullable = false)
    private String cloudflareApiKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "google_recaptch_api_key", length = 255, nullable = false)
    private String googleRecaptchApiKey;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cloudflare_blacklist", nullable = false)
    private YesNo cloudflareBlacklist;

    @NotNull
    @Size(max = 255)
    @Column(name = "cloudflare_email", length = 255, nullable = false)
    private String cloudflareEmail;

    @NotNull
    @Size(max = 255)
    @Column(name = "google_recaptch_api_secret", length = 255, nullable = false)
    private String googleRecaptchApiSecret;

    @NotNull
    @Column(name = "cloudflare_blacklist_attempts", nullable = false)
    private Integer cloudflareBlacklistAttempts;

    @NotNull
    @Column(name = "cloudflare_blacklist_timeframe", nullable = false)
    private Double cloudflareBlacklistTimeframe;

    @NotNull
    @Size(max = 255)
    @Column(name = "crypto_capital_pk", length = 255, nullable = false)
    private String cryptoCapitalPk;

    @NotNull
    @Size(max = 255)
    @Column(name = "deposit_fiat_desc", length = 255, nullable = false)
    private String depositFiatDesc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_notify_fiat_failed", nullable = false)
    private YesNo emailNotifyFiatFailed;

    @NotNull
    @Size(max = 255)
    @Column(name = "trading_status", length = 255, nullable = false)
    private String tradingStatus;

    @NotNull
    @Size(max = 255)
    @Column(name = "withdrawals_status", length = 255, nullable = false)
    private String withdrawalsStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultTimezone() {
        return this.defaultTimezone;
    }

    public AppConfiguration defaultTimezone(String defaultTimezone) {
        this.setDefaultTimezone(defaultTimezone);
        return this;
    }

    public void setDefaultTimezone(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    public String getOrdersUnderMarketPercent() {
        return this.ordersUnderMarketPercent;
    }

    public AppConfiguration ordersUnderMarketPercent(String ordersUnderMarketPercent) {
        this.setOrdersUnderMarketPercent(ordersUnderMarketPercent);
        return this;
    }

    public void setOrdersUnderMarketPercent(String ordersUnderMarketPercent) {
        this.ordersUnderMarketPercent = ordersUnderMarketPercent;
    }

    public String getOrdersMinUsd() {
        return this.ordersMinUsd;
    }

    public AppConfiguration ordersMinUsd(String ordersMinUsd) {
        this.setOrdersMinUsd(ordersMinUsd);
        return this;
    }

    public void setOrdersMinUsd(String ordersMinUsd) {
        this.ordersMinUsd = ordersMinUsd;
    }

    public String getBitcoinSendingFee() {
        return this.bitcoinSendingFee;
    }

    public AppConfiguration bitcoinSendingFee(String bitcoinSendingFee) {
        this.setBitcoinSendingFee(bitcoinSendingFee);
        return this;
    }

    public void setBitcoinSendingFee(String bitcoinSendingFee) {
        this.bitcoinSendingFee = bitcoinSendingFee;
    }

    public String getFrontendBaseurl() {
        return this.frontendBaseurl;
    }

    public AppConfiguration frontendBaseurl(String frontendBaseurl) {
        this.setFrontendBaseurl(frontendBaseurl);
        return this;
    }

    public void setFrontendBaseurl(String frontendBaseurl) {
        this.frontendBaseurl = frontendBaseurl;
    }

    public String getFrontendDirroot() {
        return this.frontendDirroot;
    }

    public AppConfiguration frontendDirroot(String frontendDirroot) {
        this.setFrontendDirroot(frontendDirroot);
        return this;
    }

    public void setFrontendDirroot(String frontendDirroot) {
        this.frontendDirroot = frontendDirroot;
    }

    public String getFiatWithdrawFee() {
        return this.fiatWithdrawFee;
    }

    public AppConfiguration fiatWithdrawFee(String fiatWithdrawFee) {
        this.setFiatWithdrawFee(fiatWithdrawFee);
        return this;
    }

    public void setFiatWithdrawFee(String fiatWithdrawFee) {
        this.fiatWithdrawFee = fiatWithdrawFee;
    }

    public YesNo getApiDbDebug() {
        return this.apiDbDebug;
    }

    public AppConfiguration apiDbDebug(YesNo apiDbDebug) {
        this.setApiDbDebug(apiDbDebug);
        return this;
    }

    public void setApiDbDebug(YesNo apiDbDebug) {
        this.apiDbDebug = apiDbDebug;
    }

    public String getApiDirroot() {
        return this.apiDirroot;
    }

    public AppConfiguration apiDirroot(String apiDirroot) {
        this.setApiDirroot(apiDirroot);
        return this;
    }

    public void setApiDirroot(String apiDirroot) {
        this.apiDirroot = apiDirroot;
    }

    public String getSupportEmail() {
        return this.supportEmail;
    }

    public AppConfiguration supportEmail(String supportEmail) {
        this.setSupportEmail(supportEmail);
        return this;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getEmailSmtpHost() {
        return this.emailSmtpHost;
    }

    public AppConfiguration emailSmtpHost(String emailSmtpHost) {
        this.setEmailSmtpHost(emailSmtpHost);
        return this;
    }

    public void setEmailSmtpHost(String emailSmtpHost) {
        this.emailSmtpHost = emailSmtpHost;
    }

    public String getEmailSmtpPort() {
        return this.emailSmtpPort;
    }

    public AppConfiguration emailSmtpPort(String emailSmtpPort) {
        this.setEmailSmtpPort(emailSmtpPort);
        return this;
    }

    public void setEmailSmtpPort(String emailSmtpPort) {
        this.emailSmtpPort = emailSmtpPort;
    }

    public String getEmailSmtpSecurity() {
        return this.emailSmtpSecurity;
    }

    public AppConfiguration emailSmtpSecurity(String emailSmtpSecurity) {
        this.setEmailSmtpSecurity(emailSmtpSecurity);
        return this;
    }

    public void setEmailSmtpSecurity(String emailSmtpSecurity) {
        this.emailSmtpSecurity = emailSmtpSecurity;
    }

    public String getEmailSmtpUsername() {
        return this.emailSmtpUsername;
    }

    public AppConfiguration emailSmtpUsername(String emailSmtpUsername) {
        this.setEmailSmtpUsername(emailSmtpUsername);
        return this;
    }

    public void setEmailSmtpUsername(String emailSmtpUsername) {
        this.emailSmtpUsername = emailSmtpUsername;
    }

    public String getEmailSmtpPassword() {
        return this.emailSmtpPassword;
    }

    public AppConfiguration emailSmtpPassword(String emailSmtpPassword) {
        this.setEmailSmtpPassword(emailSmtpPassword);
        return this;
    }

    public void setEmailSmtpPassword(String emailSmtpPassword) {
        this.emailSmtpPassword = emailSmtpPassword;
    }

    public String getEmailSmtpSendFrom() {
        return this.emailSmtpSendFrom;
    }

    public AppConfiguration emailSmtpSendFrom(String emailSmtpSendFrom) {
        this.setEmailSmtpSendFrom(emailSmtpSendFrom);
        return this;
    }

    public void setEmailSmtpSendFrom(String emailSmtpSendFrom) {
        this.emailSmtpSendFrom = emailSmtpSendFrom;
    }

    public String getBitcoinUsername() {
        return this.bitcoinUsername;
    }

    public AppConfiguration bitcoinUsername(String bitcoinUsername) {
        this.setBitcoinUsername(bitcoinUsername);
        return this;
    }

    public void setBitcoinUsername(String bitcoinUsername) {
        this.bitcoinUsername = bitcoinUsername;
    }

    public String getBitcoinAccountname() {
        return this.bitcoinAccountname;
    }

    public AppConfiguration bitcoinAccountname(String bitcoinAccountname) {
        this.setBitcoinAccountname(bitcoinAccountname);
        return this;
    }

    public void setBitcoinAccountname(String bitcoinAccountname) {
        this.bitcoinAccountname = bitcoinAccountname;
    }

    public String getBitcoinPassphrase() {
        return this.bitcoinPassphrase;
    }

    public AppConfiguration bitcoinPassphrase(String bitcoinPassphrase) {
        this.setBitcoinPassphrase(bitcoinPassphrase);
        return this;
    }

    public void setBitcoinPassphrase(String bitcoinPassphrase) {
        this.bitcoinPassphrase = bitcoinPassphrase;
    }

    public String getBitcoinHost() {
        return this.bitcoinHost;
    }

    public AppConfiguration bitcoinHost(String bitcoinHost) {
        this.setBitcoinHost(bitcoinHost);
        return this;
    }

    public void setBitcoinHost(String bitcoinHost) {
        this.bitcoinHost = bitcoinHost;
    }

    public String getBitcoinPort() {
        return this.bitcoinPort;
    }

    public AppConfiguration bitcoinPort(String bitcoinPort) {
        this.setBitcoinPort(bitcoinPort);
        return this;
    }

    public void setBitcoinPort(String bitcoinPort) {
        this.bitcoinPort = bitcoinPort;
    }

    public String getBitcoinProtocol() {
        return this.bitcoinProtocol;
    }

    public AppConfiguration bitcoinProtocol(String bitcoinProtocol) {
        this.setBitcoinProtocol(bitcoinProtocol);
        return this;
    }

    public void setBitcoinProtocol(String bitcoinProtocol) {
        this.bitcoinProtocol = bitcoinProtocol;
    }

    public String getAuthyApiKey() {
        return this.authyApiKey;
    }

    public AppConfiguration authyApiKey(String authyApiKey) {
        this.setAuthyApiKey(authyApiKey);
        return this;
    }

    public void setAuthyApiKey(String authyApiKey) {
        this.authyApiKey = authyApiKey;
    }

    public String getHelpdeskKey() {
        return this.helpdeskKey;
    }

    public AppConfiguration helpdeskKey(String helpdeskKey) {
        this.setHelpdeskKey(helpdeskKey);
        return this;
    }

    public void setHelpdeskKey(String helpdeskKey) {
        this.helpdeskKey = helpdeskKey;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public AppConfiguration exchangeName(String exchangeName) {
        this.setExchangeName(exchangeName);
        return this;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getMcryptKey() {
        return this.mcryptKey;
    }

    public AppConfiguration mcryptKey(String mcryptKey) {
        this.setMcryptKey(mcryptKey);
        return this;
    }

    public void setMcryptKey(String mcryptKey) {
        this.mcryptKey = mcryptKey;
    }

    public String getCurrencyConversionFee() {
        return this.currencyConversionFee;
    }

    public AppConfiguration currencyConversionFee(String currencyConversionFee) {
        this.setCurrencyConversionFee(currencyConversionFee);
        return this;
    }

    public void setCurrencyConversionFee(String currencyConversionFee) {
        this.currencyConversionFee = currencyConversionFee;
    }

    public YesNo getCrossCurrencyTrades() {
        return this.crossCurrencyTrades;
    }

    public AppConfiguration crossCurrencyTrades(YesNo crossCurrencyTrades) {
        this.setCrossCurrencyTrades(crossCurrencyTrades);
        return this;
    }

    public void setCrossCurrencyTrades(YesNo crossCurrencyTrades) {
        this.crossCurrencyTrades = crossCurrencyTrades;
    }

    public String getBtcCurrencyId() {
        return this.btcCurrencyId;
    }

    public AppConfiguration btcCurrencyId(String btcCurrencyId) {
        this.setBtcCurrencyId(btcCurrencyId);
        return this;
    }

    public void setBtcCurrencyId(String btcCurrencyId) {
        this.btcCurrencyId = btcCurrencyId;
    }

    public String getDepositBitcoinDesc() {
        return this.depositBitcoinDesc;
    }

    public AppConfiguration depositBitcoinDesc(String depositBitcoinDesc) {
        this.setDepositBitcoinDesc(depositBitcoinDesc);
        return this;
    }

    public void setDepositBitcoinDesc(String depositBitcoinDesc) {
        this.depositBitcoinDesc = depositBitcoinDesc;
    }

    public String getDefaultFeeScheduleId() {
        return this.defaultFeeScheduleId;
    }

    public AppConfiguration defaultFeeScheduleId(String defaultFeeScheduleId) {
        this.setDefaultFeeScheduleId(defaultFeeScheduleId);
        return this;
    }

    public void setDefaultFeeScheduleId(String defaultFeeScheduleId) {
        this.defaultFeeScheduleId = defaultFeeScheduleId;
    }

    public String getHistoryBuyId() {
        return this.historyBuyId;
    }

    public AppConfiguration historyBuyId(String historyBuyId) {
        this.setHistoryBuyId(historyBuyId);
        return this;
    }

    public void setHistoryBuyId(String historyBuyId) {
        this.historyBuyId = historyBuyId;
    }

    public String getHistoryDepositId() {
        return this.historyDepositId;
    }

    public AppConfiguration historyDepositId(String historyDepositId) {
        this.setHistoryDepositId(historyDepositId);
        return this;
    }

    public void setHistoryDepositId(String historyDepositId) {
        this.historyDepositId = historyDepositId;
    }

    public String getHistoryLoginId() {
        return this.historyLoginId;
    }

    public AppConfiguration historyLoginId(String historyLoginId) {
        this.setHistoryLoginId(historyLoginId);
        return this;
    }

    public void setHistoryLoginId(String historyLoginId) {
        this.historyLoginId = historyLoginId;
    }

    public String getHistorySellId() {
        return this.historySellId;
    }

    public AppConfiguration historySellId(String historySellId) {
        this.setHistorySellId(historySellId);
        return this;
    }

    public void setHistorySellId(String historySellId) {
        this.historySellId = historySellId;
    }

    public String getHistoryWithdrawId() {
        return this.historyWithdrawId;
    }

    public AppConfiguration historyWithdrawId(String historyWithdrawId) {
        this.setHistoryWithdrawId(historyWithdrawId);
        return this;
    }

    public void setHistoryWithdrawId(String historyWithdrawId) {
        this.historyWithdrawId = historyWithdrawId;
    }

    public String getOrderTypeAsk() {
        return this.orderTypeAsk;
    }

    public AppConfiguration orderTypeAsk(String orderTypeAsk) {
        this.setOrderTypeAsk(orderTypeAsk);
        return this;
    }

    public void setOrderTypeAsk(String orderTypeAsk) {
        this.orderTypeAsk = orderTypeAsk;
    }

    public String getRequestAwaitingId() {
        return this.requestAwaitingId;
    }

    public AppConfiguration requestAwaitingId(String requestAwaitingId) {
        this.setRequestAwaitingId(requestAwaitingId);
        return this;
    }

    public void setRequestAwaitingId(String requestAwaitingId) {
        this.requestAwaitingId = requestAwaitingId;
    }

    public String getRequestCancelledId() {
        return this.requestCancelledId;
    }

    public AppConfiguration requestCancelledId(String requestCancelledId) {
        this.setRequestCancelledId(requestCancelledId);
        return this;
    }

    public void setRequestCancelledId(String requestCancelledId) {
        this.requestCancelledId = requestCancelledId;
    }

    public String getRequestCompletedId() {
        return this.requestCompletedId;
    }

    public AppConfiguration requestCompletedId(String requestCompletedId) {
        this.setRequestCompletedId(requestCompletedId);
        return this;
    }

    public void setRequestCompletedId(String requestCompletedId) {
        this.requestCompletedId = requestCompletedId;
    }

    public String getOrderTypeBid() {
        return this.orderTypeBid;
    }

    public AppConfiguration orderTypeBid(String orderTypeBid) {
        this.setOrderTypeBid(orderTypeBid);
        return this;
    }

    public void setOrderTypeBid(String orderTypeBid) {
        this.orderTypeBid = orderTypeBid;
    }

    public String getRequestDepositId() {
        return this.requestDepositId;
    }

    public AppConfiguration requestDepositId(String requestDepositId) {
        this.setRequestDepositId(requestDepositId);
        return this;
    }

    public void setRequestDepositId(String requestDepositId) {
        this.requestDepositId = requestDepositId;
    }

    public String getRequestPendingId() {
        return this.requestPendingId;
    }

    public AppConfiguration requestPendingId(String requestPendingId) {
        this.setRequestPendingId(requestPendingId);
        return this;
    }

    public void setRequestPendingId(String requestPendingId) {
        this.requestPendingId = requestPendingId;
    }

    public String getRequestWithdrawalId() {
        return this.requestWithdrawalId;
    }

    public AppConfiguration requestWithdrawalId(String requestWithdrawalId) {
        this.setRequestWithdrawalId(requestWithdrawalId);
        return this;
    }

    public void setRequestWithdrawalId(String requestWithdrawalId) {
        this.requestWithdrawalId = requestWithdrawalId;
    }

    public String getTransactionsBuyId() {
        return this.transactionsBuyId;
    }

    public AppConfiguration transactionsBuyId(String transactionsBuyId) {
        this.setTransactionsBuyId(transactionsBuyId);
        return this;
    }

    public void setTransactionsBuyId(String transactionsBuyId) {
        this.transactionsBuyId = transactionsBuyId;
    }

    public String getTransactionsSellId() {
        return this.transactionsSellId;
    }

    public AppConfiguration transactionsSellId(String transactionsSellId) {
        this.setTransactionsSellId(transactionsSellId);
        return this;
    }

    public void setTransactionsSellId(String transactionsSellId) {
        this.transactionsSellId = transactionsSellId;
    }

    public String getWithdrawFiatDesc() {
        return this.withdrawFiatDesc;
    }

    public AppConfiguration withdrawFiatDesc(String withdrawFiatDesc) {
        this.setWithdrawFiatDesc(withdrawFiatDesc);
        return this;
    }

    public void setWithdrawFiatDesc(String withdrawFiatDesc) {
        this.withdrawFiatDesc = withdrawFiatDesc;
    }

    public String getWithdrawBtcDesc() {
        return this.withdrawBtcDesc;
    }

    public AppConfiguration withdrawBtcDesc(String withdrawBtcDesc) {
        this.setWithdrawBtcDesc(withdrawBtcDesc);
        return this;
    }

    public void setWithdrawBtcDesc(String withdrawBtcDesc) {
        this.withdrawBtcDesc = withdrawBtcDesc;
    }

    public String getFormEmailFrom() {
        return this.formEmailFrom;
    }

    public AppConfiguration formEmailFrom(String formEmailFrom) {
        this.setFormEmailFrom(formEmailFrom);
        return this;
    }

    public void setFormEmailFrom(String formEmailFrom) {
        this.formEmailFrom = formEmailFrom;
    }

    public YesNo getEmailNotifyNewUsers() {
        return this.emailNotifyNewUsers;
    }

    public AppConfiguration emailNotifyNewUsers(YesNo emailNotifyNewUsers) {
        this.setEmailNotifyNewUsers(emailNotifyNewUsers);
        return this;
    }

    public void setEmailNotifyNewUsers(YesNo emailNotifyNewUsers) {
        this.emailNotifyNewUsers = emailNotifyNewUsers;
    }

    public String getPassRegex() {
        return this.passRegex;
    }

    public AppConfiguration passRegex(String passRegex) {
        this.setPassRegex(passRegex);
        return this;
    }

    public void setPassRegex(String passRegex) {
        this.passRegex = passRegex;
    }

    public String getPassMinChars() {
        return this.passMinChars;
    }

    public AppConfiguration passMinChars(String passMinChars) {
        this.setPassMinChars(passMinChars);
        return this;
    }

    public void setPassMinChars(String passMinChars) {
        this.passMinChars = passMinChars;
    }

    public YesNo getAuthDbDebug() {
        return this.authDbDebug;
    }

    public AppConfiguration authDbDebug(YesNo authDbDebug) {
        this.setAuthDbDebug(authDbDebug);
        return this;
    }

    public void setAuthDbDebug(YesNo authDbDebug) {
        this.authDbDebug = authDbDebug;
    }

    public String getBitcoinReserveMin() {
        return this.bitcoinReserveMin;
    }

    public AppConfiguration bitcoinReserveMin(String bitcoinReserveMin) {
        this.setBitcoinReserveMin(bitcoinReserveMin);
        return this;
    }

    public void setBitcoinReserveMin(String bitcoinReserveMin) {
        this.bitcoinReserveMin = bitcoinReserveMin;
    }

    public String getBitcoinReserveRatio() {
        return this.bitcoinReserveRatio;
    }

    public AppConfiguration bitcoinReserveRatio(String bitcoinReserveRatio) {
        this.setBitcoinReserveRatio(bitcoinReserveRatio);
        return this;
    }

    public void setBitcoinReserveRatio(String bitcoinReserveRatio) {
        this.bitcoinReserveRatio = bitcoinReserveRatio;
    }

    public String getBitcoinWarmWalletAddress() {
        return this.bitcoinWarmWalletAddress;
    }

    public AppConfiguration bitcoinWarmWalletAddress(String bitcoinWarmWalletAddress) {
        this.setBitcoinWarmWalletAddress(bitcoinWarmWalletAddress);
        return this;
    }

    public void setBitcoinWarmWalletAddress(String bitcoinWarmWalletAddress) {
        this.bitcoinWarmWalletAddress = bitcoinWarmWalletAddress;
    }

    public YesNo getCronDbDebug() {
        return this.cronDbDebug;
    }

    public AppConfiguration cronDbDebug(YesNo cronDbDebug) {
        this.setCronDbDebug(cronDbDebug);
        return this;
    }

    public void setCronDbDebug(YesNo cronDbDebug) {
        this.cronDbDebug = cronDbDebug;
    }

    public String getQuandlApiKey() {
        return this.quandlApiKey;
    }

    public AppConfiguration quandlApiKey(String quandlApiKey) {
        this.setQuandlApiKey(quandlApiKey);
        return this;
    }

    public void setQuandlApiKey(String quandlApiKey) {
        this.quandlApiKey = quandlApiKey;
    }

    public String getCronDirroot() {
        return this.cronDirroot;
    }

    public AppConfiguration cronDirroot(String cronDirroot) {
        this.setCronDirroot(cronDirroot);
        return this;
    }

    public void setCronDirroot(String cronDirroot) {
        this.cronDirroot = cronDirroot;
    }

    public YesNo getBackstageDbDebug() {
        return this.backstageDbDebug;
    }

    public AppConfiguration backstageDbDebug(YesNo backstageDbDebug) {
        this.setBackstageDbDebug(backstageDbDebug);
        return this;
    }

    public void setBackstageDbDebug(YesNo backstageDbDebug) {
        this.backstageDbDebug = backstageDbDebug;
    }

    public String getBackstageDirroot() {
        return this.backstageDirroot;
    }

    public AppConfiguration backstageDirroot(String backstageDirroot) {
        this.setBackstageDirroot(backstageDirroot);
        return this;
    }

    public void setBackstageDirroot(String backstageDirroot) {
        this.backstageDirroot = backstageDirroot;
    }

    public YesNo getEmailNotifyFiatWithdrawals() {
        return this.emailNotifyFiatWithdrawals;
    }

    public AppConfiguration emailNotifyFiatWithdrawals(YesNo emailNotifyFiatWithdrawals) {
        this.setEmailNotifyFiatWithdrawals(emailNotifyFiatWithdrawals);
        return this;
    }

    public void setEmailNotifyFiatWithdrawals(YesNo emailNotifyFiatWithdrawals) {
        this.emailNotifyFiatWithdrawals = emailNotifyFiatWithdrawals;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public AppConfiguration contactEmail(String contactEmail) {
        this.setContactEmail(contactEmail);
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCloudflareApiKey() {
        return this.cloudflareApiKey;
    }

    public AppConfiguration cloudflareApiKey(String cloudflareApiKey) {
        this.setCloudflareApiKey(cloudflareApiKey);
        return this;
    }

    public void setCloudflareApiKey(String cloudflareApiKey) {
        this.cloudflareApiKey = cloudflareApiKey;
    }

    public String getGoogleRecaptchApiKey() {
        return this.googleRecaptchApiKey;
    }

    public AppConfiguration googleRecaptchApiKey(String googleRecaptchApiKey) {
        this.setGoogleRecaptchApiKey(googleRecaptchApiKey);
        return this;
    }

    public void setGoogleRecaptchApiKey(String googleRecaptchApiKey) {
        this.googleRecaptchApiKey = googleRecaptchApiKey;
    }

    public YesNo getCloudflareBlacklist() {
        return this.cloudflareBlacklist;
    }

    public AppConfiguration cloudflareBlacklist(YesNo cloudflareBlacklist) {
        this.setCloudflareBlacklist(cloudflareBlacklist);
        return this;
    }

    public void setCloudflareBlacklist(YesNo cloudflareBlacklist) {
        this.cloudflareBlacklist = cloudflareBlacklist;
    }

    public String getCloudflareEmail() {
        return this.cloudflareEmail;
    }

    public AppConfiguration cloudflareEmail(String cloudflareEmail) {
        this.setCloudflareEmail(cloudflareEmail);
        return this;
    }

    public void setCloudflareEmail(String cloudflareEmail) {
        this.cloudflareEmail = cloudflareEmail;
    }

    public String getGoogleRecaptchApiSecret() {
        return this.googleRecaptchApiSecret;
    }

    public AppConfiguration googleRecaptchApiSecret(String googleRecaptchApiSecret) {
        this.setGoogleRecaptchApiSecret(googleRecaptchApiSecret);
        return this;
    }

    public void setGoogleRecaptchApiSecret(String googleRecaptchApiSecret) {
        this.googleRecaptchApiSecret = googleRecaptchApiSecret;
    }

    public Integer getCloudflareBlacklistAttempts() {
        return this.cloudflareBlacklistAttempts;
    }

    public AppConfiguration cloudflareBlacklistAttempts(Integer cloudflareBlacklistAttempts) {
        this.setCloudflareBlacklistAttempts(cloudflareBlacklistAttempts);
        return this;
    }

    public void setCloudflareBlacklistAttempts(Integer cloudflareBlacklistAttempts) {
        this.cloudflareBlacklistAttempts = cloudflareBlacklistAttempts;
    }

    public Double getCloudflareBlacklistTimeframe() {
        return this.cloudflareBlacklistTimeframe;
    }

    public AppConfiguration cloudflareBlacklistTimeframe(Double cloudflareBlacklistTimeframe) {
        this.setCloudflareBlacklistTimeframe(cloudflareBlacklistTimeframe);
        return this;
    }

    public void setCloudflareBlacklistTimeframe(Double cloudflareBlacklistTimeframe) {
        this.cloudflareBlacklistTimeframe = cloudflareBlacklistTimeframe;
    }

    public String getCryptoCapitalPk() {
        return this.cryptoCapitalPk;
    }

    public AppConfiguration cryptoCapitalPk(String cryptoCapitalPk) {
        this.setCryptoCapitalPk(cryptoCapitalPk);
        return this;
    }

    public void setCryptoCapitalPk(String cryptoCapitalPk) {
        this.cryptoCapitalPk = cryptoCapitalPk;
    }

    public String getDepositFiatDesc() {
        return this.depositFiatDesc;
    }

    public AppConfiguration depositFiatDesc(String depositFiatDesc) {
        this.setDepositFiatDesc(depositFiatDesc);
        return this;
    }

    public void setDepositFiatDesc(String depositFiatDesc) {
        this.depositFiatDesc = depositFiatDesc;
    }

    public YesNo getEmailNotifyFiatFailed() {
        return this.emailNotifyFiatFailed;
    }

    public AppConfiguration emailNotifyFiatFailed(YesNo emailNotifyFiatFailed) {
        this.setEmailNotifyFiatFailed(emailNotifyFiatFailed);
        return this;
    }

    public void setEmailNotifyFiatFailed(YesNo emailNotifyFiatFailed) {
        this.emailNotifyFiatFailed = emailNotifyFiatFailed;
    }

    public String getTradingStatus() {
        return this.tradingStatus;
    }

    public AppConfiguration tradingStatus(String tradingStatus) {
        this.setTradingStatus(tradingStatus);
        return this;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getWithdrawalsStatus() {
        return this.withdrawalsStatus;
    }

    public AppConfiguration withdrawalsStatus(String withdrawalsStatus) {
        this.setWithdrawalsStatus(withdrawalsStatus);
        return this;
    }

    public void setWithdrawalsStatus(String withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfiguration)) {
            return false;
        }
        return id != null && id.equals(((AppConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConfiguration{" +
            "id=" + getId() +
            ", defaultTimezone='" + getDefaultTimezone() + "'" +
            ", ordersUnderMarketPercent='" + getOrdersUnderMarketPercent() + "'" +
            ", ordersMinUsd='" + getOrdersMinUsd() + "'" +
            ", bitcoinSendingFee='" + getBitcoinSendingFee() + "'" +
            ", frontendBaseurl='" + getFrontendBaseurl() + "'" +
            ", frontendDirroot='" + getFrontendDirroot() + "'" +
            ", fiatWithdrawFee='" + getFiatWithdrawFee() + "'" +
            ", apiDbDebug='" + getApiDbDebug() + "'" +
            ", apiDirroot='" + getApiDirroot() + "'" +
            ", supportEmail='" + getSupportEmail() + "'" +
            ", emailSmtpHost='" + getEmailSmtpHost() + "'" +
            ", emailSmtpPort='" + getEmailSmtpPort() + "'" +
            ", emailSmtpSecurity='" + getEmailSmtpSecurity() + "'" +
            ", emailSmtpUsername='" + getEmailSmtpUsername() + "'" +
            ", emailSmtpPassword='" + getEmailSmtpPassword() + "'" +
            ", emailSmtpSendFrom='" + getEmailSmtpSendFrom() + "'" +
            ", bitcoinUsername='" + getBitcoinUsername() + "'" +
            ", bitcoinAccountname='" + getBitcoinAccountname() + "'" +
            ", bitcoinPassphrase='" + getBitcoinPassphrase() + "'" +
            ", bitcoinHost='" + getBitcoinHost() + "'" +
            ", bitcoinPort='" + getBitcoinPort() + "'" +
            ", bitcoinProtocol='" + getBitcoinProtocol() + "'" +
            ", authyApiKey='" + getAuthyApiKey() + "'" +
            ", helpdeskKey='" + getHelpdeskKey() + "'" +
            ", exchangeName='" + getExchangeName() + "'" +
            ", mcryptKey='" + getMcryptKey() + "'" +
            ", currencyConversionFee='" + getCurrencyConversionFee() + "'" +
            ", crossCurrencyTrades='" + getCrossCurrencyTrades() + "'" +
            ", btcCurrencyId='" + getBtcCurrencyId() + "'" +
            ", depositBitcoinDesc='" + getDepositBitcoinDesc() + "'" +
            ", defaultFeeScheduleId='" + getDefaultFeeScheduleId() + "'" +
            ", historyBuyId='" + getHistoryBuyId() + "'" +
            ", historyDepositId='" + getHistoryDepositId() + "'" +
            ", historyLoginId='" + getHistoryLoginId() + "'" +
            ", historySellId='" + getHistorySellId() + "'" +
            ", historyWithdrawId='" + getHistoryWithdrawId() + "'" +
            ", orderTypeAsk='" + getOrderTypeAsk() + "'" +
            ", requestAwaitingId='" + getRequestAwaitingId() + "'" +
            ", requestCancelledId='" + getRequestCancelledId() + "'" +
            ", requestCompletedId='" + getRequestCompletedId() + "'" +
            ", orderTypeBid='" + getOrderTypeBid() + "'" +
            ", requestDepositId='" + getRequestDepositId() + "'" +
            ", requestPendingId='" + getRequestPendingId() + "'" +
            ", requestWithdrawalId='" + getRequestWithdrawalId() + "'" +
            ", transactionsBuyId='" + getTransactionsBuyId() + "'" +
            ", transactionsSellId='" + getTransactionsSellId() + "'" +
            ", withdrawFiatDesc='" + getWithdrawFiatDesc() + "'" +
            ", withdrawBtcDesc='" + getWithdrawBtcDesc() + "'" +
            ", formEmailFrom='" + getFormEmailFrom() + "'" +
            ", emailNotifyNewUsers='" + getEmailNotifyNewUsers() + "'" +
            ", passRegex='" + getPassRegex() + "'" +
            ", passMinChars='" + getPassMinChars() + "'" +
            ", authDbDebug='" + getAuthDbDebug() + "'" +
            ", bitcoinReserveMin='" + getBitcoinReserveMin() + "'" +
            ", bitcoinReserveRatio='" + getBitcoinReserveRatio() + "'" +
            ", bitcoinWarmWalletAddress='" + getBitcoinWarmWalletAddress() + "'" +
            ", cronDbDebug='" + getCronDbDebug() + "'" +
            ", quandlApiKey='" + getQuandlApiKey() + "'" +
            ", cronDirroot='" + getCronDirroot() + "'" +
            ", backstageDbDebug='" + getBackstageDbDebug() + "'" +
            ", backstageDirroot='" + getBackstageDirroot() + "'" +
            ", emailNotifyFiatWithdrawals='" + getEmailNotifyFiatWithdrawals() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", cloudflareApiKey='" + getCloudflareApiKey() + "'" +
            ", googleRecaptchApiKey='" + getGoogleRecaptchApiKey() + "'" +
            ", cloudflareBlacklist='" + getCloudflareBlacklist() + "'" +
            ", cloudflareEmail='" + getCloudflareEmail() + "'" +
            ", googleRecaptchApiSecret='" + getGoogleRecaptchApiSecret() + "'" +
            ", cloudflareBlacklistAttempts=" + getCloudflareBlacklistAttempts() +
            ", cloudflareBlacklistTimeframe=" + getCloudflareBlacklistTimeframe() +
            ", cryptoCapitalPk='" + getCryptoCapitalPk() + "'" +
            ", depositFiatDesc='" + getDepositFiatDesc() + "'" +
            ", emailNotifyFiatFailed='" + getEmailNotifyFiatFailed() + "'" +
            ", tradingStatus='" + getTradingStatus() + "'" +
            ", withdrawalsStatus='" + getWithdrawalsStatus() + "'" +
            "}";
    }
}
