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

describe('PaymentJob e2e test', () => {
  const paymentJobPageUrl = '/payment-job';
  const paymentJobPageUrlPattern = new RegExp('/payment-job(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentJobSample = {};

  let paymentJob: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-jobs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-jobs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-jobs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentJob) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-jobs/${paymentJob.id}`,
      }).then(() => {
        paymentJob = undefined;
      });
    }
  });

  it('PaymentJobs menu should load PaymentJobs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-job');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentJob').should('exist');
    cy.url().should('match', paymentJobPageUrlPattern);
  });

  describe('PaymentJob page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentJobPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentJob page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-job/new$'));
        cy.getEntityCreateUpdateHeading('PaymentJob');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-jobs',
          body: paymentJobSample,
        }).then(({ body }) => {
          paymentJob = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-jobs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentJob],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentJobPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentJob page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentJob');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobPageUrlPattern);
      });

      it('edit button click should load edit PaymentJob page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentJob');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentJob', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentJob').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentJobPageUrlPattern);

        paymentJob = undefined;
      });
    });
  });

  describe('new PaymentJob page', () => {
    beforeEach(() => {
      cy.visit(`${paymentJobPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentJob');
    });

    it('should create an instance of PaymentJob', () => {
      cy.get(`[data-cy="reference"]`).type('80614').should('have.value', '80614');

      cy.get(`[data-cy="createDateTimeUtc"]`).type('2022-07-08T14:37').should('have.value', '2022-07-08T14:37');

      cy.get(`[data-cy="type"]`).select('PAYMENT');

      cy.get(`[data-cy="traceReference"]`).type('46529').should('have.value', '46529');

      cy.get(`[data-cy="configurationId"]`).type('drive Consultant').should('have.value', 'drive Consultant');

      cy.get(`[data-cy="domain"]`).type('Delaware').should('have.value', 'Delaware');

      cy.get(`[data-cy="locale"]`).select('EN_GB');

      cy.get(`[data-cy="timeZone"]`).type('Island SMS').should('have.value', 'Island SMS');

      cy.get(`[data-cy="parentPaymentJobReference"]`).type('61675').should('have.value', '61675');

      cy.get(`[data-cy="displayUrl"]`).type('array transmitting Forward').should('have.value', 'array transmitting Forward');

      cy.get(`[data-cy="currency"]`).select('RUB');

      cy.get(`[data-cy="amountToCollect"]`).type('54788').should('have.value', '54788');

      cy.get(`[data-cy="amountCollected"]`).type('99731').should('have.value', '99731');

      cy.get(`[data-cy="paidAmount"]`).type('24949').should('have.value', '24949');

      cy.get(`[data-cy="paidDateTimeUtc"]`).type('2022-07-08T22:18').should('have.value', '2022-07-08T22:18');

      cy.get(`[data-cy="expirationDateTimeUtc"]`).type('2022-07-08T14:57').should('have.value', '2022-07-08T14:57');

      cy.get(`[data-cy="dueDateTimeUtc"]`).type('2022-07-08T15:38').should('have.value', '2022-07-08T15:38');

      cy.get(`[data-cy="lastUpdateTimeUtc"]`).type('2022-07-08T14:40').should('have.value', '2022-07-08T14:40');

      cy.get(`[data-cy="lastProcessedTimeUtc"]`).type('2022-07-08T18:58').should('have.value', '2022-07-08T18:58');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentJob = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentJobPageUrlPattern);
    });
  });
});
