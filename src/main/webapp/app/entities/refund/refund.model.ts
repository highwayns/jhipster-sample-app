import dayjs from 'dayjs/esm';
import { IPayment } from 'app/entities/payment/payment.model';
import { IRefundStep } from 'app/entities/refund-step/refund-step.model';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface IRefund {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  refundNumber?: string | null;
  status?: RefundStatus | null;
  amountToRefund?: number | null;
  convertedAmountToRefund?: number | null;
  convertedCurrency?: Currency | null;
  conversionRate?: number | null;
  payments?: IPayment[] | null;
  steps?: IRefundStep | null;
}

export class Refund implements IRefund {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public refundNumber?: string | null,
    public status?: RefundStatus | null,
    public amountToRefund?: number | null,
    public convertedAmountToRefund?: number | null,
    public convertedCurrency?: Currency | null,
    public conversionRate?: number | null,
    public payments?: IPayment[] | null,
    public steps?: IRefundStep | null
  ) {}
}

export function getRefundIdentifier(refund: IRefund): number | undefined {
  return refund.id;
}
