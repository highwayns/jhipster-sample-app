import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';

export interface IBitcoindLog {
  id?: number;
  transactionId?: string;
  amount?: number;
  date?: dayjs.Dayjs;
  siteUser?: ISiteUsers | null;
}

export class BitcoindLog implements IBitcoindLog {
  constructor(
    public id?: number,
    public transactionId?: string,
    public amount?: number,
    public date?: dayjs.Dayjs,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getBitcoindLogIdentifier(bitcoindLog: IBitcoindLog): number | undefined {
  return bitcoindLog.id;
}
