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

describe('AdminGroupsPages e2e test', () => {
  const adminGroupsPagesPageUrl = '/admin-groups-pages';
  const adminGroupsPagesPageUrlPattern = new RegExp('/admin-groups-pages(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminGroupsPagesSample = { permission: true };

  let adminGroupsPages: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-groups-pages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-groups-pages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-groups-pages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminGroupsPages) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-groups-pages/${adminGroupsPages.id}`,
      }).then(() => {
        adminGroupsPages = undefined;
      });
    }
  });

  it('AdminGroupsPages menu should load AdminGroupsPages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-groups-pages');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminGroupsPages').should('exist');
    cy.url().should('match', adminGroupsPagesPageUrlPattern);
  });

  describe('AdminGroupsPages page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminGroupsPagesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminGroupsPages page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-groups-pages/new$'));
        cy.getEntityCreateUpdateHeading('AdminGroupsPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPagesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-groups-pages',
          body: adminGroupsPagesSample,
        }).then(({ body }) => {
          adminGroupsPages = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-groups-pages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-groups-pages?page=0&size=20>; rel="last",<http://localhost/api/admin-groups-pages?page=0&size=20>; rel="first"',
              },
              body: [adminGroupsPages],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminGroupsPagesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminGroupsPages page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminGroupsPages');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPagesPageUrlPattern);
      });

      it('edit button click should load edit AdminGroupsPages page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminGroupsPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPagesPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminGroupsPages', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminGroupsPages').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPagesPageUrlPattern);

        adminGroupsPages = undefined;
      });
    });
  });

  describe('new AdminGroupsPages page', () => {
    beforeEach(() => {
      cy.visit(`${adminGroupsPagesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminGroupsPages');
    });

    it('should create an instance of AdminGroupsPages', () => {
      cy.get(`[data-cy="permission"]`).should('not.be.checked');
      cy.get(`[data-cy="permission"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminGroupsPages = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminGroupsPagesPageUrlPattern);
    });
  });
});
