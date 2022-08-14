import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IAppConfiguration {
  id?: number;
  defaultTimezone?: string;
  ordersUnderMarketPercent?: string;
  ordersMinUsd?: string;
  bitcoinSendingFee?: string;
  frontendBaseurl?: string;
  frontendDirroot?: string;
  fiatWithdrawFee?: string;
  apiDbDebug?: YesNo;
  apiDirroot?: string;
  supportEmail?: string;
  emailSmtpHost?: string;
  emailSmtpPort?: string;
  emailSmtpSecurity?: string;
  emailSmtpUsername?: string;
  emailSmtpPassword?: string;
  emailSmtpSendFrom?: string;
  bitcoinUsername?: string;
  bitcoinAccountname?: string;
  bitcoinPassphrase?: string;
  bitcoinHost?: string;
  bitcoinPort?: string;
  bitcoinProtocol?: string;
  authyApiKey?: string;
  helpdeskKey?: string;
  exchangeName?: string;
  mcryptKey?: string;
  currencyConversionFee?: string;
  crossCurrencyTrades?: YesNo;
  btcCurrencyId?: string;
  depositBitcoinDesc?: string;
  defaultFeeScheduleId?: string;
  historyBuyId?: string;
  historyDepositId?: string;
  historyLoginId?: string;
  historySellId?: string;
  historyWithdrawId?: string;
  orderTypeAsk?: string;
  requestAwaitingId?: string;
  requestCancelledId?: string;
  requestCompletedId?: string;
  orderTypeBid?: string;
  requestDepositId?: string;
  requestPendingId?: string;
  requestWithdrawalId?: string;
  transactionsBuyId?: string;
  transactionsSellId?: string;
  withdrawFiatDesc?: string;
  withdrawBtcDesc?: string;
  formEmailFrom?: string;
  emailNotifyNewUsers?: YesNo;
  passRegex?: string;
  passMinChars?: string;
  authDbDebug?: YesNo;
  bitcoinReserveMin?: string;
  bitcoinReserveRatio?: string;
  bitcoinWarmWalletAddress?: string;
  cronDbDebug?: YesNo;
  quandlApiKey?: string;
  cronDirroot?: string;
  backstageDbDebug?: YesNo;
  backstageDirroot?: string;
  emailNotifyFiatWithdrawals?: YesNo;
  contactEmail?: string;
  cloudflareApiKey?: string;
  googleRecaptchApiKey?: string;
  cloudflareBlacklist?: YesNo;
  cloudflareEmail?: string;
  googleRecaptchApiSecret?: string;
  cloudflareBlacklistAttempts?: number;
  cloudflareBlacklistTimeframe?: number;
  cryptoCapitalPk?: string;
  depositFiatDesc?: string;
  emailNotifyFiatFailed?: YesNo;
  tradingStatus?: string;
  withdrawalsStatus?: string;
}

export class AppConfiguration implements IAppConfiguration {
  constructor(
    public id?: number,
    public defaultTimezone?: string,
    public ordersUnderMarketPercent?: string,
    public ordersMinUsd?: string,
    public bitcoinSendingFee?: string,
    public frontendBaseurl?: string,
    public frontendDirroot?: string,
    public fiatWithdrawFee?: string,
    public apiDbDebug?: YesNo,
    public apiDirroot?: string,
    public supportEmail?: string,
    public emailSmtpHost?: string,
    public emailSmtpPort?: string,
    public emailSmtpSecurity?: string,
    public emailSmtpUsername?: string,
    public emailSmtpPassword?: string,
    public emailSmtpSendFrom?: string,
    public bitcoinUsername?: string,
    public bitcoinAccountname?: string,
    public bitcoinPassphrase?: string,
    public bitcoinHost?: string,
    public bitcoinPort?: string,
    public bitcoinProtocol?: string,
    public authyApiKey?: string,
    public helpdeskKey?: string,
    public exchangeName?: string,
    public mcryptKey?: string,
    public currencyConversionFee?: string,
    public crossCurrencyTrades?: YesNo,
    public btcCurrencyId?: string,
    public depositBitcoinDesc?: string,
    public defaultFeeScheduleId?: string,
    public historyBuyId?: string,
    public historyDepositId?: string,
    public historyLoginId?: string,
    public historySellId?: string,
    public historyWithdrawId?: string,
    public orderTypeAsk?: string,
    public requestAwaitingId?: string,
    public requestCancelledId?: string,
    public requestCompletedId?: string,
    public orderTypeBid?: string,
    public requestDepositId?: string,
    public requestPendingId?: string,
    public requestWithdrawalId?: string,
    public transactionsBuyId?: string,
    public transactionsSellId?: string,
    public withdrawFiatDesc?: string,
    public withdrawBtcDesc?: string,
    public formEmailFrom?: string,
    public emailNotifyNewUsers?: YesNo,
    public passRegex?: string,
    public passMinChars?: string,
    public authDbDebug?: YesNo,
    public bitcoinReserveMin?: string,
    public bitcoinReserveRatio?: string,
    public bitcoinWarmWalletAddress?: string,
    public cronDbDebug?: YesNo,
    public quandlApiKey?: string,
    public cronDirroot?: string,
    public backstageDbDebug?: YesNo,
    public backstageDirroot?: string,
    public emailNotifyFiatWithdrawals?: YesNo,
    public contactEmail?: string,
    public cloudflareApiKey?: string,
    public googleRecaptchApiKey?: string,
    public cloudflareBlacklist?: YesNo,
    public cloudflareEmail?: string,
    public googleRecaptchApiSecret?: string,
    public cloudflareBlacklistAttempts?: number,
    public cloudflareBlacklistTimeframe?: number,
    public cryptoCapitalPk?: string,
    public depositFiatDesc?: string,
    public emailNotifyFiatFailed?: YesNo,
    public tradingStatus?: string,
    public withdrawalsStatus?: string
  ) {}
}

export function getAppConfigurationIdentifier(appConfiguration: IAppConfiguration): number | undefined {
  return appConfiguration.id;
}
