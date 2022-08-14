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

describe('Requests e2e test', () => {
  const requestsPageUrl = '/requests';
  const requestsPageUrlPattern = new RegExp('/requests(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const requestsSample = {
    date: '2022-08-13T10:27:05.869Z',
    amount: 52316,
    addressId: 36106,
    account: 56072,
    sendAddress: 'Future',
    transactionId: 'killer structure Malaysian',
    increment: 44032,
    done: 'N',
    cryptoId: 67455,
    fee: 98151,
    netAmount: 86537,
    notified: 78458,
  };

  let requests: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/requests+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/requests').as('postEntityRequest');
    cy.intercept('DELETE', '/api/requests/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (requests) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/requests/${requests.id}`,
      }).then(() => {
        requests = undefined;
      });
    }
  });

  it('Requests menu should load Requests page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('requests');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Requests').should('exist');
    cy.url().should('match', requestsPageUrlPattern);
  });

  describe('Requests page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(requestsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Requests page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/requests/new$'));
        cy.getEntityCreateUpdateHeading('Requests');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/requests',
          body: requestsSample,
        }).then(({ body }) => {
          requests = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/requests+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/requests?page=0&size=20>; rel="last",<http://localhost/api/requests?page=0&size=20>; rel="first"',
              },
              body: [requests],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(requestsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Requests page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('requests');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestsPageUrlPattern);
      });

      it('edit button click should load edit Requests page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Requests');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestsPageUrlPattern);
      });

      it('last delete button click should delete instance of Requests', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('requests').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', requestsPageUrlPattern);

        requests = undefined;
      });
    });
  });

  describe('new Requests page', () => {
    beforeEach(() => {
      cy.visit(`${requestsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Requests');
    });

    it('should create an instance of Requests', () => {
      cy.get(`[data-cy="date"]`).type('2022-08-13T11:18').should('have.value', '2022-08-13T11:18');

      cy.get(`[data-cy="amount"]`).type('93356').should('have.value', '93356');

      cy.get(`[data-cy="addressId"]`).type('16920').should('have.value', '16920');

      cy.get(`[data-cy="account"]`).type('47974').should('have.value', '47974');

      cy.get(`[data-cy="sendAddress"]`).type('Creative Product open-source').should('have.value', 'Creative Product open-source');

      cy.get(`[data-cy="transactionId"]`).type('Unbranded').should('have.value', 'Unbranded');

      cy.get(`[data-cy="increment"]`).type('74613').should('have.value', '74613');

      cy.get(`[data-cy="done"]`).select('Y');

      cy.get(`[data-cy="cryptoId"]`).type('92850').should('have.value', '92850');

      cy.get(`[data-cy="fee"]`).type('37963').should('have.value', '37963');

      cy.get(`[data-cy="netAmount"]`).type('34907').should('have.value', '34907');

      cy.get(`[data-cy="notified"]`).type('65622').should('have.value', '65622');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        requests = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', requestsPageUrlPattern);
    });
  });
});
