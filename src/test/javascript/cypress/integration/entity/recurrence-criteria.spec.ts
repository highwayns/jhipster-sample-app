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

describe('RecurrenceCriteria e2e test', () => {
  const recurrenceCriteriaPageUrl = '/recurrence-criteria';
  const recurrenceCriteriaPageUrlPattern = new RegExp('/recurrence-criteria(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const recurrenceCriteriaSample = {};

  let recurrenceCriteria: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/recurrence-criteria+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/recurrence-criteria').as('postEntityRequest');
    cy.intercept('DELETE', '/api/recurrence-criteria/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (recurrenceCriteria) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/recurrence-criteria/${recurrenceCriteria.id}`,
      }).then(() => {
        recurrenceCriteria = undefined;
      });
    }
  });

  it('RecurrenceCriteria menu should load RecurrenceCriteria page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('recurrence-criteria');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RecurrenceCriteria').should('exist');
    cy.url().should('match', recurrenceCriteriaPageUrlPattern);
  });

  describe('RecurrenceCriteria page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(recurrenceCriteriaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RecurrenceCriteria page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/recurrence-criteria/new$'));
        cy.getEntityCreateUpdateHeading('RecurrenceCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recurrenceCriteriaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/recurrence-criteria',
          body: recurrenceCriteriaSample,
        }).then(({ body }) => {
          recurrenceCriteria = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/recurrence-criteria+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [recurrenceCriteria],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(recurrenceCriteriaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RecurrenceCriteria page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('recurrenceCriteria');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recurrenceCriteriaPageUrlPattern);
      });

      it('edit button click should load edit RecurrenceCriteria page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RecurrenceCriteria');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recurrenceCriteriaPageUrlPattern);
      });

      it('last delete button click should delete instance of RecurrenceCriteria', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('recurrenceCriteria').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', recurrenceCriteriaPageUrlPattern);

        recurrenceCriteria = undefined;
      });
    });
  });

  describe('new RecurrenceCriteria page', () => {
    beforeEach(() => {
      cy.visit(`${recurrenceCriteriaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RecurrenceCriteria');
    });

    it('should create an instance of RecurrenceCriteria', () => {
      cy.get(`[data-cy="recurrenceType"]`).select('CUSTOMERINITIATED');

      cy.get(`[data-cy="recurringExpiry"]`).type('2022-07-08T07:00').should('have.value', '2022-07-08T07:00');

      cy.get(`[data-cy="recurringFrequency"]`).type('18344').should('have.value', '18344');

      cy.get(`[data-cy="instalments"]`).type('45641').should('have.value', '45641');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        recurrenceCriteria = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', recurrenceCriteriaPageUrlPattern);
    });
  });
});
