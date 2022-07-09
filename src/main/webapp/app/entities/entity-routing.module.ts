import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'bank-account',
        data: { pageTitle: 'jhipsterSampleApplicationApp.bankAccount.home.title' },
        loadChildren: () => import('./bank-account/bank-account.module').then(m => m.BankAccountModule),
      },
      {
        path: 'label',
        data: { pageTitle: 'jhipsterSampleApplicationApp.label.home.title' },
        loadChildren: () => import('./label/label.module').then(m => m.LabelModule),
      },
      {
        path: 'operation',
        data: { pageTitle: 'jhipsterSampleApplicationApp.operation.home.title' },
        loadChildren: () => import('./operation/operation.module').then(m => m.OperationModule),
      },
      {
        path: 'abuse-report',
        data: { pageTitle: 'jhipsterSampleApplicationApp.abuseReport.home.title' },
        loadChildren: () => import('./abuse-report/abuse-report.module').then(m => m.AbuseReportModule),
      },
      {
        path: 'abuse-trigger',
        data: { pageTitle: 'jhipsterSampleApplicationApp.abuseTrigger.home.title' },
        loadChildren: () => import('./abuse-trigger/abuse-trigger.module').then(m => m.AbuseTriggerModule),
      },
      {
        path: 'parameters',
        data: { pageTitle: 'jhipsterSampleApplicationApp.parameters.home.title' },
        loadChildren: () => import('./parameters/parameters.module').then(m => m.ParametersModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'jhipsterSampleApplicationApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'capture',
        data: { pageTitle: 'jhipsterSampleApplicationApp.capture.home.title' },
        loadChildren: () => import('./capture/capture.module').then(m => m.CaptureModule),
      },
      {
        path: 'card-token-data',
        data: { pageTitle: 'jhipsterSampleApplicationApp.cardTokenData.home.title' },
        loadChildren: () => import('./card-token-data/card-token-data.module').then(m => m.CardTokenDataModule),
      },
      {
        path: 'entry',
        data: { pageTitle: 'jhipsterSampleApplicationApp.entry.home.title' },
        loadChildren: () => import('./entry/entry.module').then(m => m.EntryModule),
      },
      {
        path: 'error-report',
        data: { pageTitle: 'jhipsterSampleApplicationApp.errorReport.home.title' },
        loadChildren: () => import('./error-report/error-report.module').then(m => m.ErrorReportModule),
      },
      {
        path: 'identity',
        data: { pageTitle: 'jhipsterSampleApplicationApp.identity.home.title' },
        loadChildren: () => import('./identity/identity.module').then(m => m.IdentityModule),
      },
      {
        path: 'issuer',
        data: { pageTitle: 'jhipsterSampleApplicationApp.issuer.home.title' },
        loadChildren: () => import('./issuer/issuer.module').then(m => m.IssuerModule),
      },
      {
        path: 'links',
        data: { pageTitle: 'jhipsterSampleApplicationApp.links.home.title' },
        loadChildren: () => import('./links/links.module').then(m => m.LinksModule),
      },
      {
        path: 'link',
        data: { pageTitle: 'jhipsterSampleApplicationApp.link.home.title' },
        loadChildren: () => import('./link/link.module').then(m => m.LinkModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'jhipsterSampleApplicationApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'order-line',
        data: { pageTitle: 'jhipsterSampleApplicationApp.orderLine.home.title' },
        loadChildren: () => import('./order-line/order-line.module').then(m => m.OrderLineModule),
      },
      {
        path: 'payment',
        data: { pageTitle: 'jhipsterSampleApplicationApp.payment.home.title' },
        loadChildren: () => import('./payment/payment.module').then(m => m.PaymentModule),
      },
      {
        path: 'payment-attributes',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentAttributes.home.title' },
        loadChildren: () => import('./payment-attributes/payment-attributes.module').then(m => m.PaymentAttributesModule),
      },
      {
        path: 'payment-job-attributes',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentJobAttributes.home.title' },
        loadChildren: () => import('./payment-job-attributes/payment-job-attributes.module').then(m => m.PaymentJobAttributesModule),
      },
      {
        path: 'payment-job',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentJob.home.title' },
        loadChildren: () => import('./payment-job/payment-job.module').then(m => m.PaymentJobModule),
      },
      {
        path: 'payment-step',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentStep.home.title' },
        loadChildren: () => import('./payment-step/payment-step.module').then(m => m.PaymentStepModule),
      },
      {
        path: 'payment-methods',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentMethods.home.title' },
        loadChildren: () => import('./payment-methods/payment-methods.module').then(m => m.PaymentMethodsModule),
      },
      {
        path: 'payment-method-info',
        data: { pageTitle: 'jhipsterSampleApplicationApp.paymentMethodInfo.home.title' },
        loadChildren: () => import('./payment-method-info/payment-method-info.module').then(m => m.PaymentMethodInfoModule),
      },
      {
        path: 'refund',
        data: { pageTitle: 'jhipsterSampleApplicationApp.refund.home.title' },
        loadChildren: () => import('./refund/refund.module').then(m => m.RefundModule),
      },
      {
        path: 'refund-step',
        data: { pageTitle: 'jhipsterSampleApplicationApp.refundStep.home.title' },
        loadChildren: () => import('./refund-step/refund-step.module').then(m => m.RefundStepModule),
      },
      {
        path: 'result-attributes',
        data: { pageTitle: 'jhipsterSampleApplicationApp.resultAttributes.home.title' },
        loadChildren: () => import('./result-attributes/result-attributes.module').then(m => m.ResultAttributesModule),
      },
      {
        path: 'recurrence-criteria',
        data: { pageTitle: 'jhipsterSampleApplicationApp.recurrenceCriteria.home.title' },
        loadChildren: () => import('./recurrence-criteria/recurrence-criteria.module').then(m => m.RecurrenceCriteriaModule),
      },
      {
        path: 'currencys',
        data: { pageTitle: 'jhipsterSampleApplicationApp.currencys.home.title' },
        loadChildren: () => import('./currencys/currencys.module').then(m => m.CurrencysModule),
      },
      {
        path: 'tokenised-card',
        data: { pageTitle: 'jhipsterSampleApplicationApp.tokenisedCard.home.title' },
        loadChildren: () => import('./tokenised-card/tokenised-card.module').then(m => m.TokenisedCardModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
