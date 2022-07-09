import { IPaymentMethodInfo } from 'app/entities/payment-method-info/payment-method-info.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface ICurrencys {
  id?: number;
  currency?: Currency | null;
  paymentMethodInfos?: IPaymentMethodInfo[] | null;
}

export class Currencys implements ICurrencys {
  constructor(public id?: number, public currency?: Currency | null, public paymentMethodInfos?: IPaymentMethodInfo[] | null) {}
}

export function getCurrencysIdentifier(currencys: ICurrencys): number | undefined {
  return currencys.id;
}
