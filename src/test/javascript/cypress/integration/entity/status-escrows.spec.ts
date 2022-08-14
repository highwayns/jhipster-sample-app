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

describe('StatusEscrows e2e test', () => {
  const statusEscrowsPageUrl = '/status-escrows';
  const statusEscrowsPageUrlPattern = new RegExp('/status-escrows(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const statusEscrowsSample = { balance: 17215 };

  let statusEscrows: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/status-escrows+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/status-escrows').as('postEntityRequest');
    cy.intercept('DELETE', '/api/status-escrows/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (statusEscrows) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/status-escrows/${statusEscrows.id}`,
      }).then(() => {
        statusEscrows = undefined;
      });
    }
  });

  it('StatusEscrows menu should load StatusEscrows page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('status-escrows');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StatusEscrows').should('exist');
    cy.url().should('match', statusEscrowsPageUrlPattern);
  });

  describe('StatusEscrows page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(statusEscrowsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StatusEscrows page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/status-escrows/new$'));
        cy.getEntityCreateUpdateHeading('StatusEscrows');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusEscrowsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/status-escrows',
          body: statusEscrowsSample,
        }).then(({ body }) => {
          statusEscrows = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/status-escrows+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/status-escrows?page=0&size=20>; rel="last",<http://localhost/api/status-escrows?page=0&size=20>; rel="first"',
              },
              body: [statusEscrows],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(statusEscrowsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StatusEscrows page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('statusEscrows');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusEscrowsPageUrlPattern);
      });

      it('edit button click should load edit StatusEscrows page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StatusEscrows');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusEscrowsPageUrlPattern);
      });

      it('last delete button click should delete instance of StatusEscrows', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('statusEscrows').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', statusEscrowsPageUrlPattern);

        statusEscrows = undefined;
      });
    });
  });

  describe('new StatusEscrows page', () => {
    beforeEach(() => {
      cy.visit(`${statusEscrowsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StatusEscrows');
    });

    it('should create an instance of StatusEscrows', () => {
      cy.get(`[data-cy="balance"]`).type('49450').should('have.value', '49450');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        statusEscrows = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', statusEscrowsPageUrlPattern);
    });
  });
});
