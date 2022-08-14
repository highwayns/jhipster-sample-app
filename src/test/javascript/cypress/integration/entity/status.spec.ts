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

describe('Status e2e test', () => {
  const statusPageUrl = '/status';
  const statusPageUrlPattern = new RegExp('/status(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const statusSample = {
    lastSweep: '2022-08-14T04:16:05.634Z',
    deficitBtc: 59068,
    hotWalletBtc: 47675,
    warmWalletBtc: 72093,
    totalBtc: 92420,
    receivedBtcPending: 55427,
    pendingWithdrawals: 63153,
    tradingStatus: 'Factors Licensed Enterprise-wide',
    withdrawalsStatus: 'Markets copying',
    dbVersion: 83347,
    cronDailyStats: '2022-08-14T08:52:38.809Z',
    cronGetStats: '2022-08-13T16:23:42.741Z',
    cronMaintenance: '2022-08-13T16:26:10.663Z',
    cronMonthlyStats: '2022-08-13T19:27:18.729Z',
    cronReceiveBitcoin: '2022-08-14T07:24:28.482Z',
    cronSendBitcoin: '2022-08-13T09:32:23.822Z',
  };

  let status: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/statuses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/statuses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/statuses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (status) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/statuses/${status.id}`,
      }).then(() => {
        status = undefined;
      });
    }
  });

  it('Statuses menu should load Statuses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('status');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Status').should('exist');
    cy.url().should('match', statusPageUrlPattern);
  });

  describe('Status page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(statusPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Status page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/status/new$'));
        cy.getEntityCreateUpdateHeading('Status');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/statuses',
          body: statusSample,
        }).then(({ body }) => {
          status = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/statuses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/statuses?page=0&size=20>; rel="last",<http://localhost/api/statuses?page=0&size=20>; rel="first"',
              },
              body: [status],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(statusPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Status page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('status');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusPageUrlPattern);
      });

      it('edit button click should load edit Status page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Status');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusPageUrlPattern);
      });

      it('last delete button click should delete instance of Status', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('status').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusPageUrlPattern);

        status = undefined;
      });
    });
  });

  describe('new Status page', () => {
    beforeEach(() => {
      cy.visit(`${statusPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Status');
    });

    it('should create an instance of Status', () => {
      cy.get(`[data-cy="lastSweep"]`).type('2022-08-14T03:24').should('have.value', '2022-08-14T03:24');

      cy.get(`[data-cy="deficitBtc"]`).type('71809').should('have.value', '71809');

      cy.get(`[data-cy="hotWalletBtc"]`).type('32784').should('have.value', '32784');

      cy.get(`[data-cy="warmWalletBtc"]`).type('11953').should('have.value', '11953');

      cy.get(`[data-cy="totalBtc"]`).type('95392').should('have.value', '95392');

      cy.get(`[data-cy="receivedBtcPending"]`).type('298').should('have.value', '298');

      cy.get(`[data-cy="pendingWithdrawals"]`).type('11243').should('have.value', '11243');

      cy.get(`[data-cy="tradingStatus"]`).type('white parsing').should('have.value', 'white parsing');

      cy.get(`[data-cy="withdrawalsStatus"]`).type('Soft Balanced Operations').should('have.value', 'Soft Balanced Operations');

      cy.get(`[data-cy="dbVersion"]`).type('54555').should('have.value', '54555');

      cy.get(`[data-cy="cronDailyStats"]`).type('2022-08-14T00:00').should('have.value', '2022-08-14T00:00');

      cy.get(`[data-cy="cronGetStats"]`).type('2022-08-14T03:38').should('have.value', '2022-08-14T03:38');

      cy.get(`[data-cy="cronMaintenance"]`).type('2022-08-14T03:11').should('have.value', '2022-08-14T03:11');

      cy.get(`[data-cy="cronMonthlyStats"]`).type('2022-08-13T11:09').should('have.value', '2022-08-13T11:09');

      cy.get(`[data-cy="cronReceiveBitcoin"]`).type('2022-08-13T11:02').should('have.value', '2022-08-13T11:02');

      cy.get(`[data-cy="cronSendBitcoin"]`).type('2022-08-13T12:40').should('have.value', '2022-08-13T12:40');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        status = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', statusPageUrlPattern);
    });
  });
});
