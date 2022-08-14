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

describe('Transactions e2e test', () => {
  const transactionsPageUrl = '/transactions';
  const transactionsPageUrlPattern = new RegExp('/transactions(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const transactionsSample = {
    date: '2022-08-14T05:50:59.276Z',
    btc: 29311,
    btcPrice: 15920,
    fiat: 51765,
    fee: 77694,
    fee1: 22929,
    btcNet: 68052,
    btcNet1: 91406,
    btcBefore1: 91528,
    btcAfter1: 65617,
    fiatBefore1: 19513,
    fiatAfter1: 8338,
    btcBefore: 55619,
    btcAfter: 28685,
    fiatBefore: 9821,
    fiatAfter: 62387,
    feeLevel: 61065,
    feeLevel1: 35556,
    origBtcPrice: 11288,
    conversionFee: 63430,
    convertAmount: 68483,
    convertRateGiven: 25103,
    convertSystemRate: 3293,
    conversion: 'N',
    bidAtTransaction: 94511,
    askAtTransaction: 90730,
    factored: 'N',
  };

  let transactions: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/transactions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/transactions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/transactions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (transactions) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/transactions/${transactions.id}`,
      }).then(() => {
        transactions = undefined;
      });
    }
  });

  it('Transactions menu should load Transactions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('transactions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Transactions').should('exist');
    cy.url().should('match', transactionsPageUrlPattern);
  });

  describe('Transactions page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(transactionsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Transactions page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/transactions/new$'));
        cy.getEntityCreateUpdateHeading('Transactions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/transactions',
          body: transactionsSample,
        }).then(({ body }) => {
          transactions = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/transactions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/transactions?page=0&size=20>; rel="last",<http://localhost/api/transactions?page=0&size=20>; rel="first"',
              },
              body: [transactions],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(transactionsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Transactions page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('transactions');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionsPageUrlPattern);
      });

      it('edit button click should load edit Transactions page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Transactions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionsPageUrlPattern);
      });

      it('last delete button click should delete instance of Transactions', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('transactions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', transactionsPageUrlPattern);

        transactions = undefined;
      });
    });
  });

  describe('new Transactions page', () => {
    beforeEach(() => {
      cy.visit(`${transactionsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Transactions');
    });

    it('should create an instance of Transactions', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-13T10:05').should('have.value', '2022-08-13T10:05');

      cy.get(`[data-cy="btc"]`).type('26704').should('have.value', '26704');

      cy.get(`[data-cy="btcPrice"]`).type('99120').should('have.value', '99120');

      cy.get(`[data-cy="fiat"]`).type('68680').should('have.value', '68680');

      cy.get(`[data-cy="fee"]`).type('93860').should('have.value', '93860');

      cy.get(`[data-cy="fee1"]`).type('53124').should('have.value', '53124');

      cy.get(`[data-cy="btcNet"]`).type('69262').should('have.value', '69262');

      cy.get(`[data-cy="btcNet1"]`).type('88560').should('have.value', '88560');

      cy.get(`[data-cy="btcBefore1"]`).type('91080').should('have.value', '91080');

      cy.get(`[data-cy="btcAfter1"]`).type('30329').should('have.value', '30329');

      cy.get(`[data-cy="fiatBefore1"]`).type('51189').should('have.value', '51189');

      cy.get(`[data-cy="fiatAfter1"]`).type('69745').should('have.value', '69745');

      cy.get(`[data-cy="btcBefore"]`).type('45567').should('have.value', '45567');

      cy.get(`[data-cy="btcAfter"]`).type('55966').should('have.value', '55966');

      cy.get(`[data-cy="fiatBefore"]`).type('50654').should('have.value', '50654');

      cy.get(`[data-cy="fiatAfter"]`).type('32993').should('have.value', '32993');

      cy.get(`[data-cy="feeLevel"]`).type('67430').should('have.value', '67430');

      cy.get(`[data-cy="feeLevel1"]`).type('90431').should('have.value', '90431');

      cy.get(`[data-cy="origBtcPrice"]`).type('59349').should('have.value', '59349');

      cy.get(`[data-cy="conversionFee"]`).type('87515').should('have.value', '87515');

      cy.get(`[data-cy="convertAmount"]`).type('2522').should('have.value', '2522');

      cy.get(`[data-cy="convertRateGiven"]`).type('40498').should('have.value', '40498');

      cy.get(`[data-cy="convertSystemRate"]`).type('51014').should('have.value', '51014');

      cy.get(`[data-cy="conversion"]`).select('Y');

      cy.get(`[data-cy="bidAtTransaction"]`).type('370').should('have.value', '370');

      cy.get(`[data-cy="askAtTransaction"]`).type('67381').should('have.value', '67381');

      cy.get(`[data-cy="factored"]`).select('N');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        transactions = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', transactionsPageUrlPattern);
    });
  });
});
