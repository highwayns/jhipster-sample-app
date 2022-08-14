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

describe('InnodbLockMonitor e2e test', () => {
  const innodbLockMonitorPageUrl = '/innodb-lock-monitor';
  const innodbLockMonitorPageUrlPattern = new RegExp('/innodb-lock-monitor(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const innodbLockMonitorSample = {};

  let innodbLockMonitor: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/innodb-lock-monitors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/innodb-lock-monitors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/innodb-lock-monitors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (innodbLockMonitor) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/innodb-lock-monitors/${innodbLockMonitor.id}`,
      }).then(() => {
        innodbLockMonitor = undefined;
      });
    }
  });

  it('InnodbLockMonitors menu should load InnodbLockMonitors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('innodb-lock-monitor');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InnodbLockMonitor').should('exist');
    cy.url().should('match', innodbLockMonitorPageUrlPattern);
  });

  describe('InnodbLockMonitor page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(innodbLockMonitorPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InnodbLockMonitor page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/innodb-lock-monitor/new$'));
        cy.getEntityCreateUpdateHeading('InnodbLockMonitor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', innodbLockMonitorPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/innodb-lock-monitors',
          body: innodbLockMonitorSample,
        }).then(({ body }) => {
          innodbLockMonitor = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/innodb-lock-monitors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/innodb-lock-monitors?page=0&size=20>; rel="last",<http://localhost/api/innodb-lock-monitors?page=0&size=20>; rel="first"',
              },
              body: [innodbLockMonitor],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(innodbLockMonitorPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InnodbLockMonitor page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('innodbLockMonitor');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', innodbLockMonitorPageUrlPattern);
      });

      it('edit button click should load edit InnodbLockMonitor page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InnodbLockMonitor');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', innodbLockMonitorPageUrlPattern);
      });

      it('last delete button click should delete instance of InnodbLockMonitor', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('innodbLockMonitor').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', innodbLockMonitorPageUrlPattern);

        innodbLockMonitor = undefined;
      });
    });
  });

  describe('new InnodbLockMonitor page', () => {
    beforeEach(() => {
      cy.visit(`${innodbLockMonitorPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InnodbLockMonitor');
    });

    it('should create an instance of InnodbLockMonitor', () => {
      cy.get(`[data-cy="a"]`).type('38897').should('have.value', '38897');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        innodbLockMonitor = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', innodbLockMonitorPageUrlPattern);
    });
  });
});
