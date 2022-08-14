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
      {
        path: 'admin-controls',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminControls.home.title' },
        loadChildren: () => import('./admin-controls/admin-controls.module').then(m => m.AdminControlsModule),
      },
      {
        path: 'admin-controls-methods',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminControlsMethods.home.title' },
        loadChildren: () => import('./admin-controls-methods/admin-controls-methods.module').then(m => m.AdminControlsMethodsModule),
      },
      {
        path: 'admin-cron',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminCron.home.title' },
        loadChildren: () => import('./admin-cron/admin-cron.module').then(m => m.AdminCronModule),
      },
      {
        path: 'admin-groups',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminGroups.home.title' },
        loadChildren: () => import('./admin-groups/admin-groups.module').then(m => m.AdminGroupsModule),
      },
      {
        path: 'admin-groups-pages',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminGroupsPages.home.title' },
        loadChildren: () => import('./admin-groups-pages/admin-groups-pages.module').then(m => m.AdminGroupsPagesModule),
      },
      {
        path: 'admin-groups-tabs',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminGroupsTabs.home.title' },
        loadChildren: () => import('./admin-groups-tabs/admin-groups-tabs.module').then(m => m.AdminGroupsTabsModule),
      },
      {
        path: 'admin-image-sizes',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminImageSizes.home.title' },
        loadChildren: () => import('./admin-image-sizes/admin-image-sizes.module').then(m => m.AdminImageSizesModule),
      },
      {
        path: 'admin-order',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminOrder.home.title' },
        loadChildren: () => import('./admin-order/admin-order.module').then(m => m.AdminOrderModule),
      },
      {
        path: 'admin-pages',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminPages.home.title' },
        loadChildren: () => import('./admin-pages/admin-pages.module').then(m => m.AdminPagesModule),
      },
      {
        path: 'admin-sessions',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminSessions.home.title' },
        loadChildren: () => import('./admin-sessions/admin-sessions.module').then(m => m.AdminSessionsModule),
      },
      {
        path: 'admin-tabs',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminTabs.home.title' },
        loadChildren: () => import('./admin-tabs/admin-tabs.module').then(m => m.AdminTabsModule),
      },
      {
        path: 'admin-users',
        data: { pageTitle: 'jhipsterSampleApplicationApp.adminUsers.home.title' },
        loadChildren: () => import('./admin-users/admin-users.module').then(m => m.AdminUsersModule),
      },
      {
        path: 'api-keys',
        data: { pageTitle: 'jhipsterSampleApplicationApp.apiKeys.home.title' },
        loadChildren: () => import('./api-keys/api-keys.module').then(m => m.ApiKeysModule),
      },
      {
        path: 'app-configuration',
        data: { pageTitle: 'jhipsterSampleApplicationApp.appConfiguration.home.title' },
        loadChildren: () => import('./app-configuration/app-configuration.module').then(m => m.AppConfigurationModule),
      },
      {
        path: 'bank-accounts',
        data: { pageTitle: 'jhipsterSampleApplicationApp.bankAccounts.home.title' },
        loadChildren: () => import('./bank-accounts/bank-accounts.module').then(m => m.BankAccountsModule),
      },
      {
        path: 'bitcoin-addresses',
        data: { pageTitle: 'jhipsterSampleApplicationApp.bitcoinAddresses.home.title' },
        loadChildren: () => import('./bitcoin-addresses/bitcoin-addresses.module').then(m => m.BitcoinAddressesModule),
      },
      {
        path: 'bitcoind-log',
        data: { pageTitle: 'jhipsterSampleApplicationApp.bitcoindLog.home.title' },
        loadChildren: () => import('./bitcoind-log/bitcoind-log.module').then(m => m.BitcoindLogModule),
      },
      {
        path: 'change-settings',
        data: { pageTitle: 'jhipsterSampleApplicationApp.changeSettings.home.title' },
        loadChildren: () => import('./change-settings/change-settings.module').then(m => m.ChangeSettingsModule),
      },
      {
        path: 'content',
        data: { pageTitle: 'jhipsterSampleApplicationApp.content.home.title' },
        loadChildren: () => import('./content/content.module').then(m => m.ContentModule),
      },
      {
        path: 'content-files',
        data: { pageTitle: 'jhipsterSampleApplicationApp.contentFiles.home.title' },
        loadChildren: () => import('./content-files/content-files.module').then(m => m.ContentFilesModule),
      },
      {
        path: 'conversions',
        data: { pageTitle: 'jhipsterSampleApplicationApp.conversions.home.title' },
        loadChildren: () => import('./conversions/conversions.module').then(m => m.ConversionsModule),
      },
      {
        path: 'currencies',
        data: { pageTitle: 'jhipsterSampleApplicationApp.currencies.home.title' },
        loadChildren: () => import('./currencies/currencies.module').then(m => m.CurrenciesModule),
      },
      {
        path: 'current-stats',
        data: { pageTitle: 'jhipsterSampleApplicationApp.currentStats.home.title' },
        loadChildren: () => import('./current-stats/current-stats.module').then(m => m.CurrentStatsModule),
      },
      {
        path: 'daily-reports',
        data: { pageTitle: 'jhipsterSampleApplicationApp.dailyReports.home.title' },
        loadChildren: () => import('./daily-reports/daily-reports.module').then(m => m.DailyReportsModule),
      },
      {
        path: 'emails',
        data: { pageTitle: 'jhipsterSampleApplicationApp.emails.home.title' },
        loadChildren: () => import('./emails/emails.module').then(m => m.EmailsModule),
      },
      {
        path: 'fee-schedule',
        data: { pageTitle: 'jhipsterSampleApplicationApp.feeSchedule.home.title' },
        loadChildren: () => import('./fee-schedule/fee-schedule.module').then(m => m.FeeScheduleModule),
      },
      {
        path: 'fees',
        data: { pageTitle: 'jhipsterSampleApplicationApp.fees.home.title' },
        loadChildren: () => import('./fees/fees.module').then(m => m.FeesModule),
      },
      {
        path: 'historical-data',
        data: { pageTitle: 'jhipsterSampleApplicationApp.historicalData.home.title' },
        loadChildren: () => import('./historical-data/historical-data.module').then(m => m.HistoricalDataModule),
      },
      {
        path: 'history',
        data: { pageTitle: 'jhipsterSampleApplicationApp.history.home.title' },
        loadChildren: () => import('./history/history.module').then(m => m.HistoryModule),
      },
      {
        path: 'history-actions',
        data: { pageTitle: 'jhipsterSampleApplicationApp.historyActions.home.title' },
        loadChildren: () => import('./history-actions/history-actions.module').then(m => m.HistoryActionsModule),
      },
      {
        path: 'innodb-lock-monitor',
        data: { pageTitle: 'jhipsterSampleApplicationApp.innodbLockMonitor.home.title' },
        loadChildren: () => import('./innodb-lock-monitor/innodb-lock-monitor.module').then(m => m.InnodbLockMonitorModule),
      },
      {
        path: 'ip-access-log',
        data: { pageTitle: 'jhipsterSampleApplicationApp.ipAccessLog.home.title' },
        loadChildren: () => import('./ip-access-log/ip-access-log.module').then(m => m.IpAccessLogModule),
      },
      {
        path: 'iso-countries',
        data: { pageTitle: 'jhipsterSampleApplicationApp.isoCountries.home.title' },
        loadChildren: () => import('./iso-countries/iso-countries.module').then(m => m.IsoCountriesModule),
      },
      {
        path: 'lang',
        data: { pageTitle: 'jhipsterSampleApplicationApp.lang.home.title' },
        loadChildren: () => import('./lang/lang.module').then(m => m.LangModule),
      },
      {
        path: 'monthly-reports',
        data: { pageTitle: 'jhipsterSampleApplicationApp.monthlyReports.home.title' },
        loadChildren: () => import('./monthly-reports/monthly-reports.module').then(m => m.MonthlyReportsModule),
      },
      {
        path: 'news',
        data: { pageTitle: 'jhipsterSampleApplicationApp.news.home.title' },
        loadChildren: () => import('./news/news.module').then(m => m.NewsModule),
      },
      {
        path: 'order-log',
        data: { pageTitle: 'jhipsterSampleApplicationApp.orderLog.home.title' },
        loadChildren: () => import('./order-log/order-log.module').then(m => m.OrderLogModule),
      },
      {
        path: 'order-types',
        data: { pageTitle: 'jhipsterSampleApplicationApp.orderTypes.home.title' },
        loadChildren: () => import('./order-types/order-types.module').then(m => m.OrderTypesModule),
      },
      {
        path: 'orders',
        data: { pageTitle: 'jhipsterSampleApplicationApp.orders.home.title' },
        loadChildren: () => import('./orders/orders.module').then(m => m.OrdersModule),
      },
      {
        path: 'request-descriptions',
        data: { pageTitle: 'jhipsterSampleApplicationApp.requestDescriptions.home.title' },
        loadChildren: () => import('./request-descriptions/request-descriptions.module').then(m => m.RequestDescriptionsModule),
      },
      {
        path: 'request-status',
        data: { pageTitle: 'jhipsterSampleApplicationApp.requestStatus.home.title' },
        loadChildren: () => import('./request-status/request-status.module').then(m => m.RequestStatusModule),
      },
      {
        path: 'request-types',
        data: { pageTitle: 'jhipsterSampleApplicationApp.requestTypes.home.title' },
        loadChildren: () => import('./request-types/request-types.module').then(m => m.RequestTypesModule),
      },
      {
        path: 'requests',
        data: { pageTitle: 'jhipsterSampleApplicationApp.requests.home.title' },
        loadChildren: () => import('./requests/requests.module').then(m => m.RequestsModule),
      },
      {
        path: 'settings-files',
        data: { pageTitle: 'jhipsterSampleApplicationApp.settingsFiles.home.title' },
        loadChildren: () => import('./settings-files/settings-files.module').then(m => m.SettingsFilesModule),
      },
      {
        path: 'site-users',
        data: { pageTitle: 'jhipsterSampleApplicationApp.siteUsers.home.title' },
        loadChildren: () => import('./site-users/site-users.module').then(m => m.SiteUsersModule),
      },
      {
        path: 'site-users-access',
        data: { pageTitle: 'jhipsterSampleApplicationApp.siteUsersAccess.home.title' },
        loadChildren: () => import('./site-users-access/site-users-access.module').then(m => m.SiteUsersAccessModule),
      },
      {
        path: 'site-users-balances',
        data: { pageTitle: 'jhipsterSampleApplicationApp.siteUsersBalances.home.title' },
        loadChildren: () => import('./site-users-balances/site-users-balances.module').then(m => m.SiteUsersBalancesModule),
      },
      {
        path: 'site-users-catch',
        data: { pageTitle: 'jhipsterSampleApplicationApp.siteUsersCatch.home.title' },
        loadChildren: () => import('./site-users-catch/site-users-catch.module').then(m => m.SiteUsersCatchModule),
      },
      {
        path: 'status',
        data: { pageTitle: 'jhipsterSampleApplicationApp.status.home.title' },
        loadChildren: () => import('./status/status.module').then(m => m.StatusModule),
      },
      {
        path: 'status-escrows',
        data: { pageTitle: 'jhipsterSampleApplicationApp.statusEscrows.home.title' },
        loadChildren: () => import('./status-escrows/status-escrows.module').then(m => m.StatusEscrowsModule),
      },
      {
        path: 'transaction-types',
        data: { pageTitle: 'jhipsterSampleApplicationApp.transactionTypes.home.title' },
        loadChildren: () => import('./transaction-types/transaction-types.module').then(m => m.TransactionTypesModule),
      },
      {
        path: 'transactions',
        data: { pageTitle: 'jhipsterSampleApplicationApp.transactions.home.title' },
        loadChildren: () => import('./transactions/transactions.module').then(m => m.TransactionsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
