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

describe('News e2e test', () => {
  const newsPageUrl = '/news';
  const newsPageUrlPattern = new RegExp('/news(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const newsSample = {
    titleEn: 'Avon',
    titleEs: 'deposit reboot mesh',
    date: '2022-08-14T07:21:57.731Z',
    contentEn: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEnContentType: 'unknown',
    contentEs: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEsContentType: 'unknown',
    titleRu: 'software Accountability',
    titleZh: 'Checking',
    contentRu: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentRuContentType: 'unknown',
    contentZh: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentZhContentType: 'unknown',
  };

  let news: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/news+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/news').as('postEntityRequest');
    cy.intercept('DELETE', '/api/news/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (news) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/news/${news.id}`,
      }).then(() => {
        news = undefined;
      });
    }
  });

  it('News menu should load News page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('news');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('News').should('exist');
    cy.url().should('match', newsPageUrlPattern);
  });

  describe('News page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(newsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create News page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/news/new$'));
        cy.getEntityCreateUpdateHeading('News');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', newsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/news',
          body: newsSample,
        }).then(({ body }) => {
          news = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/news+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/news?page=0&size=20>; rel="last",<http://localhost/api/news?page=0&size=20>; rel="first"',
              },
              body: [news],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(newsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details News page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('news');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', newsPageUrlPattern);
      });

      it('edit button click should load edit News page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('News');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', newsPageUrlPattern);
      });

      it('last delete button click should delete instance of News', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('news').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', newsPageUrlPattern);

        news = undefined;
      });
    });
  });

  describe('new News page', () => {
    beforeEach(() => {
      cy.visit(`${newsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('News');
    });

    it('should create an instance of News', () => {
      cy.get(`[data-cy="titleEn"]`).type('Arkansas').should('have.value', 'Arkansas');

      cy.get(`[data-cy="titleEs"]`).type('auxiliary back-end Cheese').should('have.value', 'auxiliary back-end Cheese');

      cy.get(`[data-cy="date"]`).type('2022-08-13T23:20').should('have.value', '2022-08-13T23:20');

      cy.setFieldImageAsBytesOfEntity('contentEn', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentEs', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="titleRu"]`).type('Avon Unbranded South').should('have.value', 'Avon Unbranded South');

      cy.get(`[data-cy="titleZh"]`).type('synergize').should('have.value', 'synergize');

      cy.setFieldImageAsBytesOfEntity('contentRu', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentZh', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        news = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', newsPageUrlPattern);
    });
  });
});
