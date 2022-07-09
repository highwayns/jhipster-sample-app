import { IPaymentMethodInfo } from 'app/entities/payment-method-info/payment-method-info.model';

export interface IIssuer {
  id?: string;
  name?: string | null;
  paymentMethodInfos?: IPaymentMethodInfo[] | null;
}

export class Issuer implements IIssuer {
  constructor(public id?: string, public name?: string | null, public paymentMethodInfos?: IPaymentMethodInfo[] | null) {}
}

export function getIssuerIdentifier(issuer: IIssuer): string | undefined {
  return issuer.id;
}
