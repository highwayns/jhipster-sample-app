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

describe('Currencys e2e test', () => {
  const currencysPageUrl = '/currencys';
  const currencysPageUrlPattern = new RegExp('/currencys(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currencysSample = {};

  let currencys: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/currencys+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/currencys').as('postEntityRequest');
    cy.intercept('DELETE', '/api/currencys/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (currencys) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/currencys/${currencys.id}`,
      }).then(() => {
        currencys = undefined;
      });
    }
  });

  it('Currencys menu should load Currencys page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('currencys');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Currencys').should('exist');
    cy.url().should('match', currencysPageUrlPattern);
  });

  describe('Currencys page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currencysPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Currencys page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/currencys/new$'));
        cy.getEntityCreateUpdateHeading('Currencys');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currencysPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/currencys',
          body: currencysSample,
        }).then(({ body }) => {
          currencys = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/currencys+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [currencys],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currencysPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Currencys page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('currencys');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currencysPageUrlPattern);
      });

      it('edit button click should load edit Currencys page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Currencys');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currencysPageUrlPattern);
      });

      it('last delete button click should delete instance of Currencys', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('currencys').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currencysPageUrlPattern);

        currencys = undefined;
      });
    });
  });

  describe('new Currencys page', () => {
    beforeEach(() => {
      cy.visit(`${currencysPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Currencys');
    });

    it('should create an instance of Currencys', () => {
      cy.get(`[data-cy="currency"]`).select('CNY');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        currencys = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', currencysPageUrlPattern);
    });
  });
});
