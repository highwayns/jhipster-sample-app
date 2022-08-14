import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('AppConfiguration e2e test', () => {
  const appConfigurationPageUrl = '/app-configuration';
  const appConfigurationPageUrlPattern = new RegExp('/app-configuration(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const appConfigurationSample = {
    defaultTimezone: 'redundant Product Seamless',
    ordersUnderMarketPercent: 'Idaho',
    ordersMinUsd: 'Handcrafted',
    bitcoinSendingFee: 'fuchsia Virginia',
    frontendBaseurl: 'platforms navigating',
    frontendDirroot: 'driver Innovative',
    fiatWithdrawFee: 'communities',
    apiDbDebug: 'N',
    apiDirroot: 'back Centers',
    supportEmail: 'Motorway ROI',
    emailSmtpHost: 'protocol',
    emailSmtpPort: 'cohesive',
    emailSmtpSecurity: 'Checking',
    emailSmtpUsername: 'architectures',
    emailSmtpPassword: 'deposit',
    emailSmtpSendFrom: 'Chips',
    bitcoinUsername: 'Tools Burkina',
    bitcoinAccountname: 'hack Oregon 4th',
    bitcoinPassphrase: 'calculate Arizona',
    bitcoinHost: 'iterate Borders',
    bitcoinPort: 'hacking bypass Dynamic',
    bitcoinProtocol: 'magenta lime',
    authyApiKey: 'bus',
    helpdeskKey: 'Monitored Bedfordshire',
    exchangeName: 'Maryland',
    mcryptKey: 'Macao Nevada',
    currencyConversionFee: 'deposit architecture payment',
    crossCurrencyTrades: 'N',
    btcCurrencyId: 'Towels copying',
    depositBitcoinDesc: 'Quality',
    defaultFeeScheduleId: 'Generic Profit-focused red',
    historyBuyId: 'Internal Bedfordshire Fresh',
    historyDepositId: 'HDD',
    historyLoginId: 'object-oriented',
    historySellId: 'Stream Loan',
    historyWithdrawId: 'Identity',
    orderTypeAsk: 'payment Unbranded',
    requestAwaitingId: 'Liaison',
    requestCancelledId: 'Burundi array',
    requestCompletedId: 'capacitor Berkshire',
    orderTypeBid: 'Soft synthesize',
    requestDepositId: 'compressing Cotton South',
    requestPendingId: 'Buckinghamshire',
    requestWithdrawalId: 'Sleek Ergonomic Missouri',
    transactionsBuyId: 'Optimization National seamless',
    transactionsSellId: 'Intranet Indiana withdrawal',
    withdrawFiatDesc: '24/365 standardization calculate',
    withdrawBtcDesc: 'Market',
    formEmailFrom: 'Plastic synthesize',
    emailNotifyNewUsers: 'N',
    passRegex: 'Garden Auto dynamic',
    passMinChars: 'Customer National',
    authDbDebug: 'N',
    bitcoinReserveMin: 'strategic Associate',
    bitcoinReserveRatio: 'Account Profound',
    bitcoinWarmWalletAddress: 'Shirt parsing',
    cronDbDebug: 'N',
    quandlApiKey: 'Tasty',
    cronDirroot: 'Product Dynamic knowledge',
    backstageDbDebug: 'N',
    backstageDirroot: 'content',
    emailNotifyFiatWithdrawals: 'Y',
    contactEmail: 'Garden',
    cloudflareApiKey: 'Director Niue program',
    googleRecaptchApiKey: 'Metal reciprocal Director',
    cloudflareBlacklist: 'Y',
    cloudflareEmail: 'Data invoice',
    googleRecaptchApiSecret: 'Rubber Unbranded Global',
    cloudflareBlacklistAttempts: 81285,
    cloudflareBlacklistTimeframe: 77793,
    cryptoCapitalPk: 'Field Operative cross-platform',
    depositFiatDesc: 'Card',
    emailNotifyFiatFailed: 'Y',
    tradingStatus: 'Monaco Rubber',
    withdrawalsStatus: 'Director Credit',
  };

  let appConfiguration: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/app-configurations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/app-configurations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/app-configurations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (appConfiguration) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/app-configurations/${appConfiguration.id}`,
      }).then(() => {
        appConfiguration = undefined;
      });
    }
  });

  it('AppConfigurations menu should load AppConfigurations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('app-configuration');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AppConfiguration').should('exist');
    cy.url().should('match', appConfigurationPageUrlPattern);
  });

  describe('AppConfiguration page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(appConfigurationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AppConfiguration page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/app-configuration/new$'));
        cy.getEntityCreateUpdateHeading('AppConfiguration');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', appConfigurationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/app-configurations',
          body: appConfigurationSample,
        }).then(({ body }) => {
          appConfiguration = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/app-configurations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/app-configurations?page=0&size=20>; rel="last",<http://localhost/api/app-configurations?page=0&size=20>; rel="first"',
              },
              body: [appConfiguration],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(appConfigurationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AppConfiguration page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('appConfiguration');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', appConfigurationPageUrlPattern);
      });

      it('edit button click should load edit AppConfiguration page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AppConfiguration');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', appConfigurationPageUrlPattern);
      });

      it('last delete button click should delete instance of AppConfiguration', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('appConfiguration').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', appConfigurationPageUrlPattern);

        appConfiguration = undefined;
      });
    });
  });

  describe('new AppConfiguration page', () => {
    beforeEach(() => {
      cy.visit(`${appConfigurationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AppConfiguration');
    });

    it('should create an instance of AppConfiguration', () => {
      cy.get(`[data-cy="defaultTimezone"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="ordersUnderMarketPercent"]`).type('Refined Concrete Rustic').should('have.value', 'Refined Concrete Rustic');

      cy.get(`[data-cy="ordersMinUsd"]`).type('gold').should('have.value', 'gold');

      cy.get(`[data-cy="bitcoinSendingFee"]`).type('Fully-configurable SAS').should('have.value', 'Fully-configurable SAS');

      cy.get(`[data-cy="frontendBaseurl"]`).type('Manager').should('have.value', 'Manager');

      cy.get(`[data-cy="frontendDirroot"]`).type('logistical').should('have.value', 'logistical');

      cy.get(`[data-cy="fiatWithdrawFee"]`).type('Minnesota Loan').should('have.value', 'Minnesota Loan');

      cy.get(`[data-cy="apiDbDebug"]`).select('Y');

      cy.get(`[data-cy="apiDirroot"]`).type('Devolved').should('have.value', 'Devolved');

      cy.get(`[data-cy="supportEmail"]`).type('Branding').should('have.value', 'Branding');

      cy.get(`[data-cy="emailSmtpHost"]`).type('Handmade Rial').should('have.value', 'Handmade Rial');

      cy.get(`[data-cy="emailSmtpPort"]`).type('port').should('have.value', 'port');

      cy.get(`[data-cy="emailSmtpSecurity"]`).type('Oman convergence').should('have.value', 'Oman convergence');

      cy.get(`[data-cy="emailSmtpUsername"]`).type('Berkshire').should('have.value', 'Berkshire');

      cy.get(`[data-cy="emailSmtpPassword"]`).type('Communications Courts').should('have.value', 'Communications Courts');

      cy.get(`[data-cy="emailSmtpSendFrom"]`).type('Pants ability Texas').should('have.value', 'Pants ability Texas');

      cy.get(`[data-cy="bitcoinUsername"]`).type('Fresh').should('have.value', 'Fresh');

      cy.get(`[data-cy="bitcoinAccountname"]`).type('Riel mobile Plastic').should('have.value', 'Riel mobile Plastic');

      cy.get(`[data-cy="bitcoinPassphrase"]`).type('Loan').should('have.value', 'Loan');

      cy.get(`[data-cy="bitcoinHost"]`).type('Integrated stable Licensed').should('have.value', 'Integrated stable Licensed');

      cy.get(`[data-cy="bitcoinPort"]`).type('Account Frozen').should('have.value', 'Account Frozen');

      cy.get(`[data-cy="bitcoinProtocol"]`).type('syndicate').should('have.value', 'syndicate');

      cy.get(`[data-cy="authyApiKey"]`).type('Centralized').should('have.value', 'Centralized');

      cy.get(`[data-cy="helpdeskKey"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="exchangeName"]`).type('Producer').should('have.value', 'Producer');

      cy.get(`[data-cy="mcryptKey"]`).type('Gloves Dobra payment').should('have.value', 'Gloves Dobra payment');

      cy.get(`[data-cy="currencyConversionFee"]`).type('Orchard').should('have.value', 'Orchard');

      cy.get(`[data-cy="crossCurrencyTrades"]`).select('Y');

      cy.get(`[data-cy="btcCurrencyId"]`).type('Liaison paradigms grey').should('have.value', 'Liaison paradigms grey');

      cy.get(`[data-cy="depositBitcoinDesc"]`).type('Investment Gloves line').should('have.value', 'Investment Gloves line');

      cy.get(`[data-cy="defaultFeeScheduleId"]`)
        .type('Canadian open-source proactive')
        .should('have.value', 'Canadian open-source proactive');

      cy.get(`[data-cy="historyBuyId"]`).type('Refined').should('have.value', 'Refined');

      cy.get(`[data-cy="historyDepositId"]`).type('metrics').should('have.value', 'metrics');

      cy.get(`[data-cy="historyLoginId"]`)
        .type('holistic contextually-based Accountability')
        .should('have.value', 'holistic contextually-based Accountability');

      cy.get(`[data-cy="historySellId"]`).type('Ergonomic withdrawal dynamic').should('have.value', 'Ergonomic withdrawal dynamic');

      cy.get(`[data-cy="historyWithdrawId"]`).type('Stream').should('have.value', 'Stream');

      cy.get(`[data-cy="orderTypeAsk"]`).type('Cotton Home').should('have.value', 'Cotton Home');

      cy.get(`[data-cy="requestAwaitingId"]`).type('monitor').should('have.value', 'monitor');

      cy.get(`[data-cy="requestCancelledId"]`).type('cyan neural').should('have.value', 'cyan neural');

      cy.get(`[data-cy="requestCompletedId"]`).type('deposit Malagasy').should('have.value', 'deposit Malagasy');

      cy.get(`[data-cy="orderTypeBid"]`).type('parse').should('have.value', 'parse');

      cy.get(`[data-cy="requestDepositId"]`).type('Account content-based').should('have.value', 'Account content-based');

      cy.get(`[data-cy="requestPendingId"]`).type('Courts').should('have.value', 'Courts');

      cy.get(`[data-cy="requestWithdrawalId"]`).type('Global Berkshire orange').should('have.value', 'Global Berkshire orange');

      cy.get(`[data-cy="transactionsBuyId"]`).type('Chips Associate XSS').should('have.value', 'Chips Associate XSS');

      cy.get(`[data-cy="transactionsSellId"]`).type('Regional Benin').should('have.value', 'Regional Benin');

      cy.get(`[data-cy="withdrawFiatDesc"]`).type('Analyst Soft Chicken').should('have.value', 'Analyst Soft Chicken');

      cy.get(`[data-cy="withdrawBtcDesc"]`).type('invoice Gorgeous').should('have.value', 'invoice Gorgeous');

      cy.get(`[data-cy="formEmailFrom"]`).type('driver').should('have.value', 'driver');

      cy.get(`[data-cy="emailNotifyNewUsers"]`).select('Y');

      cy.get(`[data-cy="passRegex"]`).type('core Dong').should('have.value', 'core Dong');

      cy.get(`[data-cy="passMinChars"]`).type('real-time Frozen red').should('have.value', 'real-time Frozen red');

      cy.get(`[data-cy="authDbDebug"]`).select('Y');

      cy.get(`[data-cy="bitcoinReserveMin"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="bitcoinReserveRatio"]`).type('Tala').should('have.value', 'Tala');

      cy.get(`[data-cy="bitcoinWarmWalletAddress"]`)
        .type('application turquoise Alabama')
        .should('have.value', 'application turquoise Alabama');

      cy.get(`[data-cy="cronDbDebug"]`).select('Y');

      cy.get(`[data-cy="quandlApiKey"]`).type('Polarised Borders Configuration').should('have.value', 'Polarised Borders Configuration');

      cy.get(`[data-cy="cronDirroot"]`).type('back').should('have.value', 'back');

      cy.get(`[data-cy="backstageDbDebug"]`).select('Y');

      cy.get(`[data-cy="backstageDirroot"]`).type('one-to-one').should('have.value', 'one-to-one');

      cy.get(`[data-cy="emailNotifyFiatWithdrawals"]`).select('N');

      cy.get(`[data-cy="contactEmail"]`).type('leverage IB').should('have.value', 'leverage IB');

      cy.get(`[data-cy="cloudflareApiKey"]`).type('RAM Keyboard').should('have.value', 'RAM Keyboard');

      cy.get(`[data-cy="googleRecaptchApiKey"]`).type('Checking Creative mesh').should('have.value', 'Checking Creative mesh');

      cy.get(`[data-cy="cloudflareBlacklist"]`).select('N');

      cy.get(`[data-cy="cloudflareEmail"]`).type('Washington').should('have.value', 'Washington');

      cy.get(`[data-cy="googleRecaptchApiSecret"]`).type('Egypt Borders').should('have.value', 'Egypt Borders');

      cy.get(`[data-cy="cloudflareBlacklistAttempts"]`).type('15760').should('have.value', '15760');

      cy.get(`[data-cy="cloudflareBlacklistTimeframe"]`).type('69508').should('have.value', '69508');

      cy.get(`[data-cy="cryptoCapitalPk"]`).type('solutions improvement').should('have.value', 'solutions improvement');

      cy.get(`[data-cy="depositFiatDesc"]`).type('PCI Mouse Switchable').should('have.value', 'PCI Mouse Switchable');

      cy.get(`[data-cy="emailNotifyFiatFailed"]`).select('N');

      cy.get(`[data-cy="tradingStatus"]`).type('Grocery').should('have.value', 'Grocery');

      cy.get(`[data-cy="withdrawalsStatus"]`).type('drive bypass').should('have.value', 'drive bypass');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        appConfiguration = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', appConfigurationPageUrlPattern);
    });
  });
});
