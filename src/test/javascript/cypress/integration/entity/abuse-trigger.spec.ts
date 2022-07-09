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

describe('AbuseTrigger e2e test', () => {
  const abuseTriggerPageUrl = '/abuse-trigger';
  const abuseTriggerPageUrlPattern = new RegExp('/abuse-trigger(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const abuseTriggerSample = {};

  let abuseTrigger: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/abuse-triggers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/abuse-triggers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/abuse-triggers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (abuseTrigger) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/abuse-triggers/${abuseTrigger.id}`,
      }).then(() => {
        abuseTrigger = undefined;
      });
    }
  });

  it('AbuseTriggers menu should load AbuseTriggers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('abuse-trigger');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AbuseTrigger').should('exist');
    cy.url().should('match', abuseTriggerPageUrlPattern);
  });

  describe('AbuseTrigger page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(abuseTriggerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AbuseTrigger page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/abuse-trigger/new$'));
        cy.getEntityCreateUpdateHeading('AbuseTrigger');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseTriggerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/abuse-triggers',
          body: abuseTriggerSample,
        }).then(({ body }) => {
          abuseTrigger = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/abuse-triggers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [abuseTrigger],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(abuseTriggerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AbuseTrigger page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('abuseTrigger');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseTriggerPageUrlPattern);
      });

      it('edit button click should load edit AbuseTrigger page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AbuseTrigger');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseTriggerPageUrlPattern);
      });

      it('last delete button click should delete instance of AbuseTrigger', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('abuseTrigger').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseTriggerPageUrlPattern);

        abuseTrigger = undefined;
      });
    });
  });

  describe('new AbuseTrigger page', () => {
    beforeEach(() => {
      cy.visit(`${abuseTriggerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AbuseTrigger');
    });

    it('should create an instance of AbuseTrigger', () => {
      cy.get(`[data-cy="score"]`).type('50009').should('have.value', '50009');

      cy.get(`[data-cy="code"]`).type('Up-sized').should('have.value', 'Up-sized');

      cy.get(`[data-cy="description"]`).type('navigate RSS').should('have.value', 'navigate RSS');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        abuseTrigger = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', abuseTriggerPageUrlPattern);
    });
  });
});
