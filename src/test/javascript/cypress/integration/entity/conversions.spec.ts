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

describe('Conversions e2e test', () => {
  const conversionsPageUrl = '/conversions';
  const conversionsPageUrlPattern = new RegExp('/conversions(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const conversionsSample = {
    amount: 32579,
    date: '2022-08-13T17:52:40.551Z',
    isActive: 'N',
    totalWithdrawals: 3844,
    profitToFactor: 43834,
    factored: 'N',
    date1: '2022-08-13T12:12:35.993Z',
  };

  let conversions: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/conversions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/conversions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/conversions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (conversions) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/conversions/${conversions.id}`,
      }).then(() => {
        conversions = undefined;
      });
    }
  });

  it('Conversions menu should load Conversions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('conversions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Conversions').should('exist');
    cy.url().should('match', conversionsPageUrlPattern);
  });

  describe('Conversions page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(conversionsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Conversions page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/conversions/new$'));
        cy.getEntityCreateUpdateHeading('Conversions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', conversionsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/conversions',
          body: conversionsSample,
        }).then(({ body }) => {
          conversions = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/conversions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/conversions?page=0&size=20>; rel="last",<http://localhost/api/conversions?page=0&size=20>; rel="first"',
              },
              body: [conversions],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(conversionsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Conversions page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('conversions');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', conversionsPageUrlPattern);
      });

      it('edit button click should load edit Conversions page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Conversions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', conversionsPageUrlPattern);
      });

      it('last delete button click should delete instance of Conversions', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('conversions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', conversionsPageUrlPattern);

        conversions = undefined;
      });
    });
  });

  describe('new Conversions page', () => {
    beforeEach(() => {
      cy.visit(`${conversionsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Conversions');
    });

    it('should create an instance of Conversions', () => {
      cy.get(`[data-cy="amount"]`).type('82356').should('have.value', '82356');

      cy.get(`[data-cy="date"]`).type('2022-08-13T14:08').should('have.value', '2022-08-13T14:08');

      cy.get(`[data-cy="isActive"]`).select('N');

      cy.get(`[data-cy="totalWithdrawals"]`).type('84152').should('have.value', '84152');

      cy.get(`[data-cy="profitToFactor"]`).type('56471').should('have.value', '56471');

      cy.get(`[data-cy="factored"]`).select('Y');

      cy.get(`[data-cy="date1"]`).type('2022-08-14T02:22').should('have.value', '2022-08-14T02:22');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        conversions = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', conversionsPageUrlPattern);
    });
  });
});
