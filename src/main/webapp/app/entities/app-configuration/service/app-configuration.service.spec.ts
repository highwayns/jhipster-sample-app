import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { IAppConfiguration, AppConfiguration } from '../app-configuration.model';

import { AppConfigurationService } from './app-configuration.service';

describe('AppConfiguration Service', () => {
  let service: AppConfigurationService;
  let httpMock: HttpTestingController;
  let elemDefault: IAppConfiguration;
  let expectedResult: IAppConfiguration | IAppConfiguration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AppConfigurationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      defaultTimezone: 'AAAAAAA',
      ordersUnderMarketPercent: 'AAAAAAA',
      ordersMinUsd: 'AAAAAAA',
      bitcoinSendingFee: 'AAAAAAA',
      frontendBaseurl: 'AAAAAAA',
      frontendDirroot: 'AAAAAAA',
      fiatWithdrawFee: 'AAAAAAA',
      apiDbDebug: YesNo.Y,
      apiDirroot: 'AAAAAAA',
      supportEmail: 'AAAAAAA',
      emailSmtpHost: 'AAAAAAA',
      emailSmtpPort: 'AAAAAAA',
      emailSmtpSecurity: 'AAAAAAA',
      emailSmtpUsername: 'AAAAAAA',
      emailSmtpPassword: 'AAAAAAA',
      emailSmtpSendFrom: 'AAAAAAA',
      bitcoinUsername: 'AAAAAAA',
      bitcoinAccountname: 'AAAAAAA',
      bitcoinPassphrase: 'AAAAAAA',
      bitcoinHost: 'AAAAAAA',
      bitcoinPort: 'AAAAAAA',
      bitcoinProtocol: 'AAAAAAA',
      authyApiKey: 'AAAAAAA',
      helpdeskKey: 'AAAAAAA',
      exchangeName: 'AAAAAAA',
      mcryptKey: 'AAAAAAA',
      currencyConversionFee: 'AAAAAAA',
      crossCurrencyTrades: YesNo.Y,
      btcCurrencyId: 'AAAAAAA',
      depositBitcoinDesc: 'AAAAAAA',
      defaultFeeScheduleId: 'AAAAAAA',
      historyBuyId: 'AAAAAAA',
      historyDepositId: 'AAAAAAA',
      historyLoginId: 'AAAAAAA',
      historySellId: 'AAAAAAA',
      historyWithdrawId: 'AAAAAAA',
      orderTypeAsk: 'AAAAAAA',
      requestAwaitingId: 'AAAAAAA',
      requestCancelledId: 'AAAAAAA',
      requestCompletedId: 'AAAAAAA',
      orderTypeBid: 'AAAAAAA',
      requestDepositId: 'AAAAAAA',
      requestPendingId: 'AAAAAAA',
      requestWithdrawalId: 'AAAAAAA',
      transactionsBuyId: 'AAAAAAA',
      transactionsSellId: 'AAAAAAA',
      withdrawFiatDesc: 'AAAAAAA',
      withdrawBtcDesc: 'AAAAAAA',
      formEmailFrom: 'AAAAAAA',
      emailNotifyNewUsers: YesNo.Y,
      passRegex: 'AAAAAAA',
      passMinChars: 'AAAAAAA',
      authDbDebug: YesNo.Y,
      bitcoinReserveMin: 'AAAAAAA',
      bitcoinReserveRatio: 'AAAAAAA',
      bitcoinWarmWalletAddress: 'AAAAAAA',
      cronDbDebug: YesNo.Y,
      quandlApiKey: 'AAAAAAA',
      cronDirroot: 'AAAAAAA',
      backstageDbDebug: YesNo.Y,
      backstageDirroot: 'AAAAAAA',
      emailNotifyFiatWithdrawals: YesNo.Y,
      contactEmail: 'AAAAAAA',
      cloudflareApiKey: 'AAAAAAA',
      googleRecaptchApiKey: 'AAAAAAA',
      cloudflareBlacklist: YesNo.Y,
      cloudflareEmail: 'AAAAAAA',
      googleRecaptchApiSecret: 'AAAAAAA',
      cloudflareBlacklistAttempts: 0,
      cloudflareBlacklistTimeframe: 0,
      cryptoCapitalPk: 'AAAAAAA',
      depositFiatDesc: 'AAAAAAA',
      emailNotifyFiatFailed: YesNo.Y,
      tradingStatus: 'AAAAAAA',
      withdrawalsStatus: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a AppConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AppConfiguration()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AppConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          defaultTimezone: 'BBBBBB',
          ordersUnderMarketPercent: 'BBBBBB',
          ordersMinUsd: 'BBBBBB',
          bitcoinSendingFee: 'BBBBBB',
          frontendBaseurl: 'BBBBBB',
          frontendDirroot: 'BBBBBB',
          fiatWithdrawFee: 'BBBBBB',
          apiDbDebug: 'BBBBBB',
          apiDirroot: 'BBBBBB',
          supportEmail: 'BBBBBB',
          emailSmtpHost: 'BBBBBB',
          emailSmtpPort: 'BBBBBB',
          emailSmtpSecurity: 'BBBBBB',
          emailSmtpUsername: 'BBBBBB',
          emailSmtpPassword: 'BBBBBB',
          emailSmtpSendFrom: 'BBBBBB',
          bitcoinUsername: 'BBBBBB',
          bitcoinAccountname: 'BBBBBB',
          bitcoinPassphrase: 'BBBBBB',
          bitcoinHost: 'BBBBBB',
          bitcoinPort: 'BBBBBB',
          bitcoinProtocol: 'BBBBBB',
          authyApiKey: 'BBBBBB',
          helpdeskKey: 'BBBBBB',
          exchangeName: 'BBBBBB',
          mcryptKey: 'BBBBBB',
          currencyConversionFee: 'BBBBBB',
          crossCurrencyTrades: 'BBBBBB',
          btcCurrencyId: 'BBBBBB',
          depositBitcoinDesc: 'BBBBBB',
          defaultFeeScheduleId: 'BBBBBB',
          historyBuyId: 'BBBBBB',
          historyDepositId: 'BBBBBB',
          historyLoginId: 'BBBBBB',
          historySellId: 'BBBBBB',
          historyWithdrawId: 'BBBBBB',
          orderTypeAsk: 'BBBBBB',
          requestAwaitingId: 'BBBBBB',
          requestCancelledId: 'BBBBBB',
          requestCompletedId: 'BBBBBB',
          orderTypeBid: 'BBBBBB',
          requestDepositId: 'BBBBBB',
          requestPendingId: 'BBBBBB',
          requestWithdrawalId: 'BBBBBB',
          transactionsBuyId: 'BBBBBB',
          transactionsSellId: 'BBBBBB',
          withdrawFiatDesc: 'BBBBBB',
          withdrawBtcDesc: 'BBBBBB',
          formEmailFrom: 'BBBBBB',
          emailNotifyNewUsers: 'BBBBBB',
          passRegex: 'BBBBBB',
          passMinChars: 'BBBBBB',
          authDbDebug: 'BBBBBB',
          bitcoinReserveMin: 'BBBBBB',
          bitcoinReserveRatio: 'BBBBBB',
          bitcoinWarmWalletAddress: 'BBBBBB',
          cronDbDebug: 'BBBBBB',
          quandlApiKey: 'BBBBBB',
          cronDirroot: 'BBBBBB',
          backstageDbDebug: 'BBBBBB',
          backstageDirroot: 'BBBBBB',
          emailNotifyFiatWithdrawals: 'BBBBBB',
          contactEmail: 'BBBBBB',
          cloudflareApiKey: 'BBBBBB',
          googleRecaptchApiKey: 'BBBBBB',
          cloudflareBlacklist: 'BBBBBB',
          cloudflareEmail: 'BBBBBB',
          googleRecaptchApiSecret: 'BBBBBB',
          cloudflareBlacklistAttempts: 1,
          cloudflareBlacklistTimeframe: 1,
          cryptoCapitalPk: 'BBBBBB',
          depositFiatDesc: 'BBBBBB',
          emailNotifyFiatFailed: 'BBBBBB',
          tradingStatus: 'BBBBBB',
          withdrawalsStatus: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AppConfiguration', () => {
      const patchObject = Object.assign(
        {
          defaultTimezone: 'BBBBBB',
          ordersUnderMarketPercent: 'BBBBBB',
          ordersMinUsd: 'BBBBBB',
          frontendBaseurl: 'BBBBBB',
          frontendDirroot: 'BBBBBB',
          fiatWithdrawFee: 'BBBBBB',
          apiDbDebug: 'BBBBBB',
          apiDirroot: 'BBBBBB',
          supportEmail: 'BBBBBB',
          emailSmtpHost: 'BBBBBB',
          bitcoinPassphrase: 'BBBBBB',
          helpdeskKey: 'BBBBBB',
          currencyConversionFee: 'BBBBBB',
          crossCurrencyTrades: 'BBBBBB',
          depositBitcoinDesc: 'BBBBBB',
          historyBuyId: 'BBBBBB',
          historyDepositId: 'BBBBBB',
          historyLoginId: 'BBBBBB',
          orderTypeAsk: 'BBBBBB',
          requestPendingId: 'BBBBBB',
          transactionsSellId: 'BBBBBB',
          withdrawFiatDesc: 'BBBBBB',
          formEmailFrom: 'BBBBBB',
          emailNotifyNewUsers: 'BBBBBB',
          passRegex: 'BBBBBB',
          passMinChars: 'BBBBBB',
          authDbDebug: 'BBBBBB',
          bitcoinReserveMin: 'BBBBBB',
          bitcoinReserveRatio: 'BBBBBB',
          bitcoinWarmWalletAddress: 'BBBBBB',
          cronDbDebug: 'BBBBBB',
          quandlApiKey: 'BBBBBB',
          cronDirroot: 'BBBBBB',
          backstageDbDebug: 'BBBBBB',
          backstageDirroot: 'BBBBBB',
          emailNotifyFiatWithdrawals: 'BBBBBB',
          cloudflareApiKey: 'BBBBBB',
          cloudflareBlacklistAttempts: 1,
          cloudflareBlacklistTimeframe: 1,
          cryptoCapitalPk: 'BBBBBB',
          depositFiatDesc: 'BBBBBB',
          emailNotifyFiatFailed: 'BBBBBB',
          tradingStatus: 'BBBBBB',
        },
        new AppConfiguration()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AppConfiguration', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          defaultTimezone: 'BBBBBB',
          ordersUnderMarketPercent: 'BBBBBB',
          ordersMinUsd: 'BBBBBB',
          bitcoinSendingFee: 'BBBBBB',
          frontendBaseurl: 'BBBBBB',
          frontendDirroot: 'BBBBBB',
          fiatWithdrawFee: 'BBBBBB',
          apiDbDebug: 'BBBBBB',
          apiDirroot: 'BBBBBB',
          supportEmail: 'BBBBBB',
          emailSmtpHost: 'BBBBBB',
          emailSmtpPort: 'BBBBBB',
          emailSmtpSecurity: 'BBBBBB',
          emailSmtpUsername: 'BBBBBB',
          emailSmtpPassword: 'BBBBBB',
          emailSmtpSendFrom: 'BBBBBB',
          bitcoinUsername: 'BBBBBB',
          bitcoinAccountname: 'BBBBBB',
          bitcoinPassphrase: 'BBBBBB',
          bitcoinHost: 'BBBBBB',
          bitcoinPort: 'BBBBBB',
          bitcoinProtocol: 'BBBBBB',
          authyApiKey: 'BBBBBB',
          helpdeskKey: 'BBBBBB',
          exchangeName: 'BBBBBB',
          mcryptKey: 'BBBBBB',
          currencyConversionFee: 'BBBBBB',
          crossCurrencyTrades: 'BBBBBB',
          btcCurrencyId: 'BBBBBB',
          depositBitcoinDesc: 'BBBBBB',
          defaultFeeScheduleId: 'BBBBBB',
          historyBuyId: 'BBBBBB',
          historyDepositId: 'BBBBBB',
          historyLoginId: 'BBBBBB',
          historySellId: 'BBBBBB',
          historyWithdrawId: 'BBBBBB',
          orderTypeAsk: 'BBBBBB',
          requestAwaitingId: 'BBBBBB',
          requestCancelledId: 'BBBBBB',
          requestCompletedId: 'BBBBBB',
          orderTypeBid: 'BBBBBB',
          requestDepositId: 'BBBBBB',
          requestPendingId: 'BBBBBB',
          requestWithdrawalId: 'BBBBBB',
          transactionsBuyId: 'BBBBBB',
          transactionsSellId: 'BBBBBB',
          withdrawFiatDesc: 'BBBBBB',
          withdrawBtcDesc: 'BBBBBB',
          formEmailFrom: 'BBBBBB',
          emailNotifyNewUsers: 'BBBBBB',
          passRegex: 'BBBBBB',
          passMinChars: 'BBBBBB',
          authDbDebug: 'BBBBBB',
          bitcoinReserveMin: 'BBBBBB',
          bitcoinReserveRatio: 'BBBBBB',
          bitcoinWarmWalletAddress: 'BBBBBB',
          cronDbDebug: 'BBBBBB',
          quandlApiKey: 'BBBBBB',
          cronDirroot: 'BBBBBB',
          backstageDbDebug: 'BBBBBB',
          backstageDirroot: 'BBBBBB',
          emailNotifyFiatWithdrawals: 'BBBBBB',
          contactEmail: 'BBBBBB',
          cloudflareApiKey: 'BBBBBB',
          googleRecaptchApiKey: 'BBBBBB',
          cloudflareBlacklist: 'BBBBBB',
          cloudflareEmail: 'BBBBBB',
          googleRecaptchApiSecret: 'BBBBBB',
          cloudflareBlacklistAttempts: 1,
          cloudflareBlacklistTimeframe: 1,
          cryptoCapitalPk: 'BBBBBB',
          depositFiatDesc: 'BBBBBB',
          emailNotifyFiatFailed: 'BBBBBB',
          tradingStatus: 'BBBBBB',
          withdrawalsStatus: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a AppConfiguration', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAppConfigurationToCollectionIfMissing', () => {
      it('should add a AppConfiguration to an empty array', () => {
        const appConfiguration: IAppConfiguration = { id: 123 };
        expectedResult = service.addAppConfigurationToCollectionIfMissing([], appConfiguration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appConfiguration);
      });

      it('should not add a AppConfiguration to an array that contains it', () => {
        const appConfiguration: IAppConfiguration = { id: 123 };
        const appConfigurationCollection: IAppConfiguration[] = [
          {
            ...appConfiguration,
          },
          { id: 456 },
        ];
        expectedResult = service.addAppConfigurationToCollectionIfMissing(appConfigurationCollection, appConfiguration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AppConfiguration to an array that doesn't contain it", () => {
        const appConfiguration: IAppConfiguration = { id: 123 };
        const appConfigurationCollection: IAppConfiguration[] = [{ id: 456 }];
        expectedResult = service.addAppConfigurationToCollectionIfMissing(appConfigurationCollection, appConfiguration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appConfiguration);
      });

      it('should add only unique AppConfiguration to an array', () => {
        const appConfigurationArray: IAppConfiguration[] = [{ id: 123 }, { id: 456 }, { id: 20398 }];
        const appConfigurationCollection: IAppConfiguration[] = [{ id: 123 }];
        expectedResult = service.addAppConfigurationToCollectionIfMissing(appConfigurationCollection, ...appConfigurationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const appConfiguration: IAppConfiguration = { id: 123 };
        const appConfiguration2: IAppConfiguration = { id: 456 };
        expectedResult = service.addAppConfigurationToCollectionIfMissing([], appConfiguration, appConfiguration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appConfiguration);
        expect(expectedResult).toContain(appConfiguration2);
      });

      it('should accept null and undefined values', () => {
        const appConfiguration: IAppConfiguration = { id: 123 };
        expectedResult = service.addAppConfigurationToCollectionIfMissing([], null, appConfiguration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appConfiguration);
      });

      it('should return initial array if no AppConfiguration is added', () => {
        const appConfigurationCollection: IAppConfiguration[] = [{ id: 123 }];
        expectedResult = service.addAppConfigurationToCollectionIfMissing(appConfigurationCollection, undefined, null);
        expect(expectedResult).toEqual(appConfigurationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
