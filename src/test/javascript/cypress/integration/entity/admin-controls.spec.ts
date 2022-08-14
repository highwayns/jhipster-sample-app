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

describe('AdminControls e2e test', () => {
  const adminControlsPageUrl = '/admin-controls';
  const adminControlsPageUrlPattern = new RegExp('/admin-controls(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const adminControlsSample = { action: 'Global cross-media compressing', argument: 'Gorgeous', order: 12415, isStatic: 'Y' };

  let adminControls: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/admin-controls+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/admin-controls').as('postEntityRequest');
    cy.intercept('DELETE', '/api/admin-controls/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (adminControls) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/admin-controls/${adminControls.id}`,
      }).then(() => {
        adminControls = undefined;
      });
    }
  });

  it('AdminControls menu should load AdminControls page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('admin-controls');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AdminControls').should('exist');
    cy.url().should('match', adminControlsPageUrlPattern);
  });

  describe('AdminControls page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(adminControlsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AdminControls page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/admin-controls/new$'));
        cy.getEntityCreateUpdateHeading('AdminControls');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/admin-controls',
          body: adminControlsSample,
        }).then(({ body }) => {
          adminControls = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/admin-controls+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/admin-controls?page=0&size=20>; rel="last",<http://localhost/api/admin-controls?page=0&size=20>; rel="first"',
              },
              body: [adminControls],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(adminControlsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AdminControls page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('adminControls');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsPageUrlPattern);
      });

      it('edit button click should load edit AdminControls page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AdminControls');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsPageUrlPattern);
      });

      it('last delete button click should delete instance of AdminControls', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('adminControls').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', adminControlsPageUrlPattern);

        adminControls = undefined;
      });
    });
  });

  describe('new AdminControls page', () => {
    beforeEach(() => {
      cy.visit(`${adminControlsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AdminControls');
    });

    it('should create an instance of AdminControls', () => {
      cy.get(`[data-cy="action"]`).type('copying incubate').should('have.value', 'copying incubate');

      cy.get(`[data-cy="controlClass"]`).type('Colombian').should('have.value', 'Colombian');

      cy.get(`[data-cy="argument"]`).type('States').should('have.value', 'States');

      cy.get(`[data-cy="order"]`).type('96581').should('have.value', '96581');

      cy.get(`[data-cy="isStatic"]`).select('Y');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        adminControls = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', adminControlsPageUrlPattern);
    });
  });
});
