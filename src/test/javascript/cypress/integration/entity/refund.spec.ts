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

describe('Refund e2e test', () => {
  const refundPageUrl = '/refund';
  const refundPageUrlPattern = new RegExp('/refund(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const refundSample = {};

  let refund: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/refunds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/refunds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/refunds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (refund) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/refunds/${refund.id}`,
      }).then(() => {
        refund = undefined;
      });
    }
  });

  it('Refunds menu should load Refunds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('refund');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Refund').should('exist');
    cy.url().should('match', refundPageUrlPattern);
  });

  describe('Refund page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(refundPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Refund page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/refund/new$'));
        cy.getEntityCreateUpdateHeading('Refund');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/refunds',
          body: refundSample,
        }).then(({ body }) => {
          refund = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/refunds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [refund],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(refundPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Refund page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('refund');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundPageUrlPattern);
      });

      it('edit button click should load edit Refund page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Refund');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundPageUrlPattern);
      });

      it('last delete button click should delete instance of Refund', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('refund').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', refundPageUrlPattern);

        refund = undefined;
      });
    });
  });

  describe('new Refund page', () => {
    beforeEach(() => {
      cy.visit(`${refundPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Refund');
    });

    it('should create an instance of Refund', () => {
      cy.get(`[data-cy="reference"]`).type('76375').should('have.value', '76375');

      cy.get(`[data-cy="createDateTimeUtc"]`).type('2022-07-08T03:18').should('have.value', '2022-07-08T03:18');

      cy.get(`[data-cy="refundNumber"]`).type('input').should('have.value', 'input');

      cy.get(`[data-cy="status"]`).select('FAILED');

      cy.get(`[data-cy="amountToRefund"]`).type('82594').should('have.value', '82594');

      cy.get(`[data-cy="convertedAmountToRefund"]`).type('58968').should('have.value', '58968');

      cy.get(`[data-cy="convertedCurrency"]`).select('RUB');

      cy.get(`[data-cy="conversionRate"]`).type('22900').should('have.value', '22900');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        refund = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', refundPageUrlPattern);
    });
  });
});
