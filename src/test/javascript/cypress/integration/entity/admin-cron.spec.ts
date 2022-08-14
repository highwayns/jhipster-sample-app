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

describe('AdminCron e2e test', () => {
  const adminCronPageUrl = '/admin-cron';
  const adminCronPageUrlPattern = new RegExp('/admin-cron(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminCronSample = { day: 'XSS teal Awesome', month: 'context-sensitive', year: 'National', sendCondition: 'Mountains' };

  let adminCron: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-crons+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-crons').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-crons/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminCron) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-crons/${adminCron.id}`,
      }).then(() => {
        adminCron = undefined;
      });
    }
  });

  it('AdminCrons menu should load AdminCrons page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-cron');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminCron').should('exist');
    cy.url().should('match', adminCronPageUrlPattern);
  });

  describe('AdminCron page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminCronPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminCron page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-cron/new$'));
        cy.getEntityCreateUpdateHeading('AdminCron');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminCronPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-crons',
          body: adminCronSample,
        }).then(({ body }) => {
          adminCron = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-crons+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-crons?page=0&size=20>; rel="last",<http://localhost/api/admin-crons?page=0&size=20>; rel="first"',
              },
              body: [adminCron],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminCronPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminCron page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminCron');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminCronPageUrlPattern);
      });

      it('edit button click should load edit AdminCron page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminCron');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminCronPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminCron', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminCron').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminCronPageUrlPattern);

        adminCron = undefined;
      });
    });
  });

  describe('new AdminCron page', () => {
    beforeEach(() => {
      cy.visit(`${adminCronPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminCron');
    });

    it('should create an instance of AdminCron', () => {
      cy.get(`[data-cy="day"]`).type('Berkshire Designer').should('have.value', 'Berkshire Designer');

      cy.get(`[data-cy="month"]`).type('Harbor').should('have.value', 'Harbor');

      cy.get(`[data-cy="year"]`).type('Brunei').should('have.value', 'Brunei');

      cy.get(`[data-cy="sendCondition"]`).type('New').should('have.value', 'New');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminCron = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminCronPageUrlPattern);
    });
  });
});
