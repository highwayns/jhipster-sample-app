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

describe('HistoricalData e2e test', () => {
  const historicalDataPageUrl = '/historical-data';
  const historicalDataPageUrlPattern = new RegExp('/historical-data(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const historicalDataSample = { date: '2022-08-13', usd: 25664 };

  let historicalData: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/historical-data+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/historical-data').as('postEntityRequest');
    cy.intercept('DELETE', '/api/historical-data/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (historicalData) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/historical-data/${historicalData.id}`,
      }).then(() => {
        historicalData = undefined;
      });
    }
  });

  it('HistoricalData menu should load HistoricalData page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('historical-data');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HistoricalData').should('exist');
    cy.url().should('match', historicalDataPageUrlPattern);
  });

  describe('HistoricalData page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(historicalDataPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HistoricalData page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/historical-data/new$'));
        cy.getEntityCreateUpdateHeading('HistoricalData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historicalDataPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/historical-data',
          body: historicalDataSample,
        }).then(({ body }) => {
          historicalData = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/historical-data+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/historical-data?page=0&size=20>; rel="last",<http://localhost/api/historical-data?page=0&size=20>; rel="first"',
              },
              body: [historicalData],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(historicalDataPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HistoricalData page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('historicalData');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historicalDataPageUrlPattern);
      });

      it('edit button click should load edit HistoricalData page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HistoricalData');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historicalDataPageUrlPattern);
      });

      it('last delete button click should delete instance of HistoricalData', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('historicalData').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historicalDataPageUrlPattern);

        historicalData = undefined;
      });
    });
  });

  describe('new HistoricalData page', () => {
    beforeEach(() => {
      cy.visit(`${historicalDataPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HistoricalData');
    });

    it('should create an instance of HistoricalData', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-14').should('have.value', '2022-08-14');

      cy.get(`[data-cy="usd"]`).type('39006').should('have.value', '39006');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        historicalData = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', historicalDataPageUrlPattern);
    });
  });
});
