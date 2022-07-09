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

describe('OrderLine e2e test', () => {
  const orderLinePageUrl = '/order-line';
  const orderLinePageUrlPattern = new RegExp('/order-line(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const orderLineSample = {};

  let orderLine: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/order-lines+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/order-lines').as('postEntityRequest');
    cy.intercept('DELETE', '/api/order-lines/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (orderLine) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/order-lines/${orderLine.id}`,
      }).then(() => {
        orderLine = undefined;
      });
    }
  });

  it('OrderLines menu should load OrderLines page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-line');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OrderLine').should('exist');
    cy.url().should('match', orderLinePageUrlPattern);
  });

  describe('OrderLine page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(orderLinePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OrderLine page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/order-line/new$'));
        cy.getEntityCreateUpdateHeading('OrderLine');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLinePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/order-lines',
          body: orderLineSample,
        }).then(({ body }) => {
          orderLine = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/order-lines+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [orderLine],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(orderLinePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OrderLine page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('orderLine');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLinePageUrlPattern);
      });

      it('edit button click should load edit OrderLine page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OrderLine');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLinePageUrlPattern);
      });

      it('last delete button click should delete instance of OrderLine', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('orderLine').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderLinePageUrlPattern);

        orderLine = undefined;
      });
    });
  });

  describe('new OrderLine page', () => {
    beforeEach(() => {
      cy.visit(`${orderLinePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OrderLine');
    });

    it('should create an instance of OrderLine', () => {
      cy.get(`[data-cy="lineNumber"]`).type('12218').should('have.value', '12218');

      cy.get(`[data-cy="type"]`).select('DIGITALITEM');

      cy.get(`[data-cy="skuCode"]`).type('synthesizing invoice encryption').should('have.value', 'synthesizing invoice encryption');

      cy.get(`[data-cy="name"]`).type('Somoni').should('have.value', 'Somoni');

      cy.get(`[data-cy="description"]`).type('Savings Mouse Program').should('have.value', 'Savings Mouse Program');

      cy.get(`[data-cy="quantity"]`).type('66504').should('have.value', '66504');

      cy.get(`[data-cy="unitPriceExclVat"]`).type('71342').should('have.value', '71342');

      cy.get(`[data-cy="unitPriceInclVat"]`).type('62516').should('have.value', '62516');

      cy.get(`[data-cy="vatPercentage"]`).type('96616').should('have.value', '96616');

      cy.get(`[data-cy="vatPercentageLabel"]`)
        .type('Customizable Clothing Gorgeous')
        .should('have.value', 'Customizable Clothing Gorgeous');

      cy.get(`[data-cy="discountPercentageLabel"]`).type('quantify').should('have.value', 'quantify');

      cy.get(`[data-cy="totalLineAmount"]`).type('64086').should('have.value', '64086');

      cy.get(`[data-cy="url"]`).type('https://ophelia.org').should('have.value', 'https://ophelia.org');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        orderLine = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', orderLinePageUrlPattern);
    });
  });
});
