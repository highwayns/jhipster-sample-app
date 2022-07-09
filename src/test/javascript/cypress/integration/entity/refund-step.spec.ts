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

describe('RefundStep e2e test', () => {
  const refundStepPageUrl = '/refund-step';
  const refundStepPageUrlPattern = new RegExp('/refund-step(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const refundStepSample = {};

  let refundStep: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/refund-steps+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/refund-steps').as('postEntityRequest');
    cy.intercept('DELETE', '/api/refund-steps/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (refundStep) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/refund-steps/${refundStep.id}`,
      }).then(() => {
        refundStep = undefined;
      });
    }
  });

  it('RefundSteps menu should load RefundSteps page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('refund-step');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RefundStep').should('exist');
    cy.url().should('match', refundStepPageUrlPattern);
  });

  describe('RefundStep page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(refundStepPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RefundStep page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/refund-step/new$'));
        cy.getEntityCreateUpdateHeading('RefundStep');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundStepPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/refund-steps',
          body: refundStepSample,
        }).then(({ body }) => {
          refundStep = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/refund-steps+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [refundStep],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(refundStepPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RefundStep page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('refundStep');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundStepPageUrlPattern);
      });

      it('edit button click should load edit RefundStep page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RefundStep');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundStepPageUrlPattern);
      });

      it('last delete button click should delete instance of RefundStep', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('refundStep').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundStepPageUrlPattern);

        refundStep = undefined;
      });
    });
  });

  describe('new RefundStep page', () => {
    beforeEach(() => {
      cy.visit(`${refundStepPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RefundStep');
    });

    it('should create an instance of RefundStep', () => {
      cy.get(`[data-cy="reference"]`).type('4362').should('have.value', '4362');

      cy.get(`[data-cy="createDateTimeUtc"]`).type('2022-07-08T07:17').should('have.value', '2022-07-08T07:17');

      cy.get(`[data-cy="action"]`).select('CANCEL');

      cy.get(`[data-cy="status"]`).select('PENDING');

      cy.get(`[data-cy="description"]`).type('Account PCI Berkshire').should('have.value', 'Account PCI Berkshire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        refundStep = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', refundStepPageUrlPattern);
    });
  });
});
