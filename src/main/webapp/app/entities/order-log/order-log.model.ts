import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IOrderLog {
  id?: number;
  date?: dayjs.Dayjs;
  btc?: number;
  marketPrice?: YesNo;
  btcPrice?: number;
  fiat?: number;
  pId?: number;
  stopPrice?: string;
  status?: Status;
  btcRemaining?: number;
  siteUser?: ISiteUsers | null;
  currency?: ICurrencies | null;
  orderType?: IOrderTypes | null;
}

export class OrderLog implements IOrderLog {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public btc?: number,
    public marketPrice?: YesNo,
    public btcPrice?: number,
    public fiat?: number,
    public pId?: number,
    public stopPrice?: string,
    public status?: Status,
    public btcRemaining?: number,
    public siteUser?: ISiteUsers | null,
    public currency?: ICurrencies | null,
    public orderType?: IOrderTypes | null
  ) {}
}

export function getOrderLogIdentifier(orderLog: IOrderLog): number | undefined {
  return orderLog.id;
}
