import { IPayment } from 'app/entities/payment/payment.model';
import { IPaymentStep } from 'app/entities/payment-step/payment-step.model';
import { IPaymentJob } from 'app/entities/payment-job/payment-job.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface IPaymentMethods {
  id?: number;
  paymentMethod?: PaymentMethod | null;
  payments?: IPayment[] | null;
  paymentSteps?: IPaymentStep[] | null;
  paymentJobs?: IPaymentJob[] | null;
}

export class PaymentMethods implements IPaymentMethods {
  constructor(
    public id?: number,
    public paymentMethod?: PaymentMethod | null,
    public payments?: IPayment[] | null,
    public paymentSteps?: IPaymentStep[] | null,
    public paymentJobs?: IPaymentJob[] | null
  ) {}
}

export function getPaymentMethodsIdentifier(paymentMethods: IPaymentMethods): number | undefined {
  return paymentMethods.id;
}
