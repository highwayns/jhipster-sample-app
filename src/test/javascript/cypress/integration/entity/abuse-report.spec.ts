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

describe('AbuseReport e2e test', () => {
  const abuseReportPageUrl = '/abuse-report';
  const abuseReportPageUrlPattern = new RegExp('/abuse-report(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const abuseReportSample = {};

  let abuseReport: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/abuse-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/abuse-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/abuse-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (abuseReport) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/abuse-reports/${abuseReport.id}`,
      }).then(() => {
        abuseReport = undefined;
      });
    }
  });

  it('AbuseReports menu should load AbuseReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('abuse-report');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AbuseReport').should('exist');
    cy.url().should('match', abuseReportPageUrlPattern);
  });

  describe('AbuseReport page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(abuseReportPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AbuseReport page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/abuse-report/new$'));
        cy.getEntityCreateUpdateHeading('AbuseReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseReportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/abuse-reports',
          body: abuseReportSample,
        }).then(({ body }) => {
          abuseReport = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/abuse-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [abuseReport],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(abuseReportPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AbuseReport page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('abuseReport');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseReportPageUrlPattern);
      });

      it('edit button click should load edit AbuseReport page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AbuseReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseReportPageUrlPattern);
      });

      it('last delete button click should delete instance of AbuseReport', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('abuseReport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', abuseReportPageUrlPattern);

        abuseReport = undefined;
      });
    });
  });

  describe('new AbuseReport page', () => {
    beforeEach(() => {
      cy.visit(`${abuseReportPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AbuseReport');
    });

    it('should create an instance of AbuseReport', () => {
      cy.get(`[data-cy="score"]`).type('95534').should('have.value', '95534');

      cy.get(`[data-cy="createdDateTimeUtc"]`).type('2022-07-08T17:17').should('have.value', '2022-07-08T17:17');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        abuseReport = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', abuseReportPageUrlPattern);
    });
  });
});
