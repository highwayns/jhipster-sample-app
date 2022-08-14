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

describe('SiteUsersAccess e2e test', () => {
  const siteUsersAccessPageUrl = '/site-users-access';
  const siteUsersAccessPageUrlPattern = new RegExp('/site-users-access(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const siteUsersAccessSample = { start: 42983, last: 19191, attempts: 88870 };

  let siteUsersAccess: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/site-users-accesses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/site-users-accesses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/site-users-accesses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (siteUsersAccess) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/site-users-accesses/${siteUsersAccess.id}`,
      }).then(() => {
        siteUsersAccess = undefined;
      });
    }
  });

  it('SiteUsersAccesses menu should load SiteUsersAccesses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('site-users-access');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SiteUsersAccess').should('exist');
    cy.url().should('match', siteUsersAccessPageUrlPattern);
  });

  describe('SiteUsersAccess page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(siteUsersAccessPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SiteUsersAccess page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/site-users-access/new$'));
        cy.getEntityCreateUpdateHeading('SiteUsersAccess');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersAccessPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/site-users-accesses',
          body: siteUsersAccessSample,
        }).then(({ body }) => {
          siteUsersAccess = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/site-users-accesses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/site-users-accesses?page=0&size=20>; rel="last",<http://localhost/api/site-users-accesses?page=0&size=20>; rel="first"',
              },
              body: [siteUsersAccess],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(siteUsersAccessPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SiteUsersAccess page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('siteUsersAccess');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersAccessPageUrlPattern);
      });

      it('edit button click should load edit SiteUsersAccess page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SiteUsersAccess');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersAccessPageUrlPattern);
      });

      it('last delete button click should delete instance of SiteUsersAccess', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('siteUsersAccess').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersAccessPageUrlPattern);

        siteUsersAccess = undefined;
      });
    });
  });

  describe('new SiteUsersAccess page', () => {
    beforeEach(() => {
      cy.visit(`${siteUsersAccessPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SiteUsersAccess');
    });

    it('should create an instance of SiteUsersAccess', () => {
      cy.get(`[data-cy="start"]`).type('46115').should('have.value', '46115');

      cy.get(`[data-cy="last"]`).type('38150').should('have.value', '38150');

      cy.get(`[data-cy="attempts"]`).type('70360').should('have.value', '70360');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        siteUsersAccess = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', siteUsersAccessPageUrlPattern);
    });
  });
});
