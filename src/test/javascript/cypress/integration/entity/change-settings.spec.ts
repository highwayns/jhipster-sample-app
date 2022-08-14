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

describe('ChangeSettings e2e test', () => {
  const changeSettingsPageUrl = '/change-settings';
  const changeSettingsPageUrlPattern = new RegExp('/change-settings(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const changeSettingsSample = {
    request: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    requestContentType: 'unknown',
    date: '2022-08-14T00:11:04.825Z',
  };

  let changeSettings: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/change-settings+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/change-settings').as('postEntityRequest');
    cy.intercept('DELETE', '/api/change-settings/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (changeSettings) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/change-settings/${changeSettings.id}`,
      }).then(() => {
        changeSettings = undefined;
      });
    }
  });

  it('ChangeSettings menu should load ChangeSettings page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('change-settings');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ChangeSettings').should('exist');
    cy.url().should('match', changeSettingsPageUrlPattern);
  });

  describe('ChangeSettings page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(changeSettingsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ChangeSettings page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/change-settings/new$'));
        cy.getEntityCreateUpdateHeading('ChangeSettings');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', changeSettingsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/change-settings',
          body: changeSettingsSample,
        }).then(({ body }) => {
          changeSettings = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/change-settings+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/change-settings?page=0&size=20>; rel="last",<http://localhost/api/change-settings?page=0&size=20>; rel="first"',
              },
              body: [changeSettings],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(changeSettingsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ChangeSettings page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('changeSettings');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', changeSettingsPageUrlPattern);
      });

      it('edit button click should load edit ChangeSettings page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ChangeSettings');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', changeSettingsPageUrlPattern);
      });

      it('last delete button click should delete instance of ChangeSettings', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('changeSettings').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', changeSettingsPageUrlPattern);

        changeSettings = undefined;
      });
    });
  });

  describe('new ChangeSettings page', () => {
    beforeEach(() => {
      cy.visit(`${changeSettingsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ChangeSettings');
    });

    it('should create an instance of ChangeSettings', () => {
      cy.setFieldImageAsBytesOfEntity('request', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="date"]`).type('2022-08-13T09:35').should('have.value', '2022-08-13T09:35');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        changeSettings = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', changeSettingsPageUrlPattern);
    });
  });
});
