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

describe('Link e2e test', () => {
  const linkPageUrl = '/link';
  const linkPageUrlPattern = new RegExp('/link(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const linkSample = {};

  let link: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/links+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/links').as('postEntityRequest');
    cy.intercept('DELETE', '/api/links/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (link) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/links/${link.id}`,
      }).then(() => {
        link = undefined;
      });
    }
  });

  it('Links menu should load Links page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('link');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Link').should('exist');
    cy.url().should('match', linkPageUrlPattern);
  });

  describe('Link page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(linkPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Link page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/link/new$'));
        cy.getEntityCreateUpdateHeading('Link');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linkPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/links',
          body: linkSample,
        }).then(({ body }) => {
          link = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/links+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [link],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(linkPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Link page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('link');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linkPageUrlPattern);
      });

      it('edit button click should load edit Link page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Link');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linkPageUrlPattern);
      });

      it('last delete button click should delete instance of Link', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('link').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', linkPageUrlPattern);

        link = undefined;
      });
    });
  });

  describe('new Link page', () => {
    beforeEach(() => {
      cy.visit(`${linkPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Link');
    });

    it('should create an instance of Link', () => {
      cy.get(`[data-cy="url"]`).type('https://dovie.biz').should('have.value', 'https://dovie.biz');

      cy.get(`[data-cy="type"]`).type('SCSI').should('have.value', 'SCSI');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        link = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', linkPageUrlPattern);
    });
  });
});
