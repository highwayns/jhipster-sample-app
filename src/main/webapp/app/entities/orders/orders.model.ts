import dayjs from 'dayjs/esm';
import { IOrderTypes } from 'app/entities/order-types/order-types.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { IOrderLog } from 'app/entities/order-log/order-log.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IOrders {
  id?: number;
  date?: dayjs.Dayjs;
  btc?: number;
  fiat?: number;
  btcPrice?: number;
  marketPrice?: YesNo;
  stopPrice?: number;
  orderType?: IOrderTypes | null;
  siteUser?: ISiteUsers | null;
  currency?: ICurrencies | null;
  logId?: IOrderLog | null;
}

export class Orders implements IOrders {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public btc?: number,
    public fiat?: number,
    public btcPrice?: number,
    public marketPrice?: YesNo,
    public stopPrice?: number,
    public orderType?: IOrderTypes | null,
    public siteUser?: ISiteUsers | null,
    public currency?: ICurrencies | null,
    public logId?: IOrderLog | null
  ) {}
}

export function getOrdersIdentifier(orders: IOrders): number | undefined {
  return orders.id;
}
