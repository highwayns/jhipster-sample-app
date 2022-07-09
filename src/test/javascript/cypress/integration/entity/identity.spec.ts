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

describe('Identity e2e test', () => {
  const identityPageUrl = '/identity';
  const identityPageUrlPattern = new RegExp('/identity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const identitySample = {};

  let identity: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/identities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/identities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/identities/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (identity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/identities/${identity.id}`,
      }).then(() => {
        identity = undefined;
      });
    }
  });

  it('Identities menu should load Identities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('identity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Identity').should('exist');
    cy.url().should('match', identityPageUrlPattern);
  });

  describe('Identity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(identityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Identity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/identity/new$'));
        cy.getEntityCreateUpdateHeading('Identity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', identityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/identities',
          body: identitySample,
        }).then(({ body }) => {
          identity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/identities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [identity],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(identityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Identity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('identity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', identityPageUrlPattern);
      });

      it('edit button click should load edit Identity page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Identity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', identityPageUrlPattern);
      });

      it('last delete button click should delete instance of Identity', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('identity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', identityPageUrlPattern);

        identity = undefined;
      });
    });
  });

  describe('new Identity page', () => {
    beforeEach(() => {
      cy.visit(`${identityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Identity');
    });

    it('should create an instance of Identity', () => {
      cy.get(`[data-cy="debtorId"]`).type('Kazakhstan iterate').should('have.value', 'Kazakhstan iterate');

      cy.get(`[data-cy="emailAddress"]`).type('end-to-end Computer').should('have.value', 'end-to-end Computer');

      cy.get(`[data-cy="gender"]`).select('FEMALE');

      cy.get(`[data-cy="dateOfBirth"]`).type('2022-07-08T07:39').should('have.value', '2022-07-08T07:39');

      cy.get(`[data-cy="socialSecurityNumber"]`).type('regional').should('have.value', 'regional');

      cy.get(`[data-cy="chamberOfCommerceNumber"]`).type('e-business invoice').should('have.value', 'e-business invoice');

      cy.get(`[data-cy="vatNumber"]`).type('Shirt').should('have.value', 'Shirt');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        identity = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', identityPageUrlPattern);
    });
  });
});
