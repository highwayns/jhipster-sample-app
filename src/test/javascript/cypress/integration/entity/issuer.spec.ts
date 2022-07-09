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

describe('Issuer e2e test', () => {
  const issuerPageUrl = '/issuer';
  const issuerPageUrlPattern = new RegExp('/issuer(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const issuerSample = {};

  let issuer: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/issuers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/issuers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/issuers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (issuer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/issuers/${issuer.id}`,
      }).then(() => {
        issuer = undefined;
      });
    }
  });

  it('Issuers menu should load Issuers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('issuer');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Issuer').should('exist');
    cy.url().should('match', issuerPageUrlPattern);
  });

  describe('Issuer page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(issuerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Issuer page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/issuer/new$'));
        cy.getEntityCreateUpdateHeading('Issuer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', issuerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/issuers',
          body: issuerSample,
        }).then(({ body }) => {
          issuer = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/issuers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [issuer],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(issuerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Issuer page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('issuer');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', issuerPageUrlPattern);
      });

      it('edit button click should load edit Issuer page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Issuer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', issuerPageUrlPattern);
      });

      it('last delete button click should delete instance of Issuer', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('issuer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', issuerPageUrlPattern);

        issuer = undefined;
      });
    });
  });

  describe('new Issuer page', () => {
    beforeEach(() => {
      cy.visit(`${issuerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Issuer');
    });

    it('should create an instance of Issuer', () => {
      cy.get(`[data-cy="id"]`).type('80ca428e-4ea2-4bf1-bcc2-a0ff34e53191').should('have.value', '80ca428e-4ea2-4bf1-bcc2-a0ff34e53191');

      cy.get(`[data-cy="name"]`).type('Manager').should('have.value', 'Manager');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        issuer = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', issuerPageUrlPattern);
    });
  });
});
