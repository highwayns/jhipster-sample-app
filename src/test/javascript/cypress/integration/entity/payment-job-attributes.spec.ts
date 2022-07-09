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

describe('PaymentJobAttributes e2e test', () => {
  const paymentJobAttributesPageUrl = '/payment-job-attributes';
  const paymentJobAttributesPageUrlPattern = new RegExp('/payment-job-attributes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentJobAttributesSample = {};

  let paymentJobAttributes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-job-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-job-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-job-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentJobAttributes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-job-attributes/${paymentJobAttributes.id}`,
      }).then(() => {
        paymentJobAttributes = undefined;
      });
    }
  });

  it('PaymentJobAttributes menu should load PaymentJobAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-job-attributes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentJobAttributes').should('exist');
    cy.url().should('match', paymentJobAttributesPageUrlPattern);
  });

  describe('PaymentJobAttributes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentJobAttributesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentJobAttributes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-job-attributes/new$'));
        cy.getEntityCreateUpdateHeading('PaymentJobAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobAttributesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-job-attributes',
          body: paymentJobAttributesSample,
        }).then(({ body }) => {
          paymentJobAttributes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-job-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentJobAttributes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentJobAttributesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentJobAttributes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentJobAttributes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobAttributesPageUrlPattern);
      });

      it('edit button click should load edit PaymentJobAttributes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentJobAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobAttributesPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentJobAttributes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentJobAttributes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobAttributesPageUrlPattern);

        paymentJobAttributes = undefined;
      });
    });
  });

  describe('new PaymentJobAttributes page', () => {
    beforeEach(() => {
      cy.visit(`${paymentJobAttributesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentJobAttributes');
    });

    it('should create an instance of PaymentJobAttributes', () => {
      cy.get(`[data-cy="webhookUrl"]`).type('scale ADP Soap').should('have.value', 'scale ADP Soap');

      cy.get(`[data-cy="googleAnalyticsClientId"]`).type('networks AGP').should('have.value', 'networks AGP');

      cy.get(`[data-cy="allowedParentFrameDomains"]`).type('West to Assistant').should('have.value', 'West to Assistant');

      cy.get(`[data-cy="paymentPageReference"]`).type('transitional capacitor').should('have.value', 'transitional capacitor');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentJobAttributes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentJobAttributesPageUrlPattern);
    });
  });
});
