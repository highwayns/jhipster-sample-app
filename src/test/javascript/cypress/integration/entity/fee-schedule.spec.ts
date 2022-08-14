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

describe('FeeSchedule e2e test', () => {
  const feeSchedulePageUrl = '/fee-schedule';
  const feeSchedulePageUrlPattern = new RegExp('/fee-schedule(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const feeScheduleSample = { fee: 63337, fromUsd: 90021, toUsd: 74784, order: 41265, fee1: 91168, globalBtc: 87708 };

  let feeSchedule: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/fee-schedules+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/fee-schedules').as('postEntityRequest');
    cy.intercept('DELETE', '/api/fee-schedules/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (feeSchedule) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/fee-schedules/${feeSchedule.id}`,
      }).then(() => {
        feeSchedule = undefined;
      });
    }
  });

  it('FeeSchedules menu should load FeeSchedules page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('fee-schedule');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FeeSchedule').should('exist');
    cy.url().should('match', feeSchedulePageUrlPattern);
  });

  describe('FeeSchedule page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(feeSchedulePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FeeSchedule page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/fee-schedule/new$'));
        cy.getEntityCreateUpdateHeading('FeeSchedule');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feeSchedulePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/fee-schedules',
          body: feeScheduleSample,
        }).then(({ body }) => {
          feeSchedule = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/fee-schedules+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/fee-schedules?page=0&size=20>; rel="last",<http://localhost/api/fee-schedules?page=0&size=20>; rel="first"',
              },
              body: [feeSchedule],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(feeSchedulePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FeeSchedule page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('feeSchedule');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feeSchedulePageUrlPattern);
      });

      it('edit button click should load edit FeeSchedule page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FeeSchedule');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feeSchedulePageUrlPattern);
      });

      it('last delete button click should delete instance of FeeSchedule', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('feeSchedule').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', feeSchedulePageUrlPattern);

        feeSchedule = undefined;
      });
    });
  });

  describe('new FeeSchedule page', () => {
    beforeEach(() => {
      cy.visit(`${feeSchedulePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FeeSchedule');
    });

    it('should create an instance of FeeSchedule', () => {
      cy.get(`[data-cy="fee"]`).type('84182').should('have.value', '84182');

      cy.get(`[data-cy="fromUsd"]`).type('14673').should('have.value', '14673');

      cy.get(`[data-cy="toUsd"]`).type('17233').should('have.value', '17233');

      cy.get(`[data-cy="order"]`).type('75042').should('have.value', '75042');

      cy.get(`[data-cy="fee1"]`).type('28125').should('have.value', '28125');

      cy.get(`[data-cy="globalBtc"]`).type('33479').should('have.value', '33479');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        feeSchedule = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', feeSchedulePageUrlPattern);
    });
  });
});
