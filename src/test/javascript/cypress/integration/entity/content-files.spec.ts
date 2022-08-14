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

describe('ContentFiles e2e test', () => {
  const contentFilesPageUrl = '/content-files';
  const contentFilesPageUrlPattern = new RegExp('/content-files(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contentFilesSample = {
    fId: 43741,
    ext: 'Dela',
    dir: 'Car Franc Principal',
    url: 'http://lawrence.info',
    oldName: 'turn-key navigate Dynamic',
    fieldName: 'Borders feed Concrete',
  };

  let contentFiles: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/content-files+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/content-files').as('postEntityRequest');
    cy.intercept('DELETE', '/api/content-files/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contentFiles) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/content-files/${contentFiles.id}`,
      }).then(() => {
        contentFiles = undefined;
      });
    }
  });

  it('ContentFiles menu should load ContentFiles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('content-files');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ContentFiles').should('exist');
    cy.url().should('match', contentFilesPageUrlPattern);
  });

  describe('ContentFiles page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contentFilesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ContentFiles page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/content-files/new$'));
        cy.getEntityCreateUpdateHeading('ContentFiles');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentFilesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/content-files',
          body: contentFilesSample,
        }).then(({ body }) => {
          contentFiles = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/content-files+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/content-files?page=0&size=20>; rel="last",<http://localhost/api/content-files?page=0&size=20>; rel="first"',
              },
              body: [contentFiles],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(contentFilesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ContentFiles page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contentFiles');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentFilesPageUrlPattern);
      });

      it('edit button click should load edit ContentFiles page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ContentFiles');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentFilesPageUrlPattern);
      });

      it('last delete button click should delete instance of ContentFiles', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('contentFiles').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', contentFilesPageUrlPattern);

        contentFiles = undefined;
      });
    });
  });

  describe('new ContentFiles page', () => {
    beforeEach(() => {
      cy.visit(`${contentFilesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ContentFiles');
    });

    it('should create an instance of ContentFiles', () => {
      cy.get(`[data-cy="fId"]`).type('8159').should('have.value', '8159');

      cy.get(`[data-cy="ext"]`).type('whit').should('have.value', 'whit');

      cy.get(`[data-cy="dir"]`).type('Creative Dynamic').should('have.value', 'Creative Dynamic');

      cy.get(`[data-cy="url"]`).type('http://doyle.org').should('have.value', 'http://doyle.org');

      cy.get(`[data-cy="oldName"]`).type('Chair IB Program').should('have.value', 'Chair IB Program');

      cy.get(`[data-cy="fieldName"]`).type('silver invoice').should('have.value', 'silver invoice');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        contentFiles = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', contentFilesPageUrlPattern);
    });
  });
});
