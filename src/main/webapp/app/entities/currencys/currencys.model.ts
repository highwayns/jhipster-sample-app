import { Currency } from 'app/entities/enumerations/currency.model';

export interface ICurrencys {
  id?: number;
  currency?: Currency | null;
}

export class Currencys implements ICurrencys {
  constructor(public id?: number, public currency?: Currency | null) {}
}

export function getCurrencysIdentifier(currencys: ICurrencys): number | undefined {
  return currencys.id;
}
