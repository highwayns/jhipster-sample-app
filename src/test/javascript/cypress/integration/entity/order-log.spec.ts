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

describe('OrderLog e2e test', () => {
  const orderLogPageUrl = '/order-log';
  const orderLogPageUrlPattern = new RegExp('/order-log(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const orderLogSample = {
    date: '2022-08-13T13:04:58.152Z',
    btc: 73871,
    marketPrice: 'Y',
    btcPrice: 8977,
    fiat: 86006,
    pId: 17832,
    stopPrice: 'Small',
    status: 'ACTIVE',
    btcRemaining: 20687,
  };

  let orderLog: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/order-logs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/order-logs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/order-logs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (orderLog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/order-logs/${orderLog.id}`,
      }).then(() => {
        orderLog = undefined;
      });
    }
  });

  it('OrderLogs menu should load OrderLogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-log');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OrderLog').should('exist');
    cy.url().should('match', orderLogPageUrlPattern);
  });

  describe('OrderLog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(orderLogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OrderLog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/order-log/new$'));
        cy.getEntityCreateUpdateHeading('OrderLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/order-logs',
          body: orderLogSample,
        }).then(({ body }) => {
          orderLog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/order-logs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/order-logs?page=0&size=20>; rel="last",<http://localhost/api/order-logs?page=0&size=20>; rel="first"',
              },
              body: [orderLog],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(orderLogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OrderLog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('orderLog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLogPageUrlPattern);
      });

      it('edit button click should load edit OrderLog page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OrderLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLogPageUrlPattern);
      });

      it('last delete button click should delete instance of OrderLog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('orderLog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLogPageUrlPattern);

        orderLog = undefined;
      });
    });
  });

  describe('new OrderLog page', () => {
    beforeEach(() => {
      cy.visit(`${orderLogPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OrderLog');
    });

    it('should create an instance of OrderLog', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-14T01:43').should('have.value', '2022-08-14T01:43');

      cy.get(`[data-cy="btc"]`).type('18316').should('have.value', '18316');

      cy.get(`[data-cy="marketPrice"]`).select('Y');

      cy.get(`[data-cy="btcPrice"]`).type('21394').should('have.value', '21394');

      cy.get(`[data-cy="fiat"]`).type('52934').should('have.value', '52934');

      cy.get(`[data-cy="pId"]`).type('2930').should('have.value', '2930');

      cy.get(`[data-cy="stopPrice"]`).type('Persistent responsive Licensed').should('have.value', 'Persistent responsive Licensed');

      cy.get(`[data-cy="status"]`).select('FILLED');

      cy.get(`[data-cy="btcRemaining"]`).type('98687').should('have.value', '98687');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        orderLog = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', orderLogPageUrlPattern);
    });
  });
});
