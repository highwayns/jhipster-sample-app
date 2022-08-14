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

describe('OrderTypes e2e test', () => {
  const orderTypesPageUrl = '/order-types';
  const orderTypesPageUrlPattern = new RegExp('/order-types(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const orderTypesSample = { nameEn: 'Cotton explicit', nameEs: 'program Plastic', nameRu: 'deposit Customer', nameZh: 'Borders' };

  let orderTypes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/order-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/order-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/order-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (orderTypes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/order-types/${orderTypes.id}`,
      }).then(() => {
        orderTypes = undefined;
      });
    }
  });

  it('OrderTypes menu should load OrderTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('order-types');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OrderTypes').should('exist');
    cy.url().should('match', orderTypesPageUrlPattern);
  });

  describe('OrderTypes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(orderTypesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OrderTypes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/order-types/new$'));
        cy.getEntityCreateUpdateHeading('OrderTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderTypesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/order-types',
          body: orderTypesSample,
        }).then(({ body }) => {
          orderTypes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/order-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/order-types?page=0&size=20>; rel="last",<http://localhost/api/order-types?page=0&size=20>; rel="first"',
              },
              body: [orderTypes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(orderTypesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OrderTypes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('orderTypes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderTypesPageUrlPattern);
      });

      it('edit button click should load edit OrderTypes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OrderTypes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderTypesPageUrlPattern);
      });

      it('last delete button click should delete instance of OrderTypes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('orderTypes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', orderTypesPageUrlPattern);

        orderTypes = undefined;
      });
    });
  });

  describe('new OrderTypes page', () => {
    beforeEach(() => {
      cy.visit(`${orderTypesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OrderTypes');
    });

    it('should create an instance of OrderTypes', () => {
      cy.get(`[data-cy="nameEn"]`).type('web-readiness').should('have.value', 'web-readiness');

      cy.get(`[data-cy="nameEs"]`).type('Organized Handcrafted Swaziland').should('have.value', 'Organized Handcrafted Swaziland');

      cy.get(`[data-cy="nameRu"]`).type('Metal').should('have.value', 'Metal');

      cy.get(`[data-cy="nameZh"]`).type('Tennessee').should('have.value', 'Tennessee');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        orderTypes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', orderTypesPageUrlPattern);
    });
  });
});
