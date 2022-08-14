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

describe('AdminOrder e2e test', () => {
  const adminOrderPageUrl = '/admin-order';
  const adminOrderPageUrlPattern = new RegExp('/admin-order(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminOrderSample = { orderBy: 'Cross-platform Balanced edge', orderAsc: 78525 };

  let adminOrder: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-orders+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-orders').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-orders/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminOrder) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-orders/${adminOrder.id}`,
      }).then(() => {
        adminOrder = undefined;
      });
    }
  });

  it('AdminOrders menu should load AdminOrders page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-order');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminOrder').should('exist');
    cy.url().should('match', adminOrderPageUrlPattern);
  });

  describe('AdminOrder page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminOrderPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminOrder page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-order/new$'));
        cy.getEntityCreateUpdateHeading('AdminOrder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminOrderPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-orders',
          body: adminOrderSample,
        }).then(({ body }) => {
          adminOrder = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-orders+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-orders?page=0&size=20>; rel="last",<http://localhost/api/admin-orders?page=0&size=20>; rel="first"',
              },
              body: [adminOrder],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminOrderPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminOrder page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminOrder');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminOrderPageUrlPattern);
      });

      it('edit button click should load edit AdminOrder page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminOrder');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminOrderPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminOrder', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminOrder').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminOrderPageUrlPattern);

        adminOrder = undefined;
      });
    });
  });

  describe('new AdminOrder page', () => {
    beforeEach(() => {
      cy.visit(`${adminOrderPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminOrder');
    });

    it('should create an instance of AdminOrder', () => {
      cy.get(`[data-cy="orderBy"]`).type('Tasty').should('have.value', 'Tasty');

      cy.get(`[data-cy="orderAsc"]`).type('74198').should('have.value', '74198');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminOrder = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminOrderPageUrlPattern);
    });
  });
});
