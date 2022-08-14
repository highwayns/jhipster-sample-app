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

describe('SiteUsers e2e test', () => {
  const siteUsersPageUrl = '/site-users';
  const siteUsersPageUrlPattern = new RegExp('/site-users(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const siteUsersSample = {
    pass: 'navigate port Cambridgeshire',
    firstName: 'Toney',
    lastName: 'Okuneva',
    email: 'Clementina.Buckridge@gmail.com',
    date: '2022-08-14T01:56:19.769Z',
    tel: 'Savings viral',
    user: 'SMTP Armenian Granite',
    countryCode: 898,
    authyRequested: 'Y',
    verifiedAuthy: 'N',
    authyId: 94215,
    usingSms: 'Y',
    dontAsk30Days: 'N',
    dontAskDate: '2022-08-14T00:23:07.769Z',
    confirmWithdrawalEmailBtc: 'Y',
    confirmWithdrawal2faBtc: 'N',
    confirmWithdrawal2faBank: 'Y',
    confirmWithdrawalEmailBank: 'Y',
    notifyDepositBtc: 'N',
    notifyDepositBank: 'Y',
    lastUpdate: '2022-08-13T10:37:05.605Z',
    noLogins: 'N',
    notifyLogin: 'Y',
    deactivated: 'N',
    locked: 'Y',
    google2faCode: 'grow invoice Program',
    verifiedGoogle: 'Y',
    lastLang: 'bypass',
    notifyWithdrawBtc: 'N',
    notifyWithdrawBank: 'N',
    trusted: 'Y',
  };

  let siteUsers: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/site-users+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/site-users').as('postEntityRequest');
    cy.intercept('DELETE', '/api/site-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (siteUsers) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/site-users/${siteUsers.id}`,
      }).then(() => {
        siteUsers = undefined;
      });
    }
  });

  it('SiteUsers menu should load SiteUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('site-users');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SiteUsers').should('exist');
    cy.url().should('match', siteUsersPageUrlPattern);
  });

  describe('SiteUsers page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(siteUsersPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SiteUsers page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/site-users/new$'));
        cy.getEntityCreateUpdateHeading('SiteUsers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/site-users',
          body: siteUsersSample,
        }).then(({ body }) => {
          siteUsers = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/site-users+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/site-users?page=0&size=20>; rel="last",<http://localhost/api/site-users?page=0&size=20>; rel="first"',
              },
              body: [siteUsers],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(siteUsersPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SiteUsers page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('siteUsers');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersPageUrlPattern);
      });

      it('edit button click should load edit SiteUsers page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SiteUsers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersPageUrlPattern);
      });

      it('last delete button click should delete instance of SiteUsers', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('siteUsers').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', siteUsersPageUrlPattern);

        siteUsers = undefined;
      });
    });
  });

  describe('new SiteUsers page', () => {
    beforeEach(() => {
      cy.visit(`${siteUsersPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SiteUsers');
    });

    it('should create an instance of SiteUsers', () => {
      cy.get(`[data-cy="pass"]`).type('synthesize Colorado Forward').should('have.value', 'synthesize Colorado Forward');

      cy.get(`[data-cy="firstName"]`).type('Melba').should('have.value', 'Melba');

      cy.get(`[data-cy="lastName"]`).type('Bahringer').should('have.value', 'Bahringer');

      cy.get(`[data-cy="email"]`).type('Camren.Cummerata77@yahoo.com').should('have.value', 'Camren.Cummerata77@yahoo.com');

      cy.get(`[data-cy="date"]`).type('2022-08-13T10:58').should('have.value', '2022-08-13T10:58');

      cy.get(`[data-cy="tel"]`).type('Unbranded').should('have.value', 'Unbranded');

      cy.get(`[data-cy="user"]`).type('AGP impactful Rial').should('have.value', 'AGP impactful Rial');

      cy.get(`[data-cy="countryCode"]`).type('90010').should('have.value', '90010');

      cy.get(`[data-cy="authyRequested"]`).select('N');

      cy.get(`[data-cy="verifiedAuthy"]`).select('N');

      cy.get(`[data-cy="authyId"]`).type('34117').should('have.value', '34117');

      cy.get(`[data-cy="usingSms"]`).select('N');

      cy.get(`[data-cy="dontAsk30Days"]`).select('N');

      cy.get(`[data-cy="dontAskDate"]`).type('2022-08-13T20:01').should('have.value', '2022-08-13T20:01');

      cy.get(`[data-cy="confirmWithdrawalEmailBtc"]`).select('N');

      cy.get(`[data-cy="confirmWithdrawal2faBtc"]`).select('Y');

      cy.get(`[data-cy="confirmWithdrawal2faBank"]`).select('Y');

      cy.get(`[data-cy="confirmWithdrawalEmailBank"]`).select('Y');

      cy.get(`[data-cy="notifyDepositBtc"]`).select('Y');

      cy.get(`[data-cy="notifyDepositBank"]`).select('Y');

      cy.get(`[data-cy="lastUpdate"]`).type('2022-08-14T01:39').should('have.value', '2022-08-14T01:39');

      cy.get(`[data-cy="noLogins"]`).select('N');

      cy.get(`[data-cy="notifyLogin"]`).select('Y');

      cy.get(`[data-cy="deactivated"]`).select('Y');

      cy.get(`[data-cy="locked"]`).select('N');

      cy.get(`[data-cy="google2faCode"]`).type('Practical').should('have.value', 'Practical');

      cy.get(`[data-cy="verifiedGoogle"]`).select('Y');

      cy.get(`[data-cy="lastLang"]`).type('primary').should('have.value', 'primary');

      cy.get(`[data-cy="notifyWithdrawBtc"]`).select('N');

      cy.get(`[data-cy="notifyWithdrawBank"]`).select('N');

      cy.get(`[data-cy="trusted"]`).select('N');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        siteUsers = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', siteUsersPageUrlPattern);
    });
  });
});
