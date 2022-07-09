import dayjs from 'dayjs/esm';
import { PaymentStepAction } from 'app/entities/enumerations/payment-step-action.model';
import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

export interface IPaymentStep {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  action?: PaymentStepAction | null;
  status?: PaymentStatus | null;
  amountToCollect?: number | null;
}

export class PaymentStep implements IPaymentStep {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public action?: PaymentStepAction | null,
    public status?: PaymentStatus | null,
    public amountToCollect?: number | null
  ) {}
}

export function getPaymentStepIdentifier(paymentStep: IPaymentStep): number | undefined {
  return paymentStep.id;
}
