import dayjs from 'dayjs/esm';
import { IErrorReport } from 'app/entities/error-report/error-report.model';
import { IAbuseReport } from 'app/entities/abuse-report/abuse-report.model';
import { IPaymentAttributes } from 'app/entities/payment-attributes/payment-attributes.model';
import { IPaymentJob } from 'app/entities/payment-job/payment-job.model';
import { IPaymentMethods } from 'app/entities/payment-methods/payment-methods.model';
import { IPaymentStep } from 'app/entities/payment-step/payment-step.model';
import { IRefund } from 'app/entities/refund/refund.model';
import { ICapture } from 'app/entities/capture/capture.model';
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
  paymentJobs?: IPaymentJob[] | null;
  paymentMethods?: IPaymentMethods | null;
  steps?: IPaymentStep | null;
  refunds?: IRefund | null;
  captures?: ICapture | null;
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
    public attributes?: IPaymentAttributes | null,
    public paymentJobs?: IPaymentJob[] | null,
    public paymentMethods?: IPaymentMethods | null,
    public steps?: IPaymentStep | null,
    public refunds?: IRefund | null,
    public captures?: ICapture | null
  ) {}
}

export function getPaymentIdentifier(payment: IPayment): number | undefined {
  return payment.id;
}
