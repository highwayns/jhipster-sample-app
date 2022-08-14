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

describe('SettingsFiles e2e test', () => {
  const settingsFilesPageUrl = '/settings-files';
  const settingsFilesPageUrlPattern = new RegExp('/settings-files(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const settingsFilesSample = {
    fId: 89228,
    ext: 'tran',
    dir: 'Lira Intranet',
    url: 'https://zora.net',
    oldName: 'neural-net Shoes',
    fieldName: 'Tuna Distributed Dynamic',
  };

  let settingsFiles: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/settings-files+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/settings-files').as('postEntityRequest');
    cy.intercept('DELETE', '/api/settings-files/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (settingsFiles) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/settings-files/${settingsFiles.id}`,
      }).then(() => {
        settingsFiles = undefined;
      });
    }
  });

  it('SettingsFiles menu should load SettingsFiles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('settings-files');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SettingsFiles').should('exist');
    cy.url().should('match', settingsFilesPageUrlPattern);
  });

  describe('SettingsFiles page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(settingsFilesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SettingsFiles page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/settings-files/new$'));
        cy.getEntityCreateUpdateHeading('SettingsFiles');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', settingsFilesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/settings-files',
          body: settingsFilesSample,
        }).then(({ body }) => {
          settingsFiles = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/settings-files+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/settings-files?page=0&size=20>; rel="last",<http://localhost/api/settings-files?page=0&size=20>; rel="first"',
              },
              body: [settingsFiles],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(settingsFilesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SettingsFiles page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('settingsFiles');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', settingsFilesPageUrlPattern);
      });

      it('edit button click should load edit SettingsFiles page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SettingsFiles');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', settingsFilesPageUrlPattern);
      });

      it('last delete button click should delete instance of SettingsFiles', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('settingsFiles').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', settingsFilesPageUrlPattern);

        settingsFiles = undefined;
      });
    });
  });

  describe('new SettingsFiles page', () => {
    beforeEach(() => {
      cy.visit(`${settingsFilesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SettingsFiles');
    });

    it('should create an instance of SettingsFiles', () => {
      cy.get(`[data-cy="fId"]`).type('29721').should('have.value', '29721');

      cy.get(`[data-cy="ext"]`).type('HDD').should('have.value', 'HDD');

      cy.get(`[data-cy="dir"]`).type('Up-sized Viaduct lavender').should('have.value', 'Up-sized Viaduct lavender');

      cy.get(`[data-cy="url"]`).type('https://cecil.name').should('have.value', 'https://cecil.name');

      cy.get(`[data-cy="oldName"]`).type('CSS turquoise deposit').should('have.value', 'CSS turquoise deposit');

      cy.get(`[data-cy="fieldName"]`).type('Planner National Granite').should('have.value', 'Planner National Granite');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        settingsFiles = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', settingsFilesPageUrlPattern);
    });
  });
});
