import dayjs from 'dayjs/esm';
import { IHistoryActions } from 'app/entities/history-actions/history-actions.model';
import { IOrders } from 'app/entities/orders/orders.model';
import { IRequests } from 'app/entities/requests/requests.model';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';

export interface IHistory {
  id?: number;
  date?: dayjs.Dayjs;
  ip?: string;
  bitcoinAddress?: string;
  balanceBefore?: number;
  balanceAfter?: number;
  historyAction?: IHistoryActions | null;
  orderId?: IOrders | null;
  requestId?: IRequests | null;
  siteUser?: ISiteUsers | null;
}

export class History implements IHistory {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public ip?: string,
    public bitcoinAddress?: string,
    public balanceBefore?: number,
    public balanceAfter?: number,
    public historyAction?: IHistoryActions | null,
    public orderId?: IOrders | null,
    public requestId?: IRequests | null,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getHistoryIdentifier(history: IHistory): number | undefined {
  return history.id;
}
