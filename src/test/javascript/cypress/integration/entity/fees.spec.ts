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

describe('Fees e2e test', () => {
  const feesPageUrl = '/fees';
  const feesPageUrlPattern = new RegExp('/fees(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const feesSample = { fee: 53067, date: '2022-08-13T12:07:53.663Z' };

  let fees: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/fees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/fees').as('postEntityRequest');
    cy.intercept('DELETE', '/api/fees/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fees) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/fees/${fees.id}`,
      }).then(() => {
        fees = undefined;
      });
    }
  });

  it('Fees menu should load Fees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('fees');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Fees').should('exist');
    cy.url().should('match', feesPageUrlPattern);
  });

  describe('Fees page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(feesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Fees page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/fees/new$'));
        cy.getEntityCreateUpdateHeading('Fees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/fees',
          body: feesSample,
        }).then(({ body }) => {
          fees = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/fees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/fees?page=0&size=20>; rel="last",<http://localhost/api/fees?page=0&size=20>; rel="first"',
              },
              body: [fees],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(feesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Fees page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fees');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feesPageUrlPattern);
      });

      it('edit button click should load edit Fees page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Fees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feesPageUrlPattern);
      });

      it('last delete button click should delete instance of Fees', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('fees').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feesPageUrlPattern);

        fees = undefined;
      });
    });
  });

  describe('new Fees page', () => {
    beforeEach(() => {
      cy.visit(`${feesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Fees');
    });

    it('should create an instance of Fees', () => {
      cy.get(`[data-cy="fee"]`).type('23931').should('have.value', '23931');

      cy.get(`[data-cy="date"]`).type('2022-08-14T08:36').should('have.value', '2022-08-14T08:36');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        fees = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', feesPageUrlPattern);
    });
  });
});
