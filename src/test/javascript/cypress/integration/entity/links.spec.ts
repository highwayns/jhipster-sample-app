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

describe('Links e2e test', () => {
  const linksPageUrl = '/links';
  const linksPageUrlPattern = new RegExp('/links(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const linksSample = {};

  let links: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/links+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/links').as('postEntityRequest');
    cy.intercept('DELETE', '/api/links/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (links) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/links/${links.id}`,
      }).then(() => {
        links = undefined;
      });
    }
  });

  it('Links menu should load Links page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('links');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Links').should('exist');
    cy.url().should('match', linksPageUrlPattern);
  });

  describe('Links page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(linksPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Links page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/links/new$'));
        cy.getEntityCreateUpdateHeading('Links');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linksPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/links',
          body: linksSample,
        }).then(({ body }) => {
          links = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/links+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [links],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(linksPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Links page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('links');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linksPageUrlPattern);
      });

      it('edit button click should load edit Links page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Links');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linksPageUrlPattern);
      });

      it('last delete button click should delete instance of Links', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('links').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linksPageUrlPattern);

        links = undefined;
      });
    });
  });

  describe('new Links page', () => {
    beforeEach(() => {
      cy.visit(`${linksPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Links');
    });

    it('should create an instance of Links', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        links = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', linksPageUrlPattern);
    });
  });
});
