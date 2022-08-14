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

describe('IsoCountries e2e test', () => {
  const isoCountriesPageUrl = '/iso-countries';
  const isoCountriesPageUrlPattern = new RegExp('/iso-countries(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const isoCountriesSample = { locale: 'Texas', code: 'in' };

  let isoCountries: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/iso-countries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/iso-countries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/iso-countries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (isoCountries) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/iso-countries/${isoCountries.id}`,
      }).then(() => {
        isoCountries = undefined;
      });
    }
  });

  it('IsoCountries menu should load IsoCountries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('iso-countries');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IsoCountries').should('exist');
    cy.url().should('match', isoCountriesPageUrlPattern);
  });

  describe('IsoCountries page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(isoCountriesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IsoCountries page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/iso-countries/new$'));
        cy.getEntityCreateUpdateHeading('IsoCountries');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', isoCountriesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/iso-countries',
          body: isoCountriesSample,
        }).then(({ body }) => {
          isoCountries = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/iso-countries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/iso-countries?page=0&size=20>; rel="last",<http://localhost/api/iso-countries?page=0&size=20>; rel="first"',
              },
              body: [isoCountries],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(isoCountriesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IsoCountries page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('isoCountries');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', isoCountriesPageUrlPattern);
      });

      it('edit button click should load edit IsoCountries page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IsoCountries');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', isoCountriesPageUrlPattern);
      });

      it('last delete button click should delete instance of IsoCountries', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('isoCountries').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', isoCountriesPageUrlPattern);

        isoCountries = undefined;
      });
    });
  });

  describe('new IsoCountries page', () => {
    beforeEach(() => {
      cy.visit(`${isoCountriesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IsoCountries');
    });

    it('should create an instance of IsoCountries', () => {
      cy.get(`[data-cy="locale"]`).type('compressin').should('have.value', 'compressin');

      cy.get(`[data-cy="code"]`).type('sy').should('have.value', 'sy');

      cy.get(`[data-cy="name"]`).type('COM transform').should('have.value', 'COM transform');

      cy.get(`[data-cy="prefix"]`).type('Division empowering').should('have.value', 'Division empowering');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        isoCountries = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', isoCountriesPageUrlPattern);
    });
  });
});
