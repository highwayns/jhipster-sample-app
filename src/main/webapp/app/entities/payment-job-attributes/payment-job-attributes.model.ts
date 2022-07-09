export interface IPaymentJobAttributes {
  id?: number;
  webhookUrl?: string | null;
  googleAnalyticsClientId?: string | null;
  allowedParentFrameDomains?: string | null;
  paymentPageReference?: string | null;
}

export class PaymentJobAttributes implements IPaymentJobAttributes {
  constructor(
    public id?: number,
    public webhookUrl?: string | null,
    public googleAnalyticsClientId?: string | null,
    public allowedParentFrameDomains?: string | null,
    public paymentPageReference?: string | null
  ) {}
}

export function getPaymentJobAttributesIdentifier(paymentJobAttributes: IPaymentJobAttributes): number | undefined {
  return paymentJobAttributes.id;
}
