import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface IPaymentMethods {
  id?: number;
  paymentMethod?: PaymentMethod | null;
}

export class PaymentMethods implements IPaymentMethods {
  constructor(public id?: number, public paymentMethod?: PaymentMethod | null) {}
}

export function getPaymentMethodsIdentifier(paymentMethods: IPaymentMethods): number | undefined {
  return paymentMethods.id;
}
