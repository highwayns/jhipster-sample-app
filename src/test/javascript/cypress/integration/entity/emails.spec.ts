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

describe('Emails e2e test', () => {
  const emailsPageUrl = '/emails';
  const emailsPageUrlPattern = new RegExp('/emails(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const emailsSample = {
    key: 'Response olive',
    titleEn: 'hard',
    titleEs: 'calculate Salad',
    contentEn: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEnContentType: 'unknown',
    contentEs: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentEsContentType: 'unknown',
    titleRu: 'Wooden Steel',
    titleZh: 'white',
    contentRu: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentRuContentType: 'unknown',
    contentZh: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    contentZhContentType: 'unknown',
  };

  let emails: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/emails+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/emails').as('postEntityRequest');
    cy.intercept('DELETE', '/api/emails/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (emails) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/emails/${emails.id}`,
      }).then(() => {
        emails = undefined;
      });
    }
  });

  it('Emails menu should load Emails page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emails');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Emails').should('exist');
    cy.url().should('match', emailsPageUrlPattern);
  });

  describe('Emails page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(emailsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Emails page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emails/new$'));
        cy.getEntityCreateUpdateHeading('Emails');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', emailsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/emails',
          body: emailsSample,
        }).then(({ body }) => {
          emails = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/emails+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/emails?page=0&size=20>; rel="last",<http://localhost/api/emails?page=0&size=20>; rel="first"',
              },
              body: [emails],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(emailsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Emails page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('emails');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', emailsPageUrlPattern);
      });

      it('edit button click should load edit Emails page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Emails');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', emailsPageUrlPattern);
      });

      it('last delete button click should delete instance of Emails', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('emails').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', emailsPageUrlPattern);

        emails = undefined;
      });
    });
  });

  describe('new Emails page', () => {
    beforeEach(() => {
      cy.visit(`${emailsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Emails');
    });

    it('should create an instance of Emails', () => {
      cy.get(`[data-cy="key"]`).type('Incredible indigo').should('have.value', 'Incredible indigo');

      cy.get(`[data-cy="titleEn"]`).type('copying Trail').should('have.value', 'copying Trail');

      cy.get(`[data-cy="titleEs"]`).type('bypassing Jamaica').should('have.value', 'bypassing Jamaica');

      cy.setFieldImageAsBytesOfEntity('contentEn', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentEs', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="titleRu"]`).type('e-business Function-based').should('have.value', 'e-business Function-based');

      cy.get(`[data-cy="titleZh"]`).type('Greenland').should('have.value', 'Greenland');

      cy.setFieldImageAsBytesOfEntity('contentRu', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('contentZh', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        emails = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', emailsPageUrlPattern);
    });
  });
});
