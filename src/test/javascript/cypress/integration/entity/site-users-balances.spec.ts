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

describe('SiteUsersBalances e2e test', () => {
  const siteUsersBalancesPageUrl = '/site-users-balances';
  const siteUsersBalancesPageUrlPattern = new RegExp('/site-users-balances(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const siteUsersBalancesSample = { balance: 40450 };

  let siteUsersBalances: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/site-users-balances+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/site-users-balances').as('postEntityRequest');
    cy.intercept('DELETE', '/api/site-users-balances/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (siteUsersBalances) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/site-users-balances/${siteUsersBalances.id}`,
      }).then(() => {
        siteUsersBalances = undefined;
      });
    }
  });

  it('SiteUsersBalances menu should load SiteUsersBalances page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('site-users-balances');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SiteUsersBalances').should('exist');
    cy.url().should('match', siteUsersBalancesPageUrlPattern);
  });

  describe('SiteUsersBalances page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(siteUsersBalancesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SiteUsersBalances page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/site-users-balances/new$'));
        cy.getEntityCreateUpdateHeading('SiteUsersBalances');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersBalancesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/site-users-balances',
          body: siteUsersBalancesSample,
        }).then(({ body }) => {
          siteUsersBalances = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/site-users-balances+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/site-users-balances?page=0&size=20>; rel="last",<http://localhost/api/site-users-balances?page=0&size=20>; rel="first"',
              },
              body: [siteUsersBalances],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(siteUsersBalancesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SiteUsersBalances page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('siteUsersBalances');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersBalancesPageUrlPattern);
      });

      it('edit button click should load edit SiteUsersBalances page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SiteUsersBalances');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersBalancesPageUrlPattern);
      });

      it('last delete button click should delete instance of SiteUsersBalances', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('siteUsersBalances').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersBalancesPageUrlPattern);

        siteUsersBalances = undefined;
      });
    });
  });

  describe('new SiteUsersBalances page', () => {
    beforeEach(() => {
      cy.visit(`${siteUsersBalancesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SiteUsersBalances');
    });

    it('should create an instance of SiteUsersBalances', () => {
      cy.get(`[data-cy="balance"]`).type('7915').should('have.value', '7915');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        siteUsersBalances = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', siteUsersBalancesPageUrlPattern);
    });
  });
});
