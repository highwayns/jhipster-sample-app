import dayjs from 'dayjs/esm';
import { IErrorReport } from 'app/entities/error-report/error-report.model';
import { IAbuseReport } from 'app/entities/abuse-report/abuse-report.model';
import { IPaymentAttributes } from 'app/entities/payment-attributes/payment-attributes.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface IPayment {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  status?: PaymentStatus | null;
  amountToCollect?: number | null;
  surchargeAmount?: number | null;
  convertedTotalAmount?: number | null;
  convertedCurrency?: Currency | null;
  conversionRate?: number | null;
  paidAmount?: number | null;
  lastErrorReport?: IErrorReport | null;
  abuseReport?: IAbuseReport | null;
  attributes?: IPaymentAttributes | null;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public status?: PaymentStatus | null,
    public amountToCollect?: number | null,
    public surchargeAmount?: number | null,
    public convertedTotalAmount?: number | null,
    public convertedCurrency?: Currency | null,
    public conversionRate?: number | null,
    public paidAmount?: number | null,
    public lastErrorReport?: IErrorReport | null,
    public abuseReport?: IAbuseReport | null,
    public attributes?: IPaymentAttributes | null
  ) {}
}

export function getPaymentIdentifier(payment: IPayment): number | undefined {
  return payment.id;
}
