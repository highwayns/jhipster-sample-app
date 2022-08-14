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

describe('BankAccounts e2e test', () => {
  const bankAccountsPageUrl = '/bank-accounts';
  const bankAccountsPageUrlPattern = new RegExp('/bank-accounts(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bankAccountsSample = { accountNumber: 11725, description: 'Re-contextualized clicks-and-mortar National' };

  let bankAccounts: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/bank-accounts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/bank-accounts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/bank-accounts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bankAccounts) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/bank-accounts/${bankAccounts.id}`,
      }).then(() => {
        bankAccounts = undefined;
      });
    }
  });

  it('BankAccounts menu should load BankAccounts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('bank-accounts');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BankAccounts').should('exist');
    cy.url().should('match', bankAccountsPageUrlPattern);
  });

  describe('BankAccounts page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bankAccountsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BankAccounts page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/bank-accounts/new$'));
        cy.getEntityCreateUpdateHeading('BankAccounts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bankAccountsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/bank-accounts',
          body: bankAccountsSample,
        }).then(({ body }) => {
          bankAccounts = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/bank-accounts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/bank-accounts?page=0&size=20>; rel="last",<http://localhost/api/bank-accounts?page=0&size=20>; rel="first"',
              },
              body: [bankAccounts],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bankAccountsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BankAccounts page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bankAccounts');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bankAccountsPageUrlPattern);
      });

      it('edit button click should load edit BankAccounts page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BankAccounts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bankAccountsPageUrlPattern);
      });

      it('last delete button click should delete instance of BankAccounts', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bankAccounts').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bankAccountsPageUrlPattern);

        bankAccounts = undefined;
      });
    });
  });

  describe('new BankAccounts page', () => {
    beforeEach(() => {
      cy.visit(`${bankAccountsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BankAccounts');
    });

    it('should create an instance of BankAccounts', () => {
      cy.get(`[data-cy="accountNumber"]`).type('92528').should('have.value', '92528');

      cy.get(`[data-cy="description"]`).type('Associate RAM Overpass').should('have.value', 'Associate RAM Overpass');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        bankAccounts = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', bankAccountsPageUrlPattern);
    });
  });
});
