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

describe('DailyReports e2e test', () => {
  const dailyReportsPageUrl = '/daily-reports';
  const dailyReportsPageUrlPattern = new RegExp('/daily-reports(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dailyReportsSample = {
    date: '2022-08-13T20:05:18.909Z',
    totalBtc: 45892,
    totalFiatUsd: 11127,
    openOrdersBtc: 68134,
    btcPerUser: 82903,
    transactionsBtc: 56512,
    avgTransactionSizeBtc: 58332,
    transactionVolumePerUser: 51175,
    totalFeesBtc: 60573,
    feesPerUserBtc: 25076,
    usdPerUser: 69048,
    grossProfitBtc: 97034,
  };

  let dailyReports: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/daily-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/daily-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/daily-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dailyReports) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/daily-reports/${dailyReports.id}`,
      }).then(() => {
        dailyReports = undefined;
      });
    }
  });

  it('DailyReports menu should load DailyReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('daily-reports');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DailyReports').should('exist');
    cy.url().should('match', dailyReportsPageUrlPattern);
  });

  describe('DailyReports page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dailyReportsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DailyReports page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/daily-reports/new$'));
        cy.getEntityCreateUpdateHeading('DailyReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyReportsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/daily-reports',
          body: dailyReportsSample,
        }).then(({ body }) => {
          dailyReports = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/daily-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/daily-reports?page=0&size=20>; rel="last",<http://localhost/api/daily-reports?page=0&size=20>; rel="first"',
              },
              body: [dailyReports],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(dailyReportsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DailyReports page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dailyReports');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyReportsPageUrlPattern);
      });

      it('edit button click should load edit DailyReports page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DailyReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyReportsPageUrlPattern);
      });

      it('last delete button click should delete instance of DailyReports', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('dailyReports').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyReportsPageUrlPattern);

        dailyReports = undefined;
      });
    });
  });

  describe('new DailyReports page', () => {
    beforeEach(() => {
      cy.visit(`${dailyReportsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DailyReports');
    });

    it('should create an instance of DailyReports', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-14T07:56').should('have.value', '2022-08-14T07:56');

      cy.get(`[data-cy="totalBtc"]`).type('88384').should('have.value', '88384');

      cy.get(`[data-cy="totalFiatUsd"]`).type('10585').should('have.value', '10585');

      cy.get(`[data-cy="openOrdersBtc"]`).type('61515').should('have.value', '61515');

      cy.get(`[data-cy="btcPerUser"]`).type('17866').should('have.value', '17866');

      cy.get(`[data-cy="transactionsBtc"]`).type('60586').should('have.value', '60586');

      cy.get(`[data-cy="avgTransactionSizeBtc"]`).type('15741').should('have.value', '15741');

      cy.get(`[data-cy="transactionVolumePerUser"]`).type('29196').should('have.value', '29196');

      cy.get(`[data-cy="totalFeesBtc"]`).type('2740').should('have.value', '2740');

      cy.get(`[data-cy="feesPerUserBtc"]`).type('30112').should('have.value', '30112');

      cy.get(`[data-cy="usdPerUser"]`).type('735').should('have.value', '735');

      cy.get(`[data-cy="grossProfitBtc"]`).type('22765').should('have.value', '22765');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        dailyReports = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', dailyReportsPageUrlPattern);
    });
  });
});
