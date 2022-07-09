import dayjs from 'dayjs/esm';
import { IOrder } from 'app/entities/order/order.model';
import { IPaymentJobAttributes } from 'app/entities/payment-job-attributes/payment-job-attributes.model';
import { IRecurrenceCriteria } from 'app/entities/recurrence-criteria/recurrence-criteria.model';
import { PaymentJobType } from 'app/entities/enumerations/payment-job-type.model';
import { Locale } from 'app/entities/enumerations/locale.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface IPaymentJob {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  type?: PaymentJobType | null;
  traceReference?: number | null;
  configurationId?: string | null;
  domain?: string | null;
  locale?: Locale | null;
  timeZone?: string | null;
  parentPaymentJobReference?: number | null;
  displayUrl?: string | null;
  currency?: Currency | null;
  amountToCollect?: number | null;
  amountCollected?: number | null;
  paidAmount?: number | null;
  paidDateTimeUtc?: dayjs.Dayjs | null;
  expirationDateTimeUtc?: dayjs.Dayjs | null;
  dueDateTimeUtc?: dayjs.Dayjs | null;
  lastUpdateTimeUtc?: dayjs.Dayjs | null;
  lastProcessedTimeUtc?: dayjs.Dayjs | null;
  order?: IOrder | null;
  attributes?: IPaymentJobAttributes | null;
  recurrenceCriteria?: IRecurrenceCriteria | null;
}

export class PaymentJob implements IPaymentJob {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public type?: PaymentJobType | null,
    public traceReference?: number | null,
    public configurationId?: string | null,
    public domain?: string | null,
    public locale?: Locale | null,
    public timeZone?: string | null,
    public parentPaymentJobReference?: number | null,
    public displayUrl?: string | null,
    public currency?: Currency | null,
    public amountToCollect?: number | null,
    public amountCollected?: number | null,
    public paidAmount?: number | null,
    public paidDateTimeUtc?: dayjs.Dayjs | null,
    public expirationDateTimeUtc?: dayjs.Dayjs | null,
    public dueDateTimeUtc?: dayjs.Dayjs | null,
    public lastUpdateTimeUtc?: dayjs.Dayjs | null,
    public lastProcessedTimeUtc?: dayjs.Dayjs | null,
    public order?: IOrder | null,
    public attributes?: IPaymentJobAttributes | null,
    public recurrenceCriteria?: IRecurrenceCriteria | null
  ) {}
}

export function getPaymentJobIdentifier(paymentJob: IPaymentJob): number | undefined {
  return paymentJob.id;
}
