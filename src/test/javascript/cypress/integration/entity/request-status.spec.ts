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

describe('RequestStatus e2e test', () => {
  const requestStatusPageUrl = '/request-status';
  const requestStatusPageUrlPattern = new RegExp('/request-status(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestStatusSample = {
    nameEn: 'SSL invoice open-source',
    nameEs: 'Verde',
    nameRu: 'withdrawal River blue',
    nameZh: 'JBOD hardware Planner',
  };

  let requestStatus: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-statuses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-statuses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-statuses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestStatus) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-statuses/${requestStatus.id}`,
      }).then(() => {
        requestStatus = undefined;
      });
    }
  });

  it('RequestStatuses menu should load RequestStatuses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-status');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestStatus').should('exist');
    cy.url().should('match', requestStatusPageUrlPattern);
  });

  describe('RequestStatus page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestStatusPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestStatus page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-status/new$'));
        cy.getEntityCreateUpdateHeading('RequestStatus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestStatusPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-statuses',
          body: requestStatusSample,
        }).then(({ body }) => {
          requestStatus = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-statuses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/request-statuses?page=0&size=20>; rel="last",<http://localhost/api/request-statuses?page=0&size=20>; rel="first"',
              },
              body: [requestStatus],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestStatusPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestStatus page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestStatus');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestStatusPageUrlPattern);
      });

      it('edit button click should load edit RequestStatus page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestStatus');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestStatusPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestStatus', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('requestStatus').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestStatusPageUrlPattern);

        requestStatus = undefined;
      });
    });
  });

  describe('new RequestStatus page', () => {
    beforeEach(() => {
      cy.visit(`${requestStatusPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestStatus');
    });

    it('should create an instance of RequestStatus', () => {
      cy.get(`[data-cy="nameEn"]`).type('West').should('have.value', 'West');

      cy.get(`[data-cy="nameEs"]`).type('Alley local').should('have.value', 'Alley local');

      cy.get(`[data-cy="nameRu"]`).type('Steel port').should('have.value', 'Steel port');

      cy.get(`[data-cy="nameZh"]`).type('synthesizing Right-sized back').should('have.value', 'synthesizing Right-sized back');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        requestStatus = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', requestStatusPageUrlPattern);
    });
  });
});
