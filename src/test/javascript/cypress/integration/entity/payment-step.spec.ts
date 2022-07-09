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

describe('PaymentStep e2e test', () => {
  const paymentStepPageUrl = '/payment-step';
  const paymentStepPageUrlPattern = new RegExp('/payment-step(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentStepSample = {};

  let paymentStep: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-steps+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-steps').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-steps/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentStep) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-steps/${paymentStep.id}`,
      }).then(() => {
        paymentStep = undefined;
      });
    }
  });

  it('PaymentSteps menu should load PaymentSteps page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-step');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentStep').should('exist');
    cy.url().should('match', paymentStepPageUrlPattern);
  });

  describe('PaymentStep page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentStepPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentStep page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-step/new$'));
        cy.getEntityCreateUpdateHeading('PaymentStep');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentStepPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-steps',
          body: paymentStepSample,
        }).then(({ body }) => {
          paymentStep = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-steps+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentStep],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentStepPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentStep page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentStep');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentStepPageUrlPattern);
      });

      it('edit button click should load edit PaymentStep page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentStep');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentStepPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentStep', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentStep').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentStepPageUrlPattern);

        paymentStep = undefined;
      });
    });
  });

  describe('new PaymentStep page', () => {
    beforeEach(() => {
      cy.visit(`${paymentStepPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentStep');
    });

    it('should create an instance of PaymentStep', () => {
      cy.get(`[data-cy="reference"]`).type('22574').should('have.value', '22574');

      cy.get(`[data-cy="createDateTimeUtc"]`).type('2022-07-08T15:05').should('have.value', '2022-07-08T15:05');

      cy.get(`[data-cy="action"]`).select('START');

      cy.get(`[data-cy="status"]`).select('FAILED');

      cy.get(`[data-cy="amountToCollect"]`).type('60428').should('have.value', '60428');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentStep = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentStepPageUrlPattern);
    });
  });
});
