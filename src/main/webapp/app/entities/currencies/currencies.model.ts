import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface ICurrencies {
  id?: number;
  currency?: string;
  faSymbol?: string;
  accountNumber?: number;
  accountName?: string;
  isActive?: YesNo;
  usdBid?: string;
  usdAsk?: string;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class Currencies implements ICurrencies {
  constructor(
    public id?: number,
    public currency?: string,
    public faSymbol?: string,
    public accountNumber?: number,
    public accountName?: string,
    public isActive?: YesNo,
    public usdBid?: string,
    public usdAsk?: string,
    public nameEn?: string,
    public nameEs?: string,
    public nameRu?: string,
    public nameZh?: string
  ) {}
}

export function getCurrenciesIdentifier(currencies: ICurrencies): number | undefined {
  return currencies.id;
}
