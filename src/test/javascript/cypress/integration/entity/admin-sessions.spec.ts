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

describe('AdminSessions e2e test', () => {
  const adminSessionsPageUrl = '/admin-sessions';
  const adminSessionsPageUrlPattern = new RegExp('/admin-sessions(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminSessionsSample = {
    sessionId: 'Polarised Rapids',
    sessionTime: '2022-08-14T03:25:47.570Z',
    sessionStart: '2022-08-13T22:11:40.807Z',
    sessionValue: 'optimize back-end',
    ipAddress: 'Technician Assur',
    userAgent: 'compress',
  };

  let adminSessions: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-sessions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-sessions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-sessions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminSessions) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-sessions/${adminSessions.id}`,
      }).then(() => {
        adminSessions = undefined;
      });
    }
  });

  it('AdminSessions menu should load AdminSessions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-sessions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminSessions').should('exist');
    cy.url().should('match', adminSessionsPageUrlPattern);
  });

  describe('AdminSessions page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminSessionsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminSessions page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-sessions/new$'));
        cy.getEntityCreateUpdateHeading('AdminSessions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminSessionsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-sessions',
          body: adminSessionsSample,
        }).then(({ body }) => {
          adminSessions = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-sessions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-sessions?page=0&size=20>; rel="last",<http://localhost/api/admin-sessions?page=0&size=20>; rel="first"',
              },
              body: [adminSessions],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminSessionsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminSessions page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminSessions');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminSessionsPageUrlPattern);
      });

      it('edit button click should load edit AdminSessions page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminSessions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminSessionsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminSessions', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminSessions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminSessionsPageUrlPattern);

        adminSessions = undefined;
      });
    });
  });

  describe('new AdminSessions page', () => {
    beforeEach(() => {
      cy.visit(`${adminSessionsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminSessions');
    });

    it('should create an instance of AdminSessions', () => {
      cy.get(`[data-cy="sessionId"]`).type('Dominican dynamic').should('have.value', 'Dominican dynamic');

      cy.get(`[data-cy="sessionTime"]`).type('2022-08-13T15:59').should('have.value', '2022-08-13T15:59');

      cy.get(`[data-cy="sessionStart"]`).type('2022-08-13T11:18').should('have.value', '2022-08-13T11:18');

      cy.get(`[data-cy="sessionValue"]`).type('transmit database Home').should('have.value', 'transmit database Home');

      cy.get(`[data-cy="ipAddress"]`).type('digital').should('have.value', 'digital');

      cy.get(`[data-cy="userAgent"]`).type('Berkshire Chair').should('have.value', 'Berkshire Chair');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminSessions = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminSessionsPageUrlPattern);
    });
  });
});
