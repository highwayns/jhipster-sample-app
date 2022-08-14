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

describe('ApiKeys e2e test', () => {
  const apiKeysPageUrl = '/api-keys';
  const apiKeysPageUrlPattern = new RegExp('/api-keys(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const apiKeysSample = { key: 'Fresh Sports', secret: 'Bedfordshire', view: 'N', orders: 'N', withdraw: 'Y', nonce: 71126 };

  let apiKeys: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/api-keys+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/api-keys').as('postEntityRequest');
    cy.intercept('DELETE', '/api/api-keys/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (apiKeys) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/api-keys/${apiKeys.id}`,
      }).then(() => {
        apiKeys = undefined;
      });
    }
  });

  it('ApiKeys menu should load ApiKeys page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('api-keys');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ApiKeys').should('exist');
    cy.url().should('match', apiKeysPageUrlPattern);
  });

  describe('ApiKeys page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(apiKeysPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ApiKeys page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/api-keys/new$'));
        cy.getEntityCreateUpdateHeading('ApiKeys');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', apiKeysPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/api-keys',
          body: apiKeysSample,
        }).then(({ body }) => {
          apiKeys = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/api-keys+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/api-keys?page=0&size=20>; rel="last",<http://localhost/api/api-keys?page=0&size=20>; rel="first"',
              },
              body: [apiKeys],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(apiKeysPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ApiKeys page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('apiKeys');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', apiKeysPageUrlPattern);
      });

      it('edit button click should load edit ApiKeys page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ApiKeys');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', apiKeysPageUrlPattern);
      });

      it('last delete button click should delete instance of ApiKeys', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('apiKeys').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', apiKeysPageUrlPattern);

        apiKeys = undefined;
      });
    });
  });

  describe('new ApiKeys page', () => {
    beforeEach(() => {
      cy.visit(`${apiKeysPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ApiKeys');
    });

    it('should create an instance of ApiKeys', () => {
      cy.get(`[data-cy="key"]`).type('Legacy quantify Walks').should('have.value', 'Legacy quantify Walks');

      cy.get(`[data-cy="secret"]`).type('SCSI Soap Bedfordshire').should('have.value', 'SCSI Soap Bedfordshire');

      cy.get(`[data-cy="view"]`).select('N');

      cy.get(`[data-cy="orders"]`).select('N');

      cy.get(`[data-cy="withdraw"]`).select('Y');

      cy.get(`[data-cy="nonce"]`).type('86286').should('have.value', '86286');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        apiKeys = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', apiKeysPageUrlPattern);
    });
  });
});
