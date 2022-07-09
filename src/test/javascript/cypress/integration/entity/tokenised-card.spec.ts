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

describe('TokenisedCard e2e test', () => {
  const tokenisedCardPageUrl = '/tokenised-card';
  const tokenisedCardPageUrlPattern = new RegExp('/tokenised-card(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const tokenisedCardSample = {};

  let tokenisedCard: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tokenised-cards+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tokenised-cards').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tokenised-cards/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (tokenisedCard) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tokenised-cards/${tokenisedCard.id}`,
      }).then(() => {
        tokenisedCard = undefined;
      });
    }
  });

  it('TokenisedCards menu should load TokenisedCards page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('tokenised-card');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TokenisedCard').should('exist');
    cy.url().should('match', tokenisedCardPageUrlPattern);
  });

  describe('TokenisedCard page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(tokenisedCardPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TokenisedCard page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/tokenised-card/new$'));
        cy.getEntityCreateUpdateHeading('TokenisedCard');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', tokenisedCardPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tokenised-cards',
          body: tokenisedCardSample,
        }).then(({ body }) => {
          tokenisedCard = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tokenised-cards+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [tokenisedCard],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(tokenisedCardPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TokenisedCard page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('tokenisedCard');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', tokenisedCardPageUrlPattern);
      });

      it('edit button click should load edit TokenisedCard page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TokenisedCard');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', tokenisedCardPageUrlPattern);
      });

      it('last delete button click should delete instance of TokenisedCard', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('tokenisedCard').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', tokenisedCardPageUrlPattern);

        tokenisedCard = undefined;
      });
    });
  });

  describe('new TokenisedCard page', () => {
    beforeEach(() => {
      cy.visit(`${tokenisedCardPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TokenisedCard');
    });

    it('should create an instance of TokenisedCard', () => {
      cy.get(`[data-cy="token"]`).type('input Stream Account').should('have.value', 'input Stream Account');

      cy.get(`[data-cy="cardExpiryMonth"]`).type('Berkshire Fish').should('have.value', 'Berkshire Fish');

      cy.get(`[data-cy="cardExpiryYear"]`).type('framework').should('have.value', 'framework');

      cy.get(`[data-cy="truncatedCardNumber"]`).type('International').should('have.value', 'International');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        tokenisedCard = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', tokenisedCardPageUrlPattern);
    });
  });
});
