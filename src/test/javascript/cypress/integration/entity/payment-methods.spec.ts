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

describe('PaymentMethods e2e test', () => {
  const paymentMethodsPageUrl = '/payment-methods';
  const paymentMethodsPageUrlPattern = new RegExp('/payment-methods(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentMethodsSample = {};

  let paymentMethods: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-methods+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-methods').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-methods/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentMethods) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-methods/${paymentMethods.id}`,
      }).then(() => {
        paymentMethods = undefined;
      });
    }
  });

  it('PaymentMethods menu should load PaymentMethods page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-methods');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentMethods').should('exist');
    cy.url().should('match', paymentMethodsPageUrlPattern);
  });

  describe('PaymentMethods page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentMethodsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentMethods page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-methods/new$'));
        cy.getEntityCreateUpdateHeading('PaymentMethods');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-methods',
          body: paymentMethodsSample,
        }).then(({ body }) => {
          paymentMethods = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-methods+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentMethods],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentMethodsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentMethods page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentMethods');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodsPageUrlPattern);
      });

      it('edit button click should load edit PaymentMethods page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentMethods');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodsPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentMethods', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentMethods').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodsPageUrlPattern);

        paymentMethods = undefined;
      });
    });
  });

  describe('new PaymentMethods page', () => {
    beforeEach(() => {
      cy.visit(`${paymentMethodsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentMethods');
    });

    it('should create an instance of PaymentMethods', () => {
      cy.get(`[data-cy="paymentMethod"]`).select('KLARNASLICEIT');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentMethods = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentMethodsPageUrlPattern);
    });
  });
});
