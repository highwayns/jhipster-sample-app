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

describe('AdminTabs e2e test', () => {
  const adminTabsPageUrl = '/admin-tabs';
  const adminTabsPageUrlPattern = new RegExp('/admin-tabs(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminTabsSample = {
    name: 'Metal capacitor black',
    order: 71733,
    icon: 'solid',
    url: 'http://haleigh.org',
    hidden: 'N',
    isCtrlPanel: 'Y',
    forGroup: 24116,
    oneRecord: 'Y',
  };

  let adminTabs: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-tabs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-tabs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-tabs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminTabs) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-tabs/${adminTabs.id}`,
      }).then(() => {
        adminTabs = undefined;
      });
    }
  });

  it('AdminTabs menu should load AdminTabs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-tabs');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminTabs').should('exist');
    cy.url().should('match', adminTabsPageUrlPattern);
  });

  describe('AdminTabs page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminTabsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminTabs page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-tabs/new$'));
        cy.getEntityCreateUpdateHeading('AdminTabs');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminTabsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-tabs',
          body: adminTabsSample,
        }).then(({ body }) => {
          adminTabs = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-tabs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-tabs?page=0&size=20>; rel="last",<http://localhost/api/admin-tabs?page=0&size=20>; rel="first"',
              },
              body: [adminTabs],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminTabsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminTabs page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminTabs');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminTabsPageUrlPattern);
      });

      it('edit button click should load edit AdminTabs page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminTabs');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminTabsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminTabs', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminTabs').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminTabsPageUrlPattern);

        adminTabs = undefined;
      });
    });
  });

  describe('new AdminTabs page', () => {
    beforeEach(() => {
      cy.visit(`${adminTabsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminTabs');
    });

    it('should create an instance of AdminTabs', () => {
      cy.get(`[data-cy="name"]`).type('plum').should('have.value', 'plum');

      cy.get(`[data-cy="order"]`).type('10956').should('have.value', '10956');

      cy.get(`[data-cy="icon"]`).type('Operations').should('have.value', 'Operations');

      cy.get(`[data-cy="url"]`).type('https://alena.net').should('have.value', 'https://alena.net');

      cy.get(`[data-cy="hidden"]`).select('N');

      cy.get(`[data-cy="isCtrlPanel"]`).select('Y');

      cy.get(`[data-cy="forGroup"]`).type('26333').should('have.value', '26333');

      cy.get(`[data-cy="oneRecord"]`).select('Y');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminTabs = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminTabsPageUrlPattern);
    });
  });
});
