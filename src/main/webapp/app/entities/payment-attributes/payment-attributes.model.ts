import { PaymentStatus } from 'app/entities/enumerations/payment-status.model';

export interface IPaymentAttributes {
  id?: number;
  originatingIpAddress?: string | null;
  originHeader?: string | null;
  userAgent?: string | null;
  returnUrlSuccess?: string | null;
  returnUrlFailed?: string | null;
  returnUrlCancelled?: string | null;
  simulatedStatus?: string | null;
  idealBic?: string | null;
  paymentMethodTransactionId?: string | null;
  paymentMethodVoidTransactionId?: string | null;
  token?: string | null;
  cashFlowsAcquiringDetails?: string | null;
  descriptor?: string | null;
  ewalletType?: string | null;
  paymentStatus?: PaymentStatus | null;
}

export class PaymentAttributes implements IPaymentAttributes {
  constructor(
    public id?: number,
    public originatingIpAddress?: string | null,
    public originHeader?: string | null,
    public userAgent?: string | null,
    public returnUrlSuccess?: string | null,
    public returnUrlFailed?: string | null,
    public returnUrlCancelled?: string | null,
    public simulatedStatus?: string | null,
    public idealBic?: string | null,
    public paymentMethodTransactionId?: string | null,
    public paymentMethodVoidTransactionId?: string | null,
    public token?: string | null,
    public cashFlowsAcquiringDetails?: string | null,
    public descriptor?: string | null,
    public ewalletType?: string | null,
    public paymentStatus?: PaymentStatus | null
  ) {}
}

export function getPaymentAttributesIdentifier(paymentAttributes: IPaymentAttributes): number | undefined {
  return paymentAttributes.id;
}
