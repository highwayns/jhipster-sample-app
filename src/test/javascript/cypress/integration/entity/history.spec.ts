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

describe('History e2e test', () => {
  const historyPageUrl = '/history';
  const historyPageUrlPattern = new RegExp('/history(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const historySample = {
    date: '2022-08-13T22:48:18.310Z',
    ip: 'Unbranded',
    bitcoinAddress: 'empower Serbian Sausages',
    balanceBefore: 29954,
    balanceAfter: 30873,
  };

  let history: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/histories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/histories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/histories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (history) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/histories/${history.id}`,
      }).then(() => {
        history = undefined;
      });
    }
  });

  it('Histories menu should load Histories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('history');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('History').should('exist');
    cy.url().should('match', historyPageUrlPattern);
  });

  describe('History page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(historyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create History page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/history/new$'));
        cy.getEntityCreateUpdateHeading('History');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/histories',
          body: historySample,
        }).then(({ body }) => {
          history = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/histories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/histories?page=0&size=20>; rel="last",<http://localhost/api/histories?page=0&size=20>; rel="first"',
              },
              body: [history],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(historyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details History page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('history');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyPageUrlPattern);
      });

      it('edit button click should load edit History page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('History');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyPageUrlPattern);
      });

      it('last delete button click should delete instance of History', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('history').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', historyPageUrlPattern);

        history = undefined;
      });
    });
  });

  describe('new History page', () => {
    beforeEach(() => {
      cy.visit(`${historyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('History');
    });

    it('should create an instance of History', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-13T18:01').should('have.value', '2022-08-13T18:01');

      cy.get(`[data-cy="ip"]`).type('solid Intelligent Concrete').should('have.value', 'solid Intelligent Concrete');

      cy.get(`[data-cy="bitcoinAddress"]`).type('Executive infrastructures').should('have.value', 'Executive infrastructures');

      cy.get(`[data-cy="balanceBefore"]`).type('12922').should('have.value', '12922');

      cy.get(`[data-cy="balanceAfter"]`).type('61897').should('have.value', '61897');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        history = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', historyPageUrlPattern);
    });
  });
});
