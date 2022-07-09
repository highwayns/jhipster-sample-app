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

describe('Capture e2e test', () => {
  const capturePageUrl = '/capture';
  const capturePageUrlPattern = new RegExp('/capture(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const captureSample = {};

  let capture: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/captures+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/captures').as('postEntityRequest');
    cy.intercept('DELETE', '/api/captures/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (capture) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/captures/${capture.id}`,
      }).then(() => {
        capture = undefined;
      });
    }
  });

  it('Captures menu should load Captures page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('capture');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Capture').should('exist');
    cy.url().should('match', capturePageUrlPattern);
  });

  describe('Capture page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(capturePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Capture page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/capture/new$'));
        cy.getEntityCreateUpdateHeading('Capture');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', capturePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/captures',
          body: captureSample,
        }).then(({ body }) => {
          capture = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/captures+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [capture],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(capturePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Capture page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('capture');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', capturePageUrlPattern);
      });

      it('edit button click should load edit Capture page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Capture');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', capturePageUrlPattern);
      });

      it('last delete button click should delete instance of Capture', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('capture').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', capturePageUrlPattern);

        capture = undefined;
      });
    });
  });

  describe('new Capture page', () => {
    beforeEach(() => {
      cy.visit(`${capturePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Capture');
    });

    it('should create an instance of Capture', () => {
      cy.get(`[data-cy="reference"]`).type('52914').should('have.value', '52914');

      cy.get(`[data-cy="createDateTimeUtc"]`).type('2022-07-08T19:07').should('have.value', '2022-07-08T19:07');

      cy.get(`[data-cy="status"]`).select('PENDING');

      cy.get(`[data-cy="amountToCapture"]`).type('14252').should('have.value', '14252');

      cy.get(`[data-cy="convertedAmountToCapture"]`).type('22968').should('have.value', '22968');

      cy.get(`[data-cy="convertedCurrency"]`).select('RUB');

      cy.get(`[data-cy="conversionRate"]`).type('70124').should('have.value', '70124');

      cy.get(`[data-cy="isFinalCapture"]`).should('not.be.checked');
      cy.get(`[data-cy="isFinalCapture"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        capture = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', capturePageUrlPattern);
    });
  });
});
