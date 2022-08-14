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

describe('AdminUsers e2e test', () => {
  const adminUsersPageUrl = '/admin-users';
  const adminUsersPageUrlPattern = new RegExp('/admin-users(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminUsersSample = {
    user: 'Rufiyaa Tasty',
    pass: 'Account turquoise',
    firstName: 'Deborah',
    lastName: 'Murazik',
    company: 'SMTP Regional',
    address: 'Enterprise-wide Steel',
    city: 'South Lilyanbury',
    phone: '1-390-928-5122 x46289',
    email: 'Jada_Watsica@hotmail.com',
    website: 'tan didactic paradigms',
    fId: 51903,
    order: 79667,
    isAdmin: 'Y',
    countryCode: 90928,
    verifiedAuthy: 'N',
    authyId: 'invoice South magenta',
  };

  let adminUsers: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-users').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminUsers) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-users/${adminUsers.id}`,
      }).then(() => {
        adminUsers = undefined;
      });
    }
  });

  it('AdminUsers menu should load AdminUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-users');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminUsers').should('exist');
    cy.url().should('match', adminUsersPageUrlPattern);
  });

  describe('AdminUsers page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminUsersPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminUsers page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-users/new$'));
        cy.getEntityCreateUpdateHeading('AdminUsers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminUsersPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-users',
          body: adminUsersSample,
        }).then(({ body }) => {
          adminUsers = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-users?page=0&size=20>; rel="last",<http://localhost/api/admin-users?page=0&size=20>; rel="first"',
              },
              body: [adminUsers],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminUsersPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminUsers page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminUsers');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminUsersPageUrlPattern);
      });

      it('edit button click should load edit AdminUsers page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminUsers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminUsersPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminUsers', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminUsers').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminUsersPageUrlPattern);

        adminUsers = undefined;
      });
    });
  });

  describe('new AdminUsers page', () => {
    beforeEach(() => {
      cy.visit(`${adminUsersPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminUsers');
    });

    it('should create an instance of AdminUsers', () => {
      cy.get(`[data-cy="user"]`).type('Administrator Fish Villages').should('have.value', 'Administrator Fish Villages');

      cy.get(`[data-cy="pass"]`).type('communities').should('have.value', 'communities');

      cy.get(`[data-cy="firstName"]`).type('Urban').should('have.value', 'Urban');

      cy.get(`[data-cy="lastName"]`).type('Hand').should('have.value', 'Hand');

      cy.get(`[data-cy="company"]`).type('users Planner').should('have.value', 'users Planner');

      cy.get(`[data-cy="address"]`).type('Wooden intermediate budgetary').should('have.value', 'Wooden intermediate budgetary');

      cy.get(`[data-cy="city"]`).type('Welchtown').should('have.value', 'Welchtown');

      cy.get(`[data-cy="phone"]`).type('1-829-563-4407').should('have.value', '1-829-563-4407');

      cy.get(`[data-cy="email"]`).type('Aletha_Little45@gmail.com').should('have.value', 'Aletha_Little45@gmail.com');

      cy.get(`[data-cy="website"]`).type('Central invoice').should('have.value', 'Central invoice');

      cy.get(`[data-cy="fId"]`).type('83121').should('have.value', '83121');

      cy.get(`[data-cy="order"]`).type('38199').should('have.value', '38199');

      cy.get(`[data-cy="isAdmin"]`).select('N');

      cy.get(`[data-cy="countryCode"]`).type('51962').should('have.value', '51962');

      cy.get(`[data-cy="verifiedAuthy"]`).select('Y');

      cy.get(`[data-cy="authyId"]`).type('Computers Executive Silver').should('have.value', 'Computers Executive Silver');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminUsers = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminUsersPageUrlPattern);
    });
  });
});
