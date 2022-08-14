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

describe('CurrentStats e2e test', () => {
  const currentStatsPageUrl = '/current-stats';
  const currentStatsPageUrlPattern = new RegExp('/current-stats(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currentStatsSample = { totalBtc: 53329, marketCap: 43969, tradeVolume: 35753 };

  let currentStats: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/current-stats+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/current-stats').as('postEntityRequest');
    cy.intercept('DELETE', '/api/current-stats/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (currentStats) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/current-stats/${currentStats.id}`,
      }).then(() => {
        currentStats = undefined;
      });
    }
  });

  it('CurrentStats menu should load CurrentStats page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('current-stats');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CurrentStats').should('exist');
    cy.url().should('match', currentStatsPageUrlPattern);
  });

  describe('CurrentStats page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currentStatsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CurrentStats page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/current-stats/new$'));
        cy.getEntityCreateUpdateHeading('CurrentStats');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currentStatsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/current-stats',
          body: currentStatsSample,
        }).then(({ body }) => {
          currentStats = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/current-stats+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/current-stats?page=0&size=20>; rel="last",<http://localhost/api/current-stats?page=0&size=20>; rel="first"',
              },
              body: [currentStats],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currentStatsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CurrentStats page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('currentStats');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currentStatsPageUrlPattern);
      });

      it('edit button click should load edit CurrentStats page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CurrentStats');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currentStatsPageUrlPattern);
      });

      it('last delete button click should delete instance of CurrentStats', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('currentStats').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currentStatsPageUrlPattern);

        currentStats = undefined;
      });
    });
  });

  describe('new CurrentStats page', () => {
    beforeEach(() => {
      cy.visit(`${currentStatsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CurrentStats');
    });

    it('should create an instance of CurrentStats', () => {
      cy.get(`[data-cy="totalBtc"]`).type('97751').should('have.value', '97751');

      cy.get(`[data-cy="marketCap"]`).type('43502').should('have.value', '43502');

      cy.get(`[data-cy="tradeVolume"]`).type('48593').should('have.value', '48593');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        currentStats = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', currentStatsPageUrlPattern);
    });
  });
});
