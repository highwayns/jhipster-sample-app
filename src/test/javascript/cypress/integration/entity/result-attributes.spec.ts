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

describe('ResultAttributes e2e test', () => {
  const resultAttributesPageUrl = '/result-attributes';
  const resultAttributesPageUrlPattern = new RegExp('/result-attributes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const resultAttributesSample = {};

  let resultAttributes: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/result-attributes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/result-attributes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/result-attributes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (resultAttributes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/result-attributes/${resultAttributes.id}`,
      }).then(() => {
        resultAttributes = undefined;
      });
    }
  });

  it('ResultAttributes menu should load ResultAttributes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('result-attributes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ResultAttributes').should('exist');
    cy.url().should('match', resultAttributesPageUrlPattern);
  });

  describe('ResultAttributes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(resultAttributesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ResultAttributes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/result-attributes/new$'));
        cy.getEntityCreateUpdateHeading('ResultAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', resultAttributesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/result-attributes',
          body: resultAttributesSample,
        }).then(({ body }) => {
          resultAttributes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/result-attributes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [resultAttributes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(resultAttributesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ResultAttributes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('resultAttributes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', resultAttributesPageUrlPattern);
      });

      it('edit button click should load edit ResultAttributes page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ResultAttributes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', resultAttributesPageUrlPattern);
      });

      it('last delete button click should delete instance of ResultAttributes', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('resultAttributes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', resultAttributesPageUrlPattern);

        resultAttributes = undefined;
      });
    });
  });

  describe('new ResultAttributes page', () => {
    beforeEach(() => {
      cy.visit(`${resultAttributesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ResultAttributes');
    });

    it('should create an instance of ResultAttributes', () => {
      cy.get(`[data-cy="key"]`).type('Bedfordshire Uruguay Chad').should('have.value', 'Bedfordshire Uruguay Chad');

      cy.get(`[data-cy="value"]`).type('Checking').should('have.value', 'Checking');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        resultAttributes = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', resultAttributesPageUrlPattern);
    });
  });
});
