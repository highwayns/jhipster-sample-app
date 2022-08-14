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

describe('AdminImageSizes e2e test', () => {
  const adminImageSizesPageUrl = '/admin-image-sizes';
  const adminImageSizesPageUrlPattern = new RegExp('/admin-image-sizes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminImageSizesSample = { fieldName: 'firewall orange Human', value: 'Berkshire monetize' };

  let adminImageSizes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-image-sizes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-image-sizes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-image-sizes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminImageSizes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-image-sizes/${adminImageSizes.id}`,
      }).then(() => {
        adminImageSizes = undefined;
      });
    }
  });

  it('AdminImageSizes menu should load AdminImageSizes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-image-sizes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminImageSizes').should('exist');
    cy.url().should('match', adminImageSizesPageUrlPattern);
  });

  describe('AdminImageSizes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminImageSizesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminImageSizes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-image-sizes/new$'));
        cy.getEntityCreateUpdateHeading('AdminImageSizes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminImageSizesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-image-sizes',
          body: adminImageSizesSample,
        }).then(({ body }) => {
          adminImageSizes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-image-sizes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-image-sizes?page=0&size=20>; rel="last",<http://localhost/api/admin-image-sizes?page=0&size=20>; rel="first"',
              },
              body: [adminImageSizes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminImageSizesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminImageSizes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminImageSizes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminImageSizesPageUrlPattern);
      });

      it('edit button click should load edit AdminImageSizes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminImageSizes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminImageSizesPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminImageSizes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminImageSizes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminImageSizesPageUrlPattern);

        adminImageSizes = undefined;
      });
    });
  });

  describe('new AdminImageSizes page', () => {
    beforeEach(() => {
      cy.visit(`${adminImageSizesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminImageSizes');
    });

    it('should create an instance of AdminImageSizes', () => {
      cy.get(`[data-cy="fieldName"]`).type('reboot').should('have.value', 'reboot');

      cy.get(`[data-cy="value"]`).type('Tools Metal invoice').should('have.value', 'Tools Metal invoice');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminImageSizes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminImageSizesPageUrlPattern);
    });
  });
});
