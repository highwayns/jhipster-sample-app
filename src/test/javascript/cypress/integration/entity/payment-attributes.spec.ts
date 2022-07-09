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

describe('PaymentAttributes e2e test', () => {
  const paymentAttributesPageUrl = '/payment-attributes';
  const paymentAttributesPageUrlPattern = new RegExp('/payment-attributes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentAttributesSample = {};

  let paymentAttributes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentAttributes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-attributes/${paymentAttributes.id}`,
      }).then(() => {
        paymentAttributes = undefined;
      });
    }
  });

  it('PaymentAttributes menu should load PaymentAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-attributes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentAttributes').should('exist');
    cy.url().should('match', paymentAttributesPageUrlPattern);
  });

  describe('PaymentAttributes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentAttributesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentAttributes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-attributes/new$'));
        cy.getEntityCreateUpdateHeading('PaymentAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentAttributesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-attributes',
          body: paymentAttributesSample,
        }).then(({ body }) => {
          paymentAttributes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentAttributes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentAttributesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentAttributes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentAttributes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentAttributesPageUrlPattern);
      });

      it('edit button click should load edit PaymentAttributes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentAttributesPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentAttributes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentAttributes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentAttributesPageUrlPattern);

        paymentAttributes = undefined;
      });
    });
  });

  describe('new PaymentAttributes page', () => {
    beforeEach(() => {
      cy.visit(`${paymentAttributesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentAttributes');
    });

    it('should create an instance of PaymentAttributes', () => {
      cy.get(`[data-cy="originatingIpAddress"]`)
        .type('Business-focused Botswana compressing')
        .should('have.value', 'Business-focused Botswana compressing');

      cy.get(`[data-cy="originHeader"]`).type('green Tasty').should('have.value', 'green Tasty');

      cy.get(`[data-cy="userAgent"]`).type('Cotton').should('have.value', 'Cotton');

      cy.get(`[data-cy="returnUrlSuccess"]`).type('Tasty responsive Global').should('have.value', 'Tasty responsive Global');

      cy.get(`[data-cy="returnUrlFailed"]`).type('Sleek Senior Factors').should('have.value', 'Sleek Senior Factors');

      cy.get(`[data-cy="returnUrlCancelled"]`).type('Chicken').should('have.value', 'Chicken');

      cy.get(`[data-cy="simulatedStatus"]`).type('open-source').should('have.value', 'open-source');

      cy.get(`[data-cy="idealBic"]`).type('Cambridgeshire Group exploit').should('have.value', 'Cambridgeshire Group exploit');

      cy.get(`[data-cy="paymentMethodTransactionId"]`).type('Baby').should('have.value', 'Baby');

      cy.get(`[data-cy="paymentMethodVoidTransactionId"]`).type('Legacy').should('have.value', 'Legacy');

      cy.get(`[data-cy="token"]`).type('Books override Shirt').should('have.value', 'Books override Shirt');

      cy.get(`[data-cy="cashFlowsAcquiringDetails"]`).type('Granite Concrete cohesive').should('have.value', 'Granite Concrete cohesive');

      cy.get(`[data-cy="descriptor"]`).type('neural').should('have.value', 'neural');

      cy.get(`[data-cy="ewalletType"]`).type('payment lime').should('have.value', 'payment lime');

      cy.get(`[data-cy="paymentStatus"]`).select('PENDING');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentAttributes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentAttributesPageUrlPattern);
    });
  });
});
