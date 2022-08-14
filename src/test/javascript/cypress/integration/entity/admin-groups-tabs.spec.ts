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

describe('AdminGroupsTabs e2e test', () => {
  const adminGroupsTabsPageUrl = '/admin-groups-tabs';
  const adminGroupsTabsPageUrlPattern = new RegExp('/admin-groups-tabs(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminGroupsTabsSample = { permission: true };

  let adminGroupsTabs: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-groups-tabs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-groups-tabs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-groups-tabs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminGroupsTabs) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-groups-tabs/${adminGroupsTabs.id}`,
      }).then(() => {
        adminGroupsTabs = undefined;
      });
    }
  });

  it('AdminGroupsTabs menu should load AdminGroupsTabs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-groups-tabs');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminGroupsTabs').should('exist');
    cy.url().should('match', adminGroupsTabsPageUrlPattern);
  });

  describe('AdminGroupsTabs page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminGroupsTabsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminGroupsTabs page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-groups-tabs/new$'));
        cy.getEntityCreateUpdateHeading('AdminGroupsTabs');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsTabsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-groups-tabs',
          body: adminGroupsTabsSample,
        }).then(({ body }) => {
          adminGroupsTabs = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-groups-tabs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-groups-tabs?page=0&size=20>; rel="last",<http://localhost/api/admin-groups-tabs?page=0&size=20>; rel="first"',
              },
              body: [adminGroupsTabs],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminGroupsTabsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminGroupsTabs page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminGroupsTabs');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsTabsPageUrlPattern);
      });

      it('edit button click should load edit AdminGroupsTabs page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminGroupsTabs');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsTabsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminGroupsTabs', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminGroupsTabs').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsTabsPageUrlPattern);

        adminGroupsTabs = undefined;
      });
    });
  });

  describe('new AdminGroupsTabs page', () => {
    beforeEach(() => {
      cy.visit(`${adminGroupsTabsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminGroupsTabs');
    });

    it('should create an instance of AdminGroupsTabs', () => {
      cy.get(`[data-cy="permission"]`).should('not.be.checked');
      cy.get(`[data-cy="permission"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminGroupsTabs = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminGroupsTabsPageUrlPattern);
    });
  });
});
