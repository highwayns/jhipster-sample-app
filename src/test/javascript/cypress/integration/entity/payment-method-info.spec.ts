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

describe('PaymentMethodInfo e2e test', () => {
  const paymentMethodInfoPageUrl = '/payment-method-info';
  const paymentMethodInfoPageUrlPattern = new RegExp('/payment-method-info(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentMethodInfoSample = {};

  let paymentMethodInfo: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/payment-method-infos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/payment-method-infos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/payment-method-infos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (paymentMethodInfo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/payment-method-infos/${paymentMethodInfo.id}`,
      }).then(() => {
        paymentMethodInfo = undefined;
      });
    }
  });

  it('PaymentMethodInfos menu should load PaymentMethodInfos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('payment-method-info');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PaymentMethodInfo').should('exist');
    cy.url().should('match', paymentMethodInfoPageUrlPattern);
  });

  describe('PaymentMethodInfo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentMethodInfoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PaymentMethodInfo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/payment-method-info/new$'));
        cy.getEntityCreateUpdateHeading('PaymentMethodInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodInfoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/payment-method-infos',
          body: paymentMethodInfoSample,
        }).then(({ body }) => {
          paymentMethodInfo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/payment-method-infos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [paymentMethodInfo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentMethodInfoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PaymentMethodInfo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('paymentMethodInfo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodInfoPageUrlPattern);
      });

      it('edit button click should load edit PaymentMethodInfo page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PaymentMethodInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodInfoPageUrlPattern);
      });

      it('last delete button click should delete instance of PaymentMethodInfo', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('paymentMethodInfo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentMethodInfoPageUrlPattern);

        paymentMethodInfo = undefined;
      });
    });
  });

  describe('new PaymentMethodInfo page', () => {
    beforeEach(() => {
      cy.visit(`${paymentMethodInfoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PaymentMethodInfo');
    });

    it('should create an instance of PaymentMethodInfo', () => {
      cy.get(`[data-cy="paymentMethod"]`).type('Generic strategize Avon').should('have.value', 'Generic strategize Avon');

      cy.get(`[data-cy="logo"]`).type('Louisiana').should('have.value', 'Louisiana');

      cy.get(`[data-cy="supportsTokenisation"]`).should('not.be.checked');
      cy.get(`[data-cy="supportsTokenisation"]`).click().should('be.checked');

      cy.get(`[data-cy="currencies"]`).select('ETH');

      cy.get(`[data-cy="surchargeAmount"]`).type('61004').should('have.value', '61004');

      cy.get(`[data-cy="surchargeAmountExclVat"]`).type('11132').should('have.value', '11132');

      cy.get(`[data-cy="surchargeAmountVat"]`).type('13302').should('have.value', '13302');

      cy.get(`[data-cy="surchargeVatPercentage"]`).type('93749').should('have.value', '93749');

      cy.get(`[data-cy="description"]`).type('deposit Configurable Awesome').should('have.value', 'deposit Configurable Awesome');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        paymentMethodInfo = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentMethodInfoPageUrlPattern);
    });
  });
});
