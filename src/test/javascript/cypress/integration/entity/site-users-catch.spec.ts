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

describe('SiteUsersCatch e2e test', () => {
  const siteUsersCatchPageUrl = '/site-users-catch';
  const siteUsersCatchPageUrlPattern = new RegExp('/site-users-catch(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const siteUsersCatchSample = { attempts: 83271 };

  let siteUsersCatch: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/site-users-catches+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/site-users-catches').as('postEntityRequest');
    cy.intercept('DELETE', '/api/site-users-catches/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (siteUsersCatch) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/site-users-catches/${siteUsersCatch.id}`,
      }).then(() => {
        siteUsersCatch = undefined;
      });
    }
  });

  it('SiteUsersCatches menu should load SiteUsersCatches page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('site-users-catch');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SiteUsersCatch').should('exist');
    cy.url().should('match', siteUsersCatchPageUrlPattern);
  });

  describe('SiteUsersCatch page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(siteUsersCatchPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SiteUsersCatch page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/site-users-catch/new$'));
        cy.getEntityCreateUpdateHeading('SiteUsersCatch');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersCatchPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/site-users-catches',
          body: siteUsersCatchSample,
        }).then(({ body }) => {
          siteUsersCatch = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/site-users-catches+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/site-users-catches?page=0&size=20>; rel="last",<http://localhost/api/site-users-catches?page=0&size=20>; rel="first"',
              },
              body: [siteUsersCatch],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(siteUsersCatchPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SiteUsersCatch page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('siteUsersCatch');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersCatchPageUrlPattern);
      });

      it('edit button click should load edit SiteUsersCatch page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SiteUsersCatch');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersCatchPageUrlPattern);
      });

      it('last delete button click should delete instance of SiteUsersCatch', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('siteUsersCatch').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersCatchPageUrlPattern);

        siteUsersCatch = undefined;
      });
    });
  });

  describe('new SiteUsersCatch page', () => {
    beforeEach(() => {
      cy.visit(`${siteUsersCatchPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SiteUsersCatch');
    });

    it('should create an instance of SiteUsersCatch', () => {
      cy.get(`[data-cy="attempts"]`).type('9904').should('have.value', '9904');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        siteUsersCatch = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', siteUsersCatchPageUrlPattern);
    });
  });
});
