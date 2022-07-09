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

describe('CardTokenData e2e test', () => {
  const cardTokenDataPageUrl = '/card-token-data';
  const cardTokenDataPageUrlPattern = new RegExp('/card-token-data(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cardTokenDataSample = {};

  let cardTokenData: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/card-token-data+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/card-token-data').as('postEntityRequest');
    cy.intercept('DELETE', '/api/card-token-data/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cardTokenData) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/card-token-data/${cardTokenData.id}`,
      }).then(() => {
        cardTokenData = undefined;
      });
    }
  });

  it('CardTokenData menu should load CardTokenData page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('card-token-data');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CardTokenData').should('exist');
    cy.url().should('match', cardTokenDataPageUrlPattern);
  });

  describe('CardTokenData page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cardTokenDataPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CardTokenData page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/card-token-data/new$'));
        cy.getEntityCreateUpdateHeading('CardTokenData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cardTokenDataPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/card-token-data',
          body: cardTokenDataSample,
        }).then(({ body }) => {
          cardTokenData = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/card-token-data+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [cardTokenData],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(cardTokenDataPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CardTokenData page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cardTokenData');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cardTokenDataPageUrlPattern);
      });

      it('edit button click should load edit CardTokenData page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CardTokenData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cardTokenDataPageUrlPattern);
      });

      it('last delete button click should delete instance of CardTokenData', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cardTokenData').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', cardTokenDataPageUrlPattern);

        cardTokenData = undefined;
      });
    });
  });

  describe('new CardTokenData page', () => {
    beforeEach(() => {
      cy.visit(`${cardTokenDataPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CardTokenData');
    });

    it('should create an instance of CardTokenData', () => {
      cy.get(`[data-cy="token"]`).type('Cotton Ball best-of-breed').should('have.value', 'Cotton Ball best-of-breed');

      cy.get(`[data-cy="cardExpiryMonth"]`).type('compressing Credit').should('have.value', 'compressing Credit');

      cy.get(`[data-cy="cardExpiryYear"]`).type('methodologies lime Investor').should('have.value', 'methodologies lime Investor');

      cy.get(`[data-cy="issuerReturnCode"]`).type('Islands').should('have.value', 'Islands');

      cy.get(`[data-cy="truncatedCardNumber"]`).type('exploit').should('have.value', 'exploit');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        cardTokenData = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', cardTokenDataPageUrlPattern);
    });
  });
});
