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

describe('Parameters e2e test', () => {
  const parametersPageUrl = '/parameters';
  const parametersPageUrlPattern = new RegExp('/parameters(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const parametersSample = {};

  let parameters: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/parameters+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/parameters').as('postEntityRequest');
    cy.intercept('DELETE', '/api/parameters/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (parameters) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/parameters/${parameters.id}`,
      }).then(() => {
        parameters = undefined;
      });
    }
  });

  it('Parameters menu should load Parameters page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('parameters');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Parameters').should('exist');
    cy.url().should('match', parametersPageUrlPattern);
  });

  describe('Parameters page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(parametersPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Parameters page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/parameters/new$'));
        cy.getEntityCreateUpdateHeading('Parameters');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', parametersPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/parameters',
          body: parametersSample,
        }).then(({ body }) => {
          parameters = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/parameters+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [parameters],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(parametersPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Parameters page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('parameters');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', parametersPageUrlPattern);
      });

      it('edit button click should load edit Parameters page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Parameters');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', parametersPageUrlPattern);
      });

      it('last delete button click should delete instance of Parameters', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('parameters').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', parametersPageUrlPattern);

        parameters = undefined;
      });
    });
  });

  describe('new Parameters page', () => {
    beforeEach(() => {
      cy.visit(`${parametersPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Parameters');
    });

    it('should create an instance of Parameters', () => {
      cy.get(`[data-cy="parameter"]`).type('up Borders Legacy').should('have.value', 'up Borders Legacy');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        parameters = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', parametersPageUrlPattern);
    });
  });
});
