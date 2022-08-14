import dayjs from 'dayjs/esm';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IConversions {
  id?: number;
  amount?: number;
  date?: dayjs.Dayjs;
  isActive?: YesNo;
  totalWithdrawals?: number;
  profitToFactor?: number;
  factored?: YesNo;
  date1?: dayjs.Dayjs;
  currency?: ICurrencies | null;
}

export class Conversions implements IConversions {
  constructor(
    public id?: number,
    public amount?: number,
    public date?: dayjs.Dayjs,
    public isActive?: YesNo,
    public totalWithdrawals?: number,
    public profitToFactor?: number,
    public factored?: YesNo,
    public date1?: dayjs.Dayjs,
    public currency?: ICurrencies | null
  ) {}
}

export function getConversionsIdentifier(conversions: IConversions): number | undefined {
  return conversions.id;
}
