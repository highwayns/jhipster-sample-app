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

describe('BitcoinAddresses e2e test', () => {
  const bitcoinAddressesPageUrl = '/bitcoin-addresses';
  const bitcoinAddressesPageUrlPattern = new RegExp('/bitcoin-addresses(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bitcoinAddressesSample = {
    address: 'Cambridgeshire grid-enabled',
    date: '2022-08-13T16:09:13.837Z',
    systemAddress: 'N',
    hotWallet: 'N',
    warmWallet: 'N',
  };

  let bitcoinAddresses: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/bitcoin-addresses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/bitcoin-addresses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/bitcoin-addresses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bitcoinAddresses) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/bitcoin-addresses/${bitcoinAddresses.id}`,
      }).then(() => {
        bitcoinAddresses = undefined;
      });
    }
  });

  it('BitcoinAddresses menu should load BitcoinAddresses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('bitcoin-addresses');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BitcoinAddresses').should('exist');
    cy.url().should('match', bitcoinAddressesPageUrlPattern);
  });

  describe('BitcoinAddresses page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bitcoinAddressesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BitcoinAddresses page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/bitcoin-addresses/new$'));
        cy.getEntityCreateUpdateHeading('BitcoinAddresses');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoinAddressesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/bitcoin-addresses',
          body: bitcoinAddressesSample,
        }).then(({ body }) => {
          bitcoinAddresses = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/bitcoin-addresses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/bitcoin-addresses?page=0&size=20>; rel="last",<http://localhost/api/bitcoin-addresses?page=0&size=20>; rel="first"',
              },
              body: [bitcoinAddresses],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bitcoinAddressesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BitcoinAddresses page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bitcoinAddresses');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoinAddressesPageUrlPattern);
      });

      it('edit button click should load edit BitcoinAddresses page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BitcoinAddresses');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoinAddressesPageUrlPattern);
      });

      it('last delete button click should delete instance of BitcoinAddresses', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bitcoinAddresses').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', bitcoinAddressesPageUrlPattern);

        bitcoinAddresses = undefined;
      });
    });
  });

  describe('new BitcoinAddresses page', () => {
    beforeEach(() => {
      cy.visit(`${bitcoinAddressesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BitcoinAddresses');
    });

    it('should create an instance of BitcoinAddresses', () => {
      cy.get(`[data-cy="address"]`).type('EXE').should('have.value', 'EXE');

      cy.get(`[data-cy="date"]`).type('2022-08-13T22:40').should('have.value', '2022-08-13T22:40');

      cy.get(`[data-cy="systemAddress"]`).select('N');

      cy.get(`[data-cy="hotWallet"]`).select('Y');

      cy.get(`[data-cy="warmWallet"]`).select('Y');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        bitcoinAddresses = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', bitcoinAddressesPageUrlPattern);
    });
  });
});
