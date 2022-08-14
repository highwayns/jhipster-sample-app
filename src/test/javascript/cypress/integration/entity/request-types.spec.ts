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

describe('RequestTypes e2e test', () => {
  const requestTypesPageUrl = '/request-types';
  const requestTypesPageUrlPattern = new RegExp('/request-types(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestTypesSample = { nameEn: 'Steel redundant', nameEs: 'maximize', nameRu: 'TCP', nameZh: 'Koruna Zloty' };

  let requestTypes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestTypes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-types/${requestTypes.id}`,
      }).then(() => {
        requestTypes = undefined;
      });
    }
  });

  it('RequestTypes menu should load RequestTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-types');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestTypes').should('exist');
    cy.url().should('match', requestTypesPageUrlPattern);
  });

  describe('RequestTypes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestTypesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestTypes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-types/new$'));
        cy.getEntityCreateUpdateHeading('RequestTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestTypesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-types',
          body: requestTypesSample,
        }).then(({ body }) => {
          requestTypes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/request-types?page=0&size=20>; rel="last",<http://localhost/api/request-types?page=0&size=20>; rel="first"',
              },
              body: [requestTypes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestTypesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestTypes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestTypes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestTypesPageUrlPattern);
      });

      it('edit button click should load edit RequestTypes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestTypesPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestTypes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('requestTypes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestTypesPageUrlPattern);

        requestTypes = undefined;
      });
    });
  });

  describe('new RequestTypes page', () => {
    beforeEach(() => {
      cy.visit(`${requestTypesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestTypes');
    });

    it('should create an instance of RequestTypes', () => {
      cy.get(`[data-cy="nameEn"]`).type('Global Buckinghamshire syndicate').should('have.value', 'Global Buckinghamshire syndicate');

      cy.get(`[data-cy="nameEs"]`).type('Awesome bypassing secondary').should('have.value', 'Awesome bypassing secondary');

      cy.get(`[data-cy="nameRu"]`).type('Berkshire Configuration Peso').should('have.value', 'Berkshire Configuration Peso');

      cy.get(`[data-cy="nameZh"]`).type('Assurance SCSI').should('have.value', 'Assurance SCSI');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        requestTypes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', requestTypesPageUrlPattern);
    });
  });
});
