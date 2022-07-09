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

describe('ErrorReport e2e test', () => {
  const errorReportPageUrl = '/error-report';
  const errorReportPageUrlPattern = new RegExp('/error-report(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const errorReportSample = {};

  let errorReport: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/error-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/error-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/error-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (errorReport) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/error-reports/${errorReport.id}`,
      }).then(() => {
        errorReport = undefined;
      });
    }
  });

  it('ErrorReports menu should load ErrorReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('error-report');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ErrorReport').should('exist');
    cy.url().should('match', errorReportPageUrlPattern);
  });

  describe('ErrorReport page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(errorReportPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ErrorReport page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/error-report/new$'));
        cy.getEntityCreateUpdateHeading('ErrorReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', errorReportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/error-reports',
          body: errorReportSample,
        }).then(({ body }) => {
          errorReport = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/error-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [errorReport],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(errorReportPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ErrorReport page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('errorReport');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', errorReportPageUrlPattern);
      });

      it('edit button click should load edit ErrorReport page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ErrorReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', errorReportPageUrlPattern);
      });

      it('last delete button click should delete instance of ErrorReport', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('errorReport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', errorReportPageUrlPattern);

        errorReport = undefined;
      });
    });
  });

  describe('new ErrorReport page', () => {
    beforeEach(() => {
      cy.visit(`${errorReportPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ErrorReport');
    });

    it('should create an instance of ErrorReport', () => {
      cy.get(`[data-cy="language"]`).select('NL_NL');

      cy.get(`[data-cy="isFatalError"]`).should('not.be.checked');
      cy.get(`[data-cy="isFatalError"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        errorReport = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', errorReportPageUrlPattern);
    });
  });
});
