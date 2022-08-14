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

describe('Currencies e2e test', () => {
  const currenciesPageUrl = '/currencies';
  const currenciesPageUrlPattern = new RegExp('/currencies(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const currenciesSample = {
    currency: 'Acc',
    faSymbol: 'driver',
    accountNumber: 14184,
    accountName: 'Personal Loan Account',
    isActive: 'N',
    usdBid: 'online',
    usdAsk: 'Branding',
    nameEn: 'Handcrafted',
    nameEs: 'cultivate Cheese',
    nameRu: 'Buckinghamshire North',
    nameZh: 'copying Tala morph',
  };

  let currencies: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/currencies+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/currencies').as('postEntityRequest');
    cy.intercept('DELETE', '/api/currencies/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (currencies) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/currencies/${currencies.id}`,
      }).then(() => {
        currencies = undefined;
      });
    }
  });

  it('Currencies menu should load Currencies page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('currencies');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Currencies').should('exist');
    cy.url().should('match', currenciesPageUrlPattern);
  });

  describe('Currencies page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(currenciesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Currencies page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/currencies/new$'));
        cy.getEntityCreateUpdateHeading('Currencies');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currenciesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/currencies',
          body: currenciesSample,
        }).then(({ body }) => {
          currencies = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/currencies+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/currencies?page=0&size=20>; rel="last",<http://localhost/api/currencies?page=0&size=20>; rel="first"',
              },
              body: [currencies],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(currenciesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Currencies page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('currencies');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currenciesPageUrlPattern);
      });

      it('edit button click should load edit Currencies page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Currencies');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currenciesPageUrlPattern);
      });

      it('last delete button click should delete instance of Currencies', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('currencies').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', currenciesPageUrlPattern);

        currencies = undefined;
      });
    });
  });

  describe('new Currencies page', () => {
    beforeEach(() => {
      cy.visit(`${currenciesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Currencies');
    });

    it('should create an instance of Currencies', () => {
      cy.get(`[data-cy="currency"]`).type('par').should('have.value', 'par');

      cy.get(`[data-cy="faSymbol"]`).type('evolve').should('have.value', 'evolve');

      cy.get(`[data-cy="accountNumber"]`).type('29935').should('have.value', '29935');

      cy.get(`[data-cy="accountName"]`).type('Auto Loan Account').should('have.value', 'Auto Loan Account');

      cy.get(`[data-cy="isActive"]`).select('N');

      cy.get(`[data-cy="usdBid"]`).type('Buckinghamshire Integrated Car').should('have.value', 'Buckinghamshire Integrated Car');

      cy.get(`[data-cy="usdAsk"]`).type('Phased').should('have.value', 'Phased');

      cy.get(`[data-cy="nameEn"]`).type('Germany Mississippi').should('have.value', 'Germany Mississippi');

      cy.get(`[data-cy="nameEs"]`).type('firewall virtual Wooden').should('have.value', 'firewall virtual Wooden');

      cy.get(`[data-cy="nameRu"]`).type('Tasty').should('have.value', 'Tasty');

      cy.get(`[data-cy="nameZh"]`).type('indexing Unbranded copying').should('have.value', 'indexing Unbranded copying');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        currencies = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', currenciesPageUrlPattern);
    });
  });
});
