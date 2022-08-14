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

describe('Content e2e test', () => {
  const contentPageUrl = '/content';
  const contentPageUrlPattern = new RegExp('/content(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contentSample = {
    url: 'http://federico.biz',
    titleEn: 'Rufiyaa partnerships',
    titleEs: 'lavender Shirt Developer',
    contentEn: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEnContentType: 'unknown',
    contentEs: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEsContentType: 'unknown',
    titleRu: 'New orchestration Chief',
    titleZh: 'Concrete Industrial card',
    contentRu: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentRuContentType: 'unknown',
    contentZh: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentZhContentType: 'unknown',
  };

  let content: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/contents+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/contents').as('postEntityRequest');
    cy.intercept('DELETE', '/api/contents/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (content) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/contents/${content.id}`,
      }).then(() => {
        content = undefined;
      });
    }
  });

  it('Contents menu should load Contents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Content').should('exist');
    cy.url().should('match', contentPageUrlPattern);
  });

  describe('Content page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Content page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/content/new$'));
        cy.getEntityCreateUpdateHeading('Content');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/contents',
          body: contentSample,
        }).then(({ body }) => {
          content = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/contents+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/contents?page=0&size=20>; rel="last",<http://localhost/api/contents?page=0&size=20>; rel="first"',
              },
              body: [content],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(contentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Content page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('content');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentPageUrlPattern);
      });

      it('edit button click should load edit Content page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Content');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentPageUrlPattern);
      });

      it('last delete button click should delete instance of Content', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('content').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentPageUrlPattern);

        content = undefined;
      });
    });
  });

  describe('new Content page', () => {
    beforeEach(() => {
      cy.visit(`${contentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Content');
    });

    it('should create an instance of Content', () => {
      cy.get(`[data-cy="url"]`).type('http://clifton.name').should('have.value', 'http://clifton.name');

      cy.get(`[data-cy="titleEn"]`).type('Chips Total').should('have.value', 'Chips Total');

      cy.get(`[data-cy="titleEs"]`).type('calculate JBOD').should('have.value', 'calculate JBOD');

      cy.setFieldImageAsBytesOfEntity('contentEn', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentEs', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="titleRu"]`).type('Ireland').should('have.value', 'Ireland');

      cy.get(`[data-cy="titleZh"]`).type('Plastic').should('have.value', 'Plastic');

      cy.setFieldImageAsBytesOfEntity('contentRu', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentZh', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        content = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', contentPageUrlPattern);
    });
  });
});
