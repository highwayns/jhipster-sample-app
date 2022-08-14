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

describe('MonthlyReports e2e test', () => {
  const monthlyReportsPageUrl = '/monthly-reports';
  const monthlyReportsPageUrlPattern = new RegExp('/monthly-reports(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const monthlyReportsSample = {
    date: '2022-08-14T00:31:05.666Z',
    transactionsBtc: 54923,
    avgTransactionSizeBtc: 96203,
    transactionVolumePerUser: 10432,
    totalFeesBtc: 93367,
    feesPerUserBtc: 81973,
    grossProfitBtc: 70552,
  };

  let monthlyReports: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/monthly-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/monthly-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/monthly-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (monthlyReports) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/monthly-reports/${monthlyReports.id}`,
      }).then(() => {
        monthlyReports = undefined;
      });
    }
  });

  it('MonthlyReports menu should load MonthlyReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('monthly-reports');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MonthlyReports').should('exist');
    cy.url().should('match', monthlyReportsPageUrlPattern);
  });

  describe('MonthlyReports page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(monthlyReportsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MonthlyReports page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/monthly-reports/new$'));
        cy.getEntityCreateUpdateHeading('MonthlyReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyReportsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/monthly-reports',
          body: monthlyReportsSample,
        }).then(({ body }) => {
          monthlyReports = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/monthly-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/monthly-reports?page=0&size=20>; rel="last",<http://localhost/api/monthly-reports?page=0&size=20>; rel="first"',
              },
              body: [monthlyReports],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(monthlyReportsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MonthlyReports page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('monthlyReports');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyReportsPageUrlPattern);
      });

      it('edit button click should load edit MonthlyReports page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MonthlyReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyReportsPageUrlPattern);
      });

      it('last delete button click should delete instance of MonthlyReports', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('monthlyReports').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', monthlyReportsPageUrlPattern);

        monthlyReports = undefined;
      });
    });
  });

  describe('new MonthlyReports page', () => {
    beforeEach(() => {
      cy.visit(`${monthlyReportsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MonthlyReports');
    });

    it('should create an instance of MonthlyReports', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-13T12:08').should('have.value', '2022-08-13T12:08');

      cy.get(`[data-cy="transactionsBtc"]`).type('80245').should('have.value', '80245');

      cy.get(`[data-cy="avgTransactionSizeBtc"]`).type('33003').should('have.value', '33003');

      cy.get(`[data-cy="transactionVolumePerUser"]`).type('48136').should('have.value', '48136');

      cy.get(`[data-cy="totalFeesBtc"]`).type('471').should('have.value', '471');

      cy.get(`[data-cy="feesPerUserBtc"]`).type('62966').should('have.value', '62966');

      cy.get(`[data-cy="grossProfitBtc"]`).type('93807').should('have.value', '93807');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        monthlyReports = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', monthlyReportsPageUrlPattern);
    });
  });
});
