import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { IRequestDescriptions } from 'app/entities/request-descriptions/request-descriptions.model';
import { IRequestStatus } from 'app/entities/request-status/request-status.model';
import { IRequestTypes } from 'app/entities/request-types/request-types.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IRequests {
  id?: number;
  date?: dayjs.Dayjs;
  amount?: number;
  addressId?: number;
  account?: number;
  sendAddress?: string;
  transactionId?: string;
  increment?: number;
  done?: YesNo;
  cryptoId?: number;
  fee?: number;
  netAmount?: number;
  notified?: number;
  siteUser?: ISiteUsers | null;
  currency?: ICurrencies | null;
  description?: IRequestDescriptions | null;
  requestStatus?: IRequestStatus | null;
  requestType?: IRequestTypes | null;
}

export class Requests implements IRequests {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public amount?: number,
    public addressId?: number,
    public account?: number,
    public sendAddress?: string,
    public transactionId?: string,
    public increment?: number,
    public done?: YesNo,
    public cryptoId?: number,
    public fee?: number,
    public netAmount?: number,
    public notified?: number,
    public siteUser?: ISiteUsers | null,
    public currency?: ICurrencies | null,
    public description?: IRequestDescriptions | null,
    public requestStatus?: IRequestStatus | null,
    public requestType?: IRequestTypes | null
  ) {}
}

export function getRequestsIdentifier(requests: IRequests): number | undefined {
  return requests.id;
}
