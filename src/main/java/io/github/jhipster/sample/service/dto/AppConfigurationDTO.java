package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.AppConfiguration} entity.
 */
public class AppConfigurationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String defaultTimezone;

    @NotNull
    @Size(max = 255)
    private String ordersUnderMarketPercent;

    @NotNull
    @Size(max = 255)
    private String ordersMinUsd;

    @NotNull
    @Size(max = 255)
    private String bitcoinSendingFee;

    @NotNull
    @Size(max = 255)
    private String frontendBaseurl;

    @NotNull
    @Size(max = 255)
    private String frontendDirroot;

    @NotNull
    @Size(max = 255)
    private String fiatWithdrawFee;

    @NotNull
    private YesNo apiDbDebug;

    @NotNull
    @Size(max = 255)
    private String apiDirroot;

    @NotNull
    @Size(max = 255)
    private String supportEmail;

    @NotNull
    @Size(max = 255)
    private String emailSmtpHost;

    @NotNull
    @Size(max = 255)
    private String emailSmtpPort;

    @NotNull
    @Size(max = 255)
    private String emailSmtpSecurity;

    @NotNull
    @Size(max = 255)
    private String emailSmtpUsername;

    @NotNull
    @Size(max = 255)
    private String emailSmtpPassword;

    @NotNull
    @Size(max = 255)
    private String emailSmtpSendFrom;

    @NotNull
    @Size(max = 255)
    private String bitcoinUsername;

    @NotNull
    @Size(max = 255)
    private String bitcoinAccountname;

    @NotNull
    @Size(max = 255)
    private String bitcoinPassphrase;

    @NotNull
    @Size(max = 255)
    private String bitcoinHost;

    @NotNull
    @Size(max = 255)
    private String bitcoinPort;

    @NotNull
    @Size(max = 255)
    private String bitcoinProtocol;

    @NotNull
    @Size(max = 255)
    private String authyApiKey;

    @NotNull
    @Size(max = 255)
    private String helpdeskKey;

    @NotNull
    @Size(max = 255)
    private String exchangeName;

    @NotNull
    @Size(max = 255)
    private String mcryptKey;

    @NotNull
    @Size(max = 255)
    private String currencyConversionFee;

    @NotNull
    private YesNo crossCurrencyTrades;

    @NotNull
    @Size(max = 255)
    private String btcCurrencyId;

    @NotNull
    @Size(max = 255)
    private String depositBitcoinDesc;

    @NotNull
    @Size(max = 255)
    private String defaultFeeScheduleId;

    @NotNull
    @Size(max = 255)
    private String historyBuyId;

    @NotNull
    @Size(max = 255)
    private String historyDepositId;

    @NotNull
    @Size(max = 255)
    private String historyLoginId;

    @NotNull
    @Size(max = 255)
    private String historySellId;

    @NotNull
    @Size(max = 255)
    private String historyWithdrawId;

    @NotNull
    @Size(max = 255)
    private String orderTypeAsk;

    @NotNull
    @Size(max = 255)
    private String requestAwaitingId;

    @NotNull
    @Size(max = 255)
    private String requestCancelledId;

    @NotNull
    @Size(max = 255)
    private String requestCompletedId;

    @NotNull
    @Size(max = 255)
    private String orderTypeBid;

    @NotNull
    @Size(max = 255)
    private String requestDepositId;

    @NotNull
    @Size(max = 255)
    private String requestPendingId;

    @NotNull
    @Size(max = 255)
    private String requestWithdrawalId;

    @NotNull
    @Size(max = 255)
    private String transactionsBuyId;

    @NotNull
    @Size(max = 255)
    private String transactionsSellId;

    @NotNull
    @Size(max = 255)
    private String withdrawFiatDesc;

    @NotNull
    @Size(max = 255)
    private String withdrawBtcDesc;

    @NotNull
    @Size(max = 255)
    private String formEmailFrom;

    @NotNull
    private YesNo emailNotifyNewUsers;

    @NotNull
    @Size(max = 255)
    private String passRegex;

    @NotNull
    @Size(max = 255)
    private String passMinChars;

    @NotNull
    private YesNo authDbDebug;

    @NotNull
    @Size(max = 255)
    private String bitcoinReserveMin;

    @NotNull
    @Size(max = 255)
    private String bitcoinReserveRatio;

    @NotNull
    @Size(max = 255)
    private String bitcoinWarmWalletAddress;

    @NotNull
    private YesNo cronDbDebug;

    @NotNull
    @Size(max = 255)
    private String quandlApiKey;

    @NotNull
    @Size(max = 255)
    private String cronDirroot;

    @NotNull
    private YesNo backstageDbDebug;

    @NotNull
    @Size(max = 255)
    private String backstageDirroot;

    @NotNull
    private YesNo emailNotifyFiatWithdrawals;

    @NotNull
    @Size(max = 255)
    private String contactEmail;

    @NotNull
    @Size(max = 255)
    private String cloudflareApiKey;

    @NotNull
    @Size(max = 255)
    private String googleRecaptchApiKey;

    @NotNull
    private YesNo cloudflareBlacklist;

    @NotNull
    @Size(max = 255)
    private String cloudflareEmail;

    @NotNull
    @Size(max = 255)
    private String googleRecaptchApiSecret;

    @NotNull
    private Integer cloudflareBlacklistAttempts;

    @NotNull
    private Double cloudflareBlacklistTimeframe;

    @NotNull
    @Size(max = 255)
    private String cryptoCapitalPk;

    @NotNull
    @Size(max = 255)
    private String depositFiatDesc;

    @NotNull
    private YesNo emailNotifyFiatFailed;

    @NotNull
    @Size(max = 255)
    private String tradingStatus;

    @NotNull
    @Size(max = 255)
    private String withdrawalsStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultTimezone() {
        return defaultTimezone;
    }

    public void setDefaultTimezone(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    public String getOrdersUnderMarketPercent() {
        return ordersUnderMarketPercent;
    }

    public void setOrdersUnderMarketPercent(String ordersUnderMarketPercent) {
        this.ordersUnderMarketPercent = ordersUnderMarketPercent;
    }

    public String getOrdersMinUsd() {
        return ordersMinUsd;
    }

    public void setOrdersMinUsd(String ordersMinUsd) {
        this.ordersMinUsd = ordersMinUsd;
    }

    public String getBitcoinSendingFee() {
        return bitcoinSendingFee;
    }

    public void setBitcoinSendingFee(String bitcoinSendingFee) {
        this.bitcoinSendingFee = bitcoinSendingFee;
    }

    public String getFrontendBaseurl() {
        return frontendBaseurl;
    }

    public void setFrontendBaseurl(String frontendBaseurl) {
        this.frontendBaseurl = frontendBaseurl;
    }

    public String getFrontendDirroot() {
        return frontendDirroot;
    }

    public void setFrontendDirroot(String frontendDirroot) {
        this.frontendDirroot = frontendDirroot;
    }

    public String getFiatWithdrawFee() {
        return fiatWithdrawFee;
    }

    public void setFiatWithdrawFee(String fiatWithdrawFee) {
        this.fiatWithdrawFee = fiatWithdrawFee;
    }

    public YesNo getApiDbDebug() {
        return apiDbDebug;
    }

    public void setApiDbDebug(YesNo apiDbDebug) {
        this.apiDbDebug = apiDbDebug;
    }

    public String getApiDirroot() {
        return apiDirroot;
    }

    public void setApiDirroot(String apiDirroot) {
        this.apiDirroot = apiDirroot;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getEmailSmtpHost() {
        return emailSmtpHost;
    }

    public void setEmailSmtpHost(String emailSmtpHost) {
        this.emailSmtpHost = emailSmtpHost;
    }

    public String getEmailSmtpPort() {
        return emailSmtpPort;
    }

    public void setEmailSmtpPort(String emailSmtpPort) {
        this.emailSmtpPort = emailSmtpPort;
    }

    public String getEmailSmtpSecurity() {
        return emailSmtpSecurity;
    }

    public void setEmailSmtpSecurity(String emailSmtpSecurity) {
        this.emailSmtpSecurity = emailSmtpSecurity;
    }

    public String getEmailSmtpUsername() {
        return emailSmtpUsername;
    }

    public void setEmailSmtpUsername(String emailSmtpUsername) {
        this.emailSmtpUsername = emailSmtpUsername;
    }

    public String getEmailSmtpPassword() {
        return emailSmtpPassword;
    }

    public void setEmailSmtpPassword(String emailSmtpPassword) {
        this.emailSmtpPassword = emailSmtpPassword;
    }

    public String getEmailSmtpSendFrom() {
        return emailSmtpSendFrom;
    }

    public void setEmailSmtpSendFrom(String emailSmtpSendFrom) {
        this.emailSmtpSendFrom = emailSmtpSendFrom;
    }

    public String getBitcoinUsername() {
        return bitcoinUsername;
    }

    public void setBitcoinUsername(String bitcoinUsername) {
        this.bitcoinUsername = bitcoinUsername;
    }

    public String getBitcoinAccountname() {
        return bitcoinAccountname;
    }

    public void setBitcoinAccountname(String bitcoinAccountname) {
        this.bitcoinAccountname = bitcoinAccountname;
    }

    public String getBitcoinPassphrase() {
        return bitcoinPassphrase;
    }

    public void setBitcoinPassphrase(String bitcoinPassphrase) {
        this.bitcoinPassphrase = bitcoinPassphrase;
    }

    public String getBitcoinHost() {
        return bitcoinHost;
    }

    public void setBitcoinHost(String bitcoinHost) {
        this.bitcoinHost = bitcoinHost;
    }

    public String getBitcoinPort() {
        return bitcoinPort;
    }

    public void setBitcoinPort(String bitcoinPort) {
        this.bitcoinPort = bitcoinPort;
    }

    public String getBitcoinProtocol() {
        return bitcoinProtocol;
    }

    public void setBitcoinProtocol(String bitcoinProtocol) {
        this.bitcoinProtocol = bitcoinProtocol;
    }

    public String getAuthyApiKey() {
        return authyApiKey;
    }

    public void setAuthyApiKey(String authyApiKey) {
        this.authyApiKey = authyApiKey;
    }

    public String getHelpdeskKey() {
        return helpdeskKey;
    }

    public void setHelpdeskKey(String helpdeskKey) {
        this.helpdeskKey = helpdeskKey;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getMcryptKey() {
        return mcryptKey;
    }

    public void setMcryptKey(String mcryptKey) {
        this.mcryptKey = mcryptKey;
    }

    public String getCurrencyConversionFee() {
        return currencyConversionFee;
    }

    public void setCurrencyConversionFee(String currencyConversionFee) {
        this.currencyConversionFee = currencyConversionFee;
    }

    public YesNo getCrossCurrencyTrades() {
        return crossCurrencyTrades;
    }

    public void setCrossCurrencyTrades(YesNo crossCurrencyTrades) {
        this.crossCurrencyTrades = crossCurrencyTrades;
    }

    public String getBtcCurrencyId() {
        return btcCurrencyId;
    }

    public void setBtcCurrencyId(String btcCurrencyId) {
        this.btcCurrencyId = btcCurrencyId;
    }

    public String getDepositBitcoinDesc() {
        return depositBitcoinDesc;
    }

    public void setDepositBitcoinDesc(String depositBitcoinDesc) {
        this.depositBitcoinDesc = depositBitcoinDesc;
    }

    public String getDefaultFeeScheduleId() {
        return defaultFeeScheduleId;
    }

    public void setDefaultFeeScheduleId(String defaultFeeScheduleId) {
        this.defaultFeeScheduleId = defaultFeeScheduleId;
    }

    public String getHistoryBuyId() {
        return historyBuyId;
    }

    public void setHistoryBuyId(String historyBuyId) {
        this.historyBuyId = historyBuyId;
    }

    public String getHistoryDepositId() {
        return historyDepositId;
    }

    public void setHistoryDepositId(String historyDepositId) {
        this.historyDepositId = historyDepositId;
    }

    public String getHistoryLoginId() {
        return historyLoginId;
    }

    public void setHistoryLoginId(String historyLoginId) {
        this.historyLoginId = historyLoginId;
    }

    public String getHistorySellId() {
        return historySellId;
    }

    public void setHistorySellId(String historySellId) {
        this.historySellId = historySellId;
    }

    public String getHistoryWithdrawId() {
        return historyWithdrawId;
    }

    public void setHistoryWithdrawId(String historyWithdrawId) {
        this.historyWithdrawId = historyWithdrawId;
    }

    public String getOrderTypeAsk() {
        return orderTypeAsk;
    }

    public void setOrderTypeAsk(String orderTypeAsk) {
        this.orderTypeAsk = orderTypeAsk;
    }

    public String getRequestAwaitingId() {
        return requestAwaitingId;
    }

    public void setRequestAwaitingId(String requestAwaitingId) {
        this.requestAwaitingId = requestAwaitingId;
    }

    public String getRequestCancelledId() {
        return requestCancelledId;
    }

    public void setRequestCancelledId(String requestCancelledId) {
        this.requestCancelledId = requestCancelledId;
    }

    public String getRequestCompletedId() {
        return requestCompletedId;
    }

    public void setRequestCompletedId(String requestCompletedId) {
        this.requestCompletedId = requestCompletedId;
    }

    public String getOrderTypeBid() {
        return orderTypeBid;
    }

    public void setOrderTypeBid(String orderTypeBid) {
        this.orderTypeBid = orderTypeBid;
    }

    public String getRequestDepositId() {
        return requestDepositId;
    }

    public void setRequestDepositId(String requestDepositId) {
        this.requestDepositId = requestDepositId;
    }

    public String getRequestPendingId() {
        return requestPendingId;
    }

    public void setRequestPendingId(String requestPendingId) {
        this.requestPendingId = requestPendingId;
    }

    public String getRequestWithdrawalId() {
        return requestWithdrawalId;
    }

    public void setRequestWithdrawalId(String requestWithdrawalId) {
        this.requestWithdrawalId = requestWithdrawalId;
    }

    public String getTransactionsBuyId() {
        return transactionsBuyId;
    }

    public void setTransactionsBuyId(String transactionsBuyId) {
        this.transactionsBuyId = transactionsBuyId;
    }

    public String getTransactionsSellId() {
        return transactionsSellId;
    }

    public void setTransactionsSellId(String transactionsSellId) {
        this.transactionsSellId = transactionsSellId;
    }

    public String getWithdrawFiatDesc() {
        return withdrawFiatDesc;
    }

    public void setWithdrawFiatDesc(String withdrawFiatDesc) {
        this.withdrawFiatDesc = withdrawFiatDesc;
    }

    public String getWithdrawBtcDesc() {
        return withdrawBtcDesc;
    }

    public void setWithdrawBtcDesc(String withdrawBtcDesc) {
        this.withdrawBtcDesc = withdrawBtcDesc;
    }

    public String getFormEmailFrom() {
        return formEmailFrom;
    }

    public void setFormEmailFrom(String formEmailFrom) {
        this.formEmailFrom = formEmailFrom;
    }

    public YesNo getEmailNotifyNewUsers() {
        return emailNotifyNewUsers;
    }

    public void setEmailNotifyNewUsers(YesNo emailNotifyNewUsers) {
        this.emailNotifyNewUsers = emailNotifyNewUsers;
    }

    public String getPassRegex() {
        return passRegex;
    }

    public void setPassRegex(String passRegex) {
        this.passRegex = passRegex;
    }

    public String getPassMinChars() {
        return passMinChars;
    }

    public void setPassMinChars(String passMinChars) {
        this.passMinChars = passMinChars;
    }

    public YesNo getAuthDbDebug() {
        return authDbDebug;
    }

    public void setAuthDbDebug(YesNo authDbDebug) {
        this.authDbDebug = authDbDebug;
    }

    public String getBitcoinReserveMin() {
        return bitcoinReserveMin;
    }

    public void setBitcoinReserveMin(String bitcoinReserveMin) {
        this.bitcoinReserveMin = bitcoinReserveMin;
    }

    public String getBitcoinReserveRatio() {
        return bitcoinReserveRatio;
    }

    public void setBitcoinReserveRatio(String bitcoinReserveRatio) {
        this.bitcoinReserveRatio = bitcoinReserveRatio;
    }

    public String getBitcoinWarmWalletAddress() {
        return bitcoinWarmWalletAddress;
    }

    public void setBitcoinWarmWalletAddress(String bitcoinWarmWalletAddress) {
        this.bitcoinWarmWalletAddress = bitcoinWarmWalletAddress;
    }

    public YesNo getCronDbDebug() {
        return cronDbDebug;
    }

    public void setCronDbDebug(YesNo cronDbDebug) {
        this.cronDbDebug = cronDbDebug;
    }

    public String getQuandlApiKey() {
        return quandlApiKey;
    }

    public void setQuandlApiKey(String quandlApiKey) {
        this.quandlApiKey = quandlApiKey;
    }

    public String getCronDirroot() {
        return cronDirroot;
    }

    public void setCronDirroot(String cronDirroot) {
        this.cronDirroot = cronDirroot;
    }

    public YesNo getBackstageDbDebug() {
        return backstageDbDebug;
    }

    public void setBackstageDbDebug(YesNo backstageDbDebug) {
        this.backstageDbDebug = backstageDbDebug;
    }

    public String getBackstageDirroot() {
        return backstageDirroot;
    }

    public void setBackstageDirroot(String backstageDirroot) {
        this.backstageDirroot = backstageDirroot;
    }

    public YesNo getEmailNotifyFiatWithdrawals() {
        return emailNotifyFiatWithdrawals;
    }

    public void setEmailNotifyFiatWithdrawals(YesNo emailNotifyFiatWithdrawals) {
        this.emailNotifyFiatWithdrawals = emailNotifyFiatWithdrawals;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCloudflareApiKey() {
        return cloudflareApiKey;
    }

    public void setCloudflareApiKey(String cloudflareApiKey) {
        this.cloudflareApiKey = cloudflareApiKey;
    }

    public String getGoogleRecaptchApiKey() {
        return googleRecaptchApiKey;
    }

    public void setGoogleRecaptchApiKey(String googleRecaptchApiKey) {
        this.googleRecaptchApiKey = googleRecaptchApiKey;
    }

    public YesNo getCloudflareBlacklist() {
        return cloudflareBlacklist;
    }

    public void setCloudflareBlacklist(YesNo cloudflareBlacklist) {
        this.cloudflareBlacklist = cloudflareBlacklist;
    }

    public String getCloudflareEmail() {
        return cloudflareEmail;
    }

    public void setCloudflareEmail(String cloudflareEmail) {
        this.cloudflareEmail = cloudflareEmail;
    }

    public String getGoogleRecaptchApiSecret() {
        return googleRecaptchApiSecret;
    }

    public void setGoogleRecaptchApiSecret(String googleRecaptchApiSecret) {
        this.googleRecaptchApiSecret = googleRecaptchApiSecret;
    }

    public Integer getCloudflareBlacklistAttempts() {
        return cloudflareBlacklistAttempts;
    }

    public void setCloudflareBlacklistAttempts(Integer cloudflareBlacklistAttempts) {
        this.cloudflareBlacklistAttempts = cloudflareBlacklistAttempts;
    }

    public Double getCloudflareBlacklistTimeframe() {
        return cloudflareBlacklistTimeframe;
    }

    public void setCloudflareBlacklistTimeframe(Double cloudflareBlacklistTimeframe) {
        this.cloudflareBlacklistTimeframe = cloudflareBlacklistTimeframe;
    }

    public String getCryptoCapitalPk() {
        return cryptoCapitalPk;
    }

    public void setCryptoCapitalPk(String cryptoCapitalPk) {
        this.cryptoCapitalPk = cryptoCapitalPk;
    }

    public String getDepositFiatDesc() {
        return depositFiatDesc;
    }

    public void setDepositFiatDesc(String depositFiatDesc) {
        this.depositFiatDesc = depositFiatDesc;
    }

    public YesNo getEmailNotifyFiatFailed() {
        return emailNotifyFiatFailed;
    }

    public void setEmailNotifyFiatFailed(YesNo emailNotifyFiatFailed) {
        this.emailNotifyFiatFailed = emailNotifyFiatFailed;
    }

    public String getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getWithdrawalsStatus() {
        return withdrawalsStatus;
    }

    public void setWithdrawalsStatus(String withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppConfigurationDTO)) {
            return false;
        }

        AppConfigurationDTO appConfigurationDTO = (AppConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppConfigurationDTO{" +
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
