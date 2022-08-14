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

describe('TransactionTypes e2e test', () => {
  const transactionTypesPageUrl = '/transaction-types';
  const transactionTypesPageUrlPattern = new RegExp('/transaction-types(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const transactionTypesSample = {
    nameEn: 'Rubber deposit',
    nameEs: 'Legacy Automotive',
    nameRu: 'Planner',
    nameZh: 'indexing Liaison Front-line',
  };

  let transactionTypes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/transaction-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/transaction-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/transaction-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (transactionTypes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/transaction-types/${transactionTypes.id}`,
      }).then(() => {
        transactionTypes = undefined;
      });
    }
  });

  it('TransactionTypes menu should load TransactionTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('transaction-types');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TransactionTypes').should('exist');
    cy.url().should('match', transactionTypesPageUrlPattern);
  });

  describe('TransactionTypes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(transactionTypesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TransactionTypes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/transaction-types/new$'));
        cy.getEntityCreateUpdateHeading('TransactionTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionTypesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/transaction-types',
          body: transactionTypesSample,
        }).then(({ body }) => {
          transactionTypes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/transaction-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/transaction-types?page=0&size=20>; rel="last",<http://localhost/api/transaction-types?page=0&size=20>; rel="first"',
              },
              body: [transactionTypes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(transactionTypesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TransactionTypes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('transactionTypes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionTypesPageUrlPattern);
      });

      it('edit button click should load edit TransactionTypes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TransactionTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionTypesPageUrlPattern);
      });

      it('last delete button click should delete instance of TransactionTypes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('transactionTypes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionTypesPageUrlPattern);

        transactionTypes = undefined;
      });
    });
  });

  describe('new TransactionTypes page', () => {
    beforeEach(() => {
      cy.visit(`${transactionTypesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TransactionTypes');
    });

    it('should create an instance of TransactionTypes', () => {
      cy.get(`[data-cy="nameEn"]`).type('interface Garden').should('have.value', 'interface Garden');

      cy.get(`[data-cy="nameEs"]`).type('vertical').should('have.value', 'vertical');

      cy.get(`[data-cy="nameRu"]`).type('Security Music').should('have.value', 'Security Music');

      cy.get(`[data-cy="nameZh"]`).type('software User-centric Maine').should('have.value', 'software User-centric Maine');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        transactionTypes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', transactionTypesPageUrlPattern);
    });
  });
});
