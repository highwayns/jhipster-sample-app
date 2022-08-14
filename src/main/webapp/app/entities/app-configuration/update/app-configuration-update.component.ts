import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAppConfiguration, AppConfiguration } from '../app-configuration.model';
import { AppConfigurationService } from '../service/app-configuration.service';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

@Component({
  selector: 'jhi-app-configuration-update',
  templateUrl: './app-configuration-update.component.html',
})
export class AppConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  yesNoValues = Object.keys(YesNo);

  editForm = this.fb.group({
    id: [],
    defaultTimezone: [null, [Validators.required, Validators.maxLength(255)]],
    ordersUnderMarketPercent: [null, [Validators.required, Validators.maxLength(255)]],
    ordersMinUsd: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinSendingFee: [null, [Validators.required, Validators.maxLength(255)]],
    frontendBaseurl: [null, [Validators.required, Validators.maxLength(255)]],
    frontendDirroot: [null, [Validators.required, Validators.maxLength(255)]],
    fiatWithdrawFee: [null, [Validators.required, Validators.maxLength(255)]],
    apiDbDebug: [null, [Validators.required]],
    apiDirroot: [null, [Validators.required, Validators.maxLength(255)]],
    supportEmail: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpHost: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpPort: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpSecurity: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpUsername: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpPassword: [null, [Validators.required, Validators.maxLength(255)]],
    emailSmtpSendFrom: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinUsername: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinAccountname: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinPassphrase: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinHost: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinPort: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinProtocol: [null, [Validators.required, Validators.maxLength(255)]],
    authyApiKey: [null, [Validators.required, Validators.maxLength(255)]],
    helpdeskKey: [null, [Validators.required, Validators.maxLength(255)]],
    exchangeName: [null, [Validators.required, Validators.maxLength(255)]],
    mcryptKey: [null, [Validators.required, Validators.maxLength(255)]],
    currencyConversionFee: [null, [Validators.required, Validators.maxLength(255)]],
    crossCurrencyTrades: [null, [Validators.required]],
    btcCurrencyId: [null, [Validators.required, Validators.maxLength(255)]],
    depositBitcoinDesc: [null, [Validators.required, Validators.maxLength(255)]],
    defaultFeeScheduleId: [null, [Validators.required, Validators.maxLength(255)]],
    historyBuyId: [null, [Validators.required, Validators.maxLength(255)]],
    historyDepositId: [null, [Validators.required, Validators.maxLength(255)]],
    historyLoginId: [null, [Validators.required, Validators.maxLength(255)]],
    historySellId: [null, [Validators.required, Validators.maxLength(255)]],
    historyWithdrawId: [null, [Validators.required, Validators.maxLength(255)]],
    orderTypeAsk: [null, [Validators.required, Validators.maxLength(255)]],
    requestAwaitingId: [null, [Validators.required, Validators.maxLength(255)]],
    requestCancelledId: [null, [Validators.required, Validators.maxLength(255)]],
    requestCompletedId: [null, [Validators.required, Validators.maxLength(255)]],
    orderTypeBid: [null, [Validators.required, Validators.maxLength(255)]],
    requestDepositId: [null, [Validators.required, Validators.maxLength(255)]],
    requestPendingId: [null, [Validators.required, Validators.maxLength(255)]],
    requestWithdrawalId: [null, [Validators.required, Validators.maxLength(255)]],
    transactionsBuyId: [null, [Validators.required, Validators.maxLength(255)]],
    transactionsSellId: [null, [Validators.required, Validators.maxLength(255)]],
    withdrawFiatDesc: [null, [Validators.required, Validators.maxLength(255)]],
    withdrawBtcDesc: [null, [Validators.required, Validators.maxLength(255)]],
    formEmailFrom: [null, [Validators.required, Validators.maxLength(255)]],
    emailNotifyNewUsers: [null, [Validators.required]],
    passRegex: [null, [Validators.required, Validators.maxLength(255)]],
    passMinChars: [null, [Validators.required, Validators.maxLength(255)]],
    authDbDebug: [null, [Validators.required]],
    bitcoinReserveMin: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinReserveRatio: [null, [Validators.required, Validators.maxLength(255)]],
    bitcoinWarmWalletAddress: [null, [Validators.required, Validators.maxLength(255)]],
    cronDbDebug: [null, [Validators.required]],
    quandlApiKey: [null, [Validators.required, Validators.maxLength(255)]],
    cronDirroot: [null, [Validators.required, Validators.maxLength(255)]],
    backstageDbDebug: [null, [Validators.required]],
    backstageDirroot: [null, [Validators.required, Validators.maxLength(255)]],
    emailNotifyFiatWithdrawals: [null, [Validators.required]],
    contactEmail: [null, [Validators.required, Validators.maxLength(255)]],
    cloudflareApiKey: [null, [Validators.required, Validators.maxLength(255)]],
    googleRecaptchApiKey: [null, [Validators.required, Validators.maxLength(255)]],
    cloudflareBlacklist: [null, [Validators.required]],
    cloudflareEmail: [null, [Validators.required, Validators.maxLength(255)]],
    googleRecaptchApiSecret: [null, [Validators.required, Validators.maxLength(255)]],
    cloudflareBlacklistAttempts: [null, [Validators.required]],
    cloudflareBlacklistTimeframe: [null, [Validators.required]],
    cryptoCapitalPk: [null, [Validators.required, Validators.maxLength(255)]],
    depositFiatDesc: [null, [Validators.required, Validators.maxLength(255)]],
    emailNotifyFiatFailed: [null, [Validators.required]],
    tradingStatus: [null, [Validators.required, Validators.maxLength(255)]],
    withdrawalsStatus: [null, [Validators.required, Validators.maxLength(255)]],
  });

  constructor(
    protected appConfigurationService: AppConfigurationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appConfiguration }) => {
      this.updateForm(appConfiguration);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appConfiguration = this.createFromForm();
    if (appConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.appConfigurationService.update(appConfiguration));
    } else {
      this.subscribeToSaveResponse(this.appConfigurationService.create(appConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppConfiguration>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(appConfiguration: IAppConfiguration): void {
    this.editForm.patchValue({
      id: appConfiguration.id,
      defaultTimezone: appConfiguration.defaultTimezone,
      ordersUnderMarketPercent: appConfiguration.ordersUnderMarketPercent,
      ordersMinUsd: appConfiguration.ordersMinUsd,
      bitcoinSendingFee: appConfiguration.bitcoinSendingFee,
      frontendBaseurl: appConfiguration.frontendBaseurl,
      frontendDirroot: appConfiguration.frontendDirroot,
      fiatWithdrawFee: appConfiguration.fiatWithdrawFee,
      apiDbDebug: appConfiguration.apiDbDebug,
      apiDirroot: appConfiguration.apiDirroot,
      supportEmail: appConfiguration.supportEmail,
      emailSmtpHost: appConfiguration.emailSmtpHost,
      emailSmtpPort: appConfiguration.emailSmtpPort,
      emailSmtpSecurity: appConfiguration.emailSmtpSecurity,
      emailSmtpUsername: appConfiguration.emailSmtpUsername,
      emailSmtpPassword: appConfiguration.emailSmtpPassword,
      emailSmtpSendFrom: appConfiguration.emailSmtpSendFrom,
      bitcoinUsername: appConfiguration.bitcoinUsername,
      bitcoinAccountname: appConfiguration.bitcoinAccountname,
      bitcoinPassphrase: appConfiguration.bitcoinPassphrase,
      bitcoinHost: appConfiguration.bitcoinHost,
      bitcoinPort: appConfiguration.bitcoinPort,
      bitcoinProtocol: appConfiguration.bitcoinProtocol,
      authyApiKey: appConfiguration.authyApiKey,
      helpdeskKey: appConfiguration.helpdeskKey,
      exchangeName: appConfiguration.exchangeName,
      mcryptKey: appConfiguration.mcryptKey,
      currencyConversionFee: appConfiguration.currencyConversionFee,
      crossCurrencyTrades: appConfiguration.crossCurrencyTrades,
      btcCurrencyId: appConfiguration.btcCurrencyId,
      depositBitcoinDesc: appConfiguration.depositBitcoinDesc,
      defaultFeeScheduleId: appConfiguration.defaultFeeScheduleId,
      historyBuyId: appConfiguration.historyBuyId,
      historyDepositId: appConfiguration.historyDepositId,
      historyLoginId: appConfiguration.historyLoginId,
      historySellId: appConfiguration.historySellId,
      historyWithdrawId: appConfiguration.historyWithdrawId,
      orderTypeAsk: appConfiguration.orderTypeAsk,
      requestAwaitingId: appConfiguration.requestAwaitingId,
      requestCancelledId: appConfiguration.requestCancelledId,
      requestCompletedId: appConfiguration.requestCompletedId,
      orderTypeBid: appConfiguration.orderTypeBid,
      requestDepositId: appConfiguration.requestDepositId,
      requestPendingId: appConfiguration.requestPendingId,
      requestWithdrawalId: appConfiguration.requestWithdrawalId,
      transactionsBuyId: appConfiguration.transactionsBuyId,
      transactionsSellId: appConfiguration.transactionsSellId,
      withdrawFiatDesc: appConfiguration.withdrawFiatDesc,
      withdrawBtcDesc: appConfiguration.withdrawBtcDesc,
      formEmailFrom: appConfiguration.formEmailFrom,
      emailNotifyNewUsers: appConfiguration.emailNotifyNewUsers,
      passRegex: appConfiguration.passRegex,
      passMinChars: appConfiguration.passMinChars,
      authDbDebug: appConfiguration.authDbDebug,
      bitcoinReserveMin: appConfiguration.bitcoinReserveMin,
      bitcoinReserveRatio: appConfiguration.bitcoinReserveRatio,
      bitcoinWarmWalletAddress: appConfiguration.bitcoinWarmWalletAddress,
      cronDbDebug: appConfiguration.cronDbDebug,
      quandlApiKey: appConfiguration.quandlApiKey,
      cronDirroot: appConfiguration.cronDirroot,
      backstageDbDebug: appConfiguration.backstageDbDebug,
      backstageDirroot: appConfiguration.backstageDirroot,
      emailNotifyFiatWithdrawals: appConfiguration.emailNotifyFiatWithdrawals,
      contactEmail: appConfiguration.contactEmail,
      cloudflareApiKey: appConfiguration.cloudflareApiKey,
      googleRecaptchApiKey: appConfiguration.googleRecaptchApiKey,
      cloudflareBlacklist: appConfiguration.cloudflareBlacklist,
      cloudflareEmail: appConfiguration.cloudflareEmail,
      googleRecaptchApiSecret: appConfiguration.googleRecaptchApiSecret,
      cloudflareBlacklistAttempts: appConfiguration.cloudflareBlacklistAttempts,
      cloudflareBlacklistTimeframe: appConfiguration.cloudflareBlacklistTimeframe,
      cryptoCapitalPk: appConfiguration.cryptoCapitalPk,
      depositFiatDesc: appConfiguration.depositFiatDesc,
      emailNotifyFiatFailed: appConfiguration.emailNotifyFiatFailed,
      tradingStatus: appConfiguration.tradingStatus,
      withdrawalsStatus: appConfiguration.withdrawalsStatus,
    });
  }

  protected createFromForm(): IAppConfiguration {
    return {
      ...new AppConfiguration(),
      id: this.editForm.get(['id'])!.value,
      defaultTimezone: this.editForm.get(['defaultTimezone'])!.value,
      ordersUnderMarketPercent: this.editForm.get(['ordersUnderMarketPercent'])!.value,
      ordersMinUsd: this.editForm.get(['ordersMinUsd'])!.value,
      bitcoinSendingFee: this.editForm.get(['bitcoinSendingFee'])!.value,
      frontendBaseurl: this.editForm.get(['frontendBaseurl'])!.value,
      frontendDirroot: this.editForm.get(['frontendDirroot'])!.value,
      fiatWithdrawFee: this.editForm.get(['fiatWithdrawFee'])!.value,
      apiDbDebug: this.editForm.get(['apiDbDebug'])!.value,
      apiDirroot: this.editForm.get(['apiDirroot'])!.value,
      supportEmail: this.editForm.get(['supportEmail'])!.value,
      emailSmtpHost: this.editForm.get(['emailSmtpHost'])!.value,
      emailSmtpPort: this.editForm.get(['emailSmtpPort'])!.value,
      emailSmtpSecurity: this.editForm.get(['emailSmtpSecurity'])!.value,
      emailSmtpUsername: this.editForm.get(['emailSmtpUsername'])!.value,
      emailSmtpPassword: this.editForm.get(['emailSmtpPassword'])!.value,
      emailSmtpSendFrom: this.editForm.get(['emailSmtpSendFrom'])!.value,
      bitcoinUsername: this.editForm.get(['bitcoinUsername'])!.value,
      bitcoinAccountname: this.editForm.get(['bitcoinAccountname'])!.value,
      bitcoinPassphrase: this.editForm.get(['bitcoinPassphrase'])!.value,
      bitcoinHost: this.editForm.get(['bitcoinHost'])!.value,
      bitcoinPort: this.editForm.get(['bitcoinPort'])!.value,
      bitcoinProtocol: this.editForm.get(['bitcoinProtocol'])!.value,
      authyApiKey: this.editForm.get(['authyApiKey'])!.value,
      helpdeskKey: this.editForm.get(['helpdeskKey'])!.value,
      exchangeName: this.editForm.get(['exchangeName'])!.value,
      mcryptKey: this.editForm.get(['mcryptKey'])!.value,
      currencyConversionFee: this.editForm.get(['currencyConversionFee'])!.value,
      crossCurrencyTrades: this.editForm.get(['crossCurrencyTrades'])!.value,
      btcCurrencyId: this.editForm.get(['btcCurrencyId'])!.value,
      depositBitcoinDesc: this.editForm.get(['depositBitcoinDesc'])!.value,
      defaultFeeScheduleId: this.editForm.get(['defaultFeeScheduleId'])!.value,
      historyBuyId: this.editForm.get(['historyBuyId'])!.value,
      historyDepositId: this.editForm.get(['historyDepositId'])!.value,
      historyLoginId: this.editForm.get(['historyLoginId'])!.value,
      historySellId: this.editForm.get(['historySellId'])!.value,
      historyWithdrawId: this.editForm.get(['historyWithdrawId'])!.value,
      orderTypeAsk: this.editForm.get(['orderTypeAsk'])!.value,
      requestAwaitingId: this.editForm.get(['requestAwaitingId'])!.value,
      requestCancelledId: this.editForm.get(['requestCancelledId'])!.value,
      requestCompletedId: this.editForm.get(['requestCompletedId'])!.value,
      orderTypeBid: this.editForm.get(['orderTypeBid'])!.value,
      requestDepositId: this.editForm.get(['requestDepositId'])!.value,
      requestPendingId: this.editForm.get(['requestPendingId'])!.value,
      requestWithdrawalId: this.editForm.get(['requestWithdrawalId'])!.value,
      transactionsBuyId: this.editForm.get(['transactionsBuyId'])!.value,
      transactionsSellId: this.editForm.get(['transactionsSellId'])!.value,
      withdrawFiatDesc: this.editForm.get(['withdrawFiatDesc'])!.value,
      withdrawBtcDesc: this.editForm.get(['withdrawBtcDesc'])!.value,
      formEmailFrom: this.editForm.get(['formEmailFrom'])!.value,
      emailNotifyNewUsers: this.editForm.get(['emailNotifyNewUsers'])!.value,
      passRegex: this.editForm.get(['passRegex'])!.value,
      passMinChars: this.editForm.get(['passMinChars'])!.value,
      authDbDebug: this.editForm.get(['authDbDebug'])!.value,
      bitcoinReserveMin: this.editForm.get(['bitcoinReserveMin'])!.value,
      bitcoinReserveRatio: this.editForm.get(['bitcoinReserveRatio'])!.value,
      bitcoinWarmWalletAddress: this.editForm.get(['bitcoinWarmWalletAddress'])!.value,
      cronDbDebug: this.editForm.get(['cronDbDebug'])!.value,
      quandlApiKey: this.editForm.get(['quandlApiKey'])!.value,
      cronDirroot: this.editForm.get(['cronDirroot'])!.value,
      backstageDbDebug: this.editForm.get(['backstageDbDebug'])!.value,
      backstageDirroot: this.editForm.get(['backstageDirroot'])!.value,
      emailNotifyFiatWithdrawals: this.editForm.get(['emailNotifyFiatWithdrawals'])!.value,
      contactEmail: this.editForm.get(['contactEmail'])!.value,
      cloudflareApiKey: this.editForm.get(['cloudflareApiKey'])!.value,
      googleRecaptchApiKey: this.editForm.get(['googleRecaptchApiKey'])!.value,
      cloudflareBlacklist: this.editForm.get(['cloudflareBlacklist'])!.value,
      cloudflareEmail: this.editForm.get(['cloudflareEmail'])!.value,
      googleRecaptchApiSecret: this.editForm.get(['googleRecaptchApiSecret'])!.value,
      cloudflareBlacklistAttempts: this.editForm.get(['cloudflareBlacklistAttempts'])!.value,
      cloudflareBlacklistTimeframe: this.editForm.get(['cloudflareBlacklistTimeframe'])!.value,
      cryptoCapitalPk: this.editForm.get(['cryptoCapitalPk'])!.value,
      depositFiatDesc: this.editForm.get(['depositFiatDesc'])!.value,
      emailNotifyFiatFailed: this.editForm.get(['emailNotifyFiatFailed'])!.value,
      tradingStatus: this.editForm.get(['tradingStatus'])!.value,
      withdrawalsStatus: this.editForm.get(['withdrawalsStatus'])!.value,
    };
  }
}
