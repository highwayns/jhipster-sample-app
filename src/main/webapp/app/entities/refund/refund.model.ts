import dayjs from 'dayjs/esm';
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
    public conversionRate?: number | null
  ) {}
}

export function getRefundIdentifier(refund: IRefund): number | undefined {
  return refund.id;
}
