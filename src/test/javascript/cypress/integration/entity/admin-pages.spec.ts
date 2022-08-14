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

describe('AdminPages e2e test', () => {
  const adminPagesPageUrl = '/admin-pages';
  const adminPagesPageUrlPattern = new RegExp('/admin-pages(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminPagesSample = {
    fId: 80799,
    name: 'navigating Supervisor',
    url: 'http://nikko.biz',
    icon: 'Wooden Swiss',
    order: 72460,
    pageMapReorders: true,
    oneRecord: 'Y',
  };

  let adminPages: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-pages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-pages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-pages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminPages) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-pages/${adminPages.id}`,
      }).then(() => {
        adminPages = undefined;
      });
    }
  });

  it('AdminPages menu should load AdminPages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-pages');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminPages').should('exist');
    cy.url().should('match', adminPagesPageUrlPattern);
  });

  describe('AdminPages page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminPagesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminPages page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-pages/new$'));
        cy.getEntityCreateUpdateHeading('AdminPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminPagesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-pages',
          body: adminPagesSample,
        }).then(({ body }) => {
          adminPages = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-pages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-pages?page=0&size=20>; rel="last",<http://localhost/api/admin-pages?page=0&size=20>; rel="first"',
              },
              body: [adminPages],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminPagesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminPages page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminPages');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminPagesPageUrlPattern);
      });

      it('edit button click should load edit AdminPages page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminPages');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminPagesPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminPages', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminPages').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminPagesPageUrlPattern);

        adminPages = undefined;
      });
    });
  });

  describe('new AdminPages page', () => {
    beforeEach(() => {
      cy.visit(`${adminPagesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminPages');
    });

    it('should create an instance of AdminPages', () => {
      cy.get(`[data-cy="fId"]`).type('38300').should('have.value', '38300');

      cy.get(`[data-cy="name"]`).type('client-server hacking').should('have.value', 'client-server hacking');

      cy.get(`[data-cy="url"]`).type('http://colt.info').should('have.value', 'http://colt.info');

      cy.get(`[data-cy="icon"]`).type('Supervisor Representative').should('have.value', 'Supervisor Representative');

      cy.get(`[data-cy="order"]`).type('21222').should('have.value', '21222');

      cy.get(`[data-cy="pageMapReorders"]`).should('not.be.checked');
      cy.get(`[data-cy="pageMapReorders"]`).click().should('be.checked');

      cy.get(`[data-cy="oneRecord"]`).select('Y');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminPages = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminPagesPageUrlPattern);
    });
  });
});
