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

describe('AdminGroups e2e test', () => {
  const adminGroupsPageUrl = '/admin-groups';
  const adminGroupsPageUrlPattern = new RegExp('/admin-groups(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminGroupsSample = { name: 'composite', order: 96078 };

  let adminGroups: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-groups+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-groups').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-groups/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminGroups) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-groups/${adminGroups.id}`,
      }).then(() => {
        adminGroups = undefined;
      });
    }
  });

  it('AdminGroups menu should load AdminGroups page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-groups');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminGroups').should('exist');
    cy.url().should('match', adminGroupsPageUrlPattern);
  });

  describe('AdminGroups page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminGroupsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminGroups page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-groups/new$'));
        cy.getEntityCreateUpdateHeading('AdminGroups');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-groups',
          body: adminGroupsSample,
        }).then(({ body }) => {
          adminGroups = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-groups+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-groups?page=0&size=20>; rel="last",<http://localhost/api/admin-groups?page=0&size=20>; rel="first"',
              },
              body: [adminGroups],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminGroupsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminGroups page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminGroups');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPageUrlPattern);
      });

      it('edit button click should load edit AdminGroups page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminGroups');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminGroups', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminGroups').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminGroupsPageUrlPattern);

        adminGroups = undefined;
      });
    });
  });

  describe('new AdminGroups page', () => {
    beforeEach(() => {
      cy.visit(`${adminGroupsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminGroups');
    });

    it('should create an instance of AdminGroups', () => {
      cy.get(`[data-cy="name"]`).type('Persevering Dinar').should('have.value', 'Persevering Dinar');

      cy.get(`[data-cy="order"]`).type('94604').should('have.value', '94604');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminGroups = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminGroupsPageUrlPattern);
    });
  });
});
