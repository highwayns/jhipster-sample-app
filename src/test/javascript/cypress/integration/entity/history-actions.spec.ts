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

describe('HistoryActions e2e test', () => {
  const historyActionsPageUrl = '/history-actions';
  const historyActionsPageUrlPattern = new RegExp('/history-actions(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const historyActionsSample = {
    nameEn: 'communities yellow',
    nameEs: 'Enterprise-wide connect streamline',
    nameRu: 'Account Producer back',
    nameZh: 'Vision-oriented',
  };

  let historyActions: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/history-actions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/history-actions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/history-actions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (historyActions) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/history-actions/${historyActions.id}`,
      }).then(() => {
        historyActions = undefined;
      });
    }
  });

  it('HistoryActions menu should load HistoryActions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('history-actions');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HistoryActions').should('exist');
    cy.url().should('match', historyActionsPageUrlPattern);
  });

  describe('HistoryActions page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(historyActionsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HistoryActions page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/history-actions/new$'));
        cy.getEntityCreateUpdateHeading('HistoryActions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyActionsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/history-actions',
          body: historyActionsSample,
        }).then(({ body }) => {
          historyActions = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/history-actions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/history-actions?page=0&size=20>; rel="last",<http://localhost/api/history-actions?page=0&size=20>; rel="first"',
              },
              body: [historyActions],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(historyActionsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HistoryActions page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('historyActions');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyActionsPageUrlPattern);
      });

      it('edit button click should load edit HistoryActions page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HistoryActions');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyActionsPageUrlPattern);
      });

      it('last delete button click should delete instance of HistoryActions', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('historyActions').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyActionsPageUrlPattern);

        historyActions = undefined;
      });
    });
  });

  describe('new HistoryActions page', () => {
    beforeEach(() => {
      cy.visit(`${historyActionsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HistoryActions');
    });

    it('should create an instance of HistoryActions', () => {
      cy.get(`[data-cy="nameEn"]`).type('synergistic bypass').should('have.value', 'synergistic bypass');

      cy.get(`[data-cy="nameEs"]`).type('override Games').should('have.value', 'override Games');

      cy.get(`[data-cy="nameRu"]`).type('synergize web').should('have.value', 'synergize web');

      cy.get(`[data-cy="nameZh"]`).type('Colorado to innovative').should('have.value', 'Colorado to innovative');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        historyActions = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', historyActionsPageUrlPattern);
    });
  });
});
