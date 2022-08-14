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

describe('RequestDescriptions e2e test', () => {
  const requestDescriptionsPageUrl = '/request-descriptions';
  const requestDescriptionsPageUrlPattern = new RegExp('/request-descriptions(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestDescriptionsSample = { nameEn: 'Spur payment Data', nameEs: 'Designer', nameRu: 'internet', nameZh: 'Car' };

  let requestDescriptions: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/request-descriptions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/request-descriptions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/request-descriptions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requestDescriptions) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/request-descriptions/${requestDescriptions.id}`,
      }).then(() => {
        requestDescriptions = undefined;
      });
    }
  });

  it('RequestDescriptions menu should load RequestDescriptions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('request-descriptions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RequestDescriptions').should('exist');
    cy.url().should('match', requestDescriptionsPageUrlPattern);
  });

  describe('RequestDescriptions page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestDescriptionsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RequestDescriptions page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/request-descriptions/new$'));
        cy.getEntityCreateUpdateHeading('RequestDescriptions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestDescriptionsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/request-descriptions',
          body: requestDescriptionsSample,
        }).then(({ body }) => {
          requestDescriptions = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/request-descriptions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/request-descriptions?page=0&size=20>; rel="last",<http://localhost/api/request-descriptions?page=0&size=20>; rel="first"',
              },
              body: [requestDescriptions],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestDescriptionsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RequestDescriptions page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requestDescriptions');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestDescriptionsPageUrlPattern);
      });

      it('edit button click should load edit RequestDescriptions page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RequestDescriptions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestDescriptionsPageUrlPattern);
      });

      it('last delete button click should delete instance of RequestDescriptions', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('requestDescriptions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestDescriptionsPageUrlPattern);

        requestDescriptions = undefined;
      });
    });
  });

  describe('new RequestDescriptions page', () => {
    beforeEach(() => {
      cy.visit(`${requestDescriptionsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RequestDescriptions');
    });

    it('should create an instance of RequestDescriptions', () => {
      cy.get(`[data-cy="nameEn"]`).type('parsing').should('have.value', 'parsing');

      cy.get(`[data-cy="nameEs"]`).type('Quality').should('have.value', 'Quality');

      cy.get(`[data-cy="nameRu"]`).type('Circle transmitting').should('have.value', 'Circle transmitting');

      cy.get(`[data-cy="nameZh"]`).type('Branding Handcrafted protocol').should('have.value', 'Branding Handcrafted protocol');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        requestDescriptions = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', requestDescriptionsPageUrlPattern);
    });
  });
});
