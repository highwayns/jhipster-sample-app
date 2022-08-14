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

describe('AdminControlsMethods e2e test', () => {
  const adminControlsMethodsPageUrl = '/admin-controls-methods';
  const adminControlsMethodsPageUrlPattern = new RegExp('/admin-controls-methods(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminControlsMethodsSample = { argument: 'Shoals initiative', order: 52689, pId: 83159 };

  let adminControlsMethods: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-controls-methods+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-controls-methods').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-controls-methods/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminControlsMethods) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-controls-methods/${adminControlsMethods.id}`,
      }).then(() => {
        adminControlsMethods = undefined;
      });
    }
  });

  it('AdminControlsMethods menu should load AdminControlsMethods page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-controls-methods');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminControlsMethods').should('exist');
    cy.url().should('match', adminControlsMethodsPageUrlPattern);
  });

  describe('AdminControlsMethods page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminControlsMethodsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminControlsMethods page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-controls-methods/new$'));
        cy.getEntityCreateUpdateHeading('AdminControlsMethods');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsMethodsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-controls-methods',
          body: adminControlsMethodsSample,
        }).then(({ body }) => {
          adminControlsMethods = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-controls-methods+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-controls-methods?page=0&size=20>; rel="last",<http://localhost/api/admin-controls-methods?page=0&size=20>; rel="first"',
              },
              body: [adminControlsMethods],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminControlsMethodsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminControlsMethods page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminControlsMethods');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsMethodsPageUrlPattern);
      });

      it('edit button click should load edit AdminControlsMethods page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminControlsMethods');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsMethodsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminControlsMethods', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminControlsMethods').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsMethodsPageUrlPattern);

        adminControlsMethods = undefined;
      });
    });
  });

  describe('new AdminControlsMethods page', () => {
    beforeEach(() => {
      cy.visit(`${adminControlsMethodsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminControlsMethods');
    });

    it('should create an instance of AdminControlsMethods', () => {
      cy.get(`[data-cy="method"]`).type('Pants Market').should('have.value', 'Pants Market');

      cy.get(`[data-cy="argument"]`).type('Kazakhstan policy').should('have.value', 'Kazakhstan policy');

      cy.get(`[data-cy="order"]`).type('65372').should('have.value', '65372');

      cy.get(`[data-cy="pId"]`).type('76677').should('have.value', '76677');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminControlsMethods = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminControlsMethodsPageUrlPattern);
    });
  });
});
