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

describe('BitcoindLog e2e test', () => {
  const bitcoindLogPageUrl = '/bitcoind-log';
  const bitcoindLogPageUrlPattern = new RegExp('/bitcoind-log(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bitcoindLogSample = { transactionId: 'optical', amount: 24344, date: '2022-08-14T01:21:14.739Z' };

  let bitcoindLog: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/bitcoind-logs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/bitcoind-logs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/bitcoind-logs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bitcoindLog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/bitcoind-logs/${bitcoindLog.id}`,
      }).then(() => {
        bitcoindLog = undefined;
      });
    }
  });

  it('BitcoindLogs menu should load BitcoindLogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('bitcoind-log');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BitcoindLog').should('exist');
    cy.url().should('match', bitcoindLogPageUrlPattern);
  });

  describe('BitcoindLog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bitcoindLogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BitcoindLog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/bitcoind-log/new$'));
        cy.getEntityCreateUpdateHeading('BitcoindLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoindLogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/bitcoind-logs',
          body: bitcoindLogSample,
        }).then(({ body }) => {
          bitcoindLog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/bitcoind-logs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/bitcoind-logs?page=0&size=20>; rel="last",<http://localhost/api/bitcoind-logs?page=0&size=20>; rel="first"',
              },
              body: [bitcoindLog],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bitcoindLogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BitcoindLog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bitcoindLog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoindLogPageUrlPattern);
      });

      it('edit button click should load edit BitcoindLog page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BitcoindLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoindLogPageUrlPattern);
      });

      it('last delete button click should delete instance of BitcoindLog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bitcoindLog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoindLogPageUrlPattern);

        bitcoindLog = undefined;
      });
    });
  });

  describe('new BitcoindLog page', () => {
    beforeEach(() => {
      cy.visit(`${bitcoindLogPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BitcoindLog');
    });

    it('should create an instance of BitcoindLog', () => {
      cy.get(`[data-cy="transactionId"]`).type('Infrastructure').should('have.value', 'Infrastructure');

      cy.get(`[data-cy="amount"]`).type('46551').should('have.value', '46551');

      cy.get(`[data-cy="date"]`).type('2022-08-13T15:55').should('have.value', '2022-08-13T15:55');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        bitcoindLog = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', bitcoindLogPageUrlPattern);
    });
  });
});
