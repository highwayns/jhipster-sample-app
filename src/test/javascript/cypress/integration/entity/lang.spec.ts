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

describe('Lang e2e test', () => {
  const langPageUrl = '/lang';
  const langPageUrlPattern = new RegExp('/lang(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const langSample = {
    key: 'Locks Pants',
    esp: 'Outdoors synthesize deposit',
    eng: 'Orchestrator Rustic',
    order: 'Account incentivize',
    pId: 76071,
    zh: 'transmitting',
    ru: 'AI neural',
  };

  let lang: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/langs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/langs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/langs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (lang) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/langs/${lang.id}`,
      }).then(() => {
        lang = undefined;
      });
    }
  });

  it('Langs menu should load Langs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('lang');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Lang').should('exist');
    cy.url().should('match', langPageUrlPattern);
  });

  describe('Lang page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(langPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Lang page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/lang/new$'));
        cy.getEntityCreateUpdateHeading('Lang');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', langPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/langs',
          body: langSample,
        }).then(({ body }) => {
          lang = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/langs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/langs?page=0&size=20>; rel="last",<http://localhost/api/langs?page=0&size=20>; rel="first"',
              },
              body: [lang],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(langPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Lang page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('lang');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', langPageUrlPattern);
      });

      it('edit button click should load edit Lang page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Lang');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', langPageUrlPattern);
      });

      it('last delete button click should delete instance of Lang', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('lang').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', langPageUrlPattern);

        lang = undefined;
      });
    });
  });

  describe('new Lang page', () => {
    beforeEach(() => {
      cy.visit(`${langPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Lang');
    });

    it('should create an instance of Lang', () => {
      cy.get(`[data-cy="key"]`).type('Vista Handmade Toys').should('have.value', 'Vista Handmade Toys');

      cy.get(`[data-cy="esp"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="eng"]`).type('migration Bedfordshire 1080p').should('have.value', 'migration Bedfordshire 1080p');

      cy.get(`[data-cy="order"]`).type('markets Alabama').should('have.value', 'markets Alabama');

      cy.get(`[data-cy="pId"]`).type('87413').should('have.value', '87413');

      cy.get(`[data-cy="zh"]`).type('Tuna facilitate hack').should('have.value', 'Tuna facilitate hack');

      cy.get(`[data-cy="ru"]`).type('Knoll function protocol').should('have.value', 'Knoll function protocol');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        lang = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', langPageUrlPattern);
    });
  });
});
