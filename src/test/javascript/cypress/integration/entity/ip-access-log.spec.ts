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

describe('IpAccessLog e2e test', () => {
  const ipAccessLogPageUrl = '/ip-access-log';
  const ipAccessLogPageUrlPattern = new RegExp('/ip-access-log(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ipAccessLogSample = { ip: 88623, timestamp: '2022-08-13T13:20:40.391Z', login: 'Y' };

  let ipAccessLog: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ip-access-logs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ip-access-logs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ip-access-logs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ipAccessLog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ip-access-logs/${ipAccessLog.id}`,
      }).then(() => {
        ipAccessLog = undefined;
      });
    }
  });

  it('IpAccessLogs menu should load IpAccessLogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ip-access-log');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IpAccessLog').should('exist');
    cy.url().should('match', ipAccessLogPageUrlPattern);
  });

  describe('IpAccessLog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ipAccessLogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IpAccessLog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ip-access-log/new$'));
        cy.getEntityCreateUpdateHeading('IpAccessLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ipAccessLogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ip-access-logs',
          body: ipAccessLogSample,
        }).then(({ body }) => {
          ipAccessLog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ip-access-logs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ip-access-logs?page=0&size=20>; rel="last",<http://localhost/api/ip-access-logs?page=0&size=20>; rel="first"',
              },
              body: [ipAccessLog],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ipAccessLogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IpAccessLog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ipAccessLog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ipAccessLogPageUrlPattern);
      });

      it('edit button click should load edit IpAccessLog page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IpAccessLog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ipAccessLogPageUrlPattern);
      });

      it('last delete button click should delete instance of IpAccessLog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('ipAccessLog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ipAccessLogPageUrlPattern);

        ipAccessLog = undefined;
      });
    });
  });

  describe('new IpAccessLog page', () => {
    beforeEach(() => {
      cy.visit(`${ipAccessLogPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IpAccessLog');
    });

    it('should create an instance of IpAccessLog', () => {
      cy.get(`[data-cy="ip"]`).type('22343').should('have.value', '22343');

      cy.get(`[data-cy="timestamp"]`).type('2022-08-13T12:30').should('have.value', '2022-08-13T12:30');

      cy.get(`[data-cy="login"]`).select('Y');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        ipAccessLog = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', ipAccessLogPageUrlPattern);
    });
  });
});
