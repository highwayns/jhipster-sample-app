import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IBitcoinAddresses {
  id?: number;
  address?: string;
  date?: dayjs.Dayjs;
  systemAddress?: YesNo;
  hotWallet?: YesNo;
  warmWallet?: YesNo;
  siteUser?: ISiteUsers | null;
}

export class BitcoinAddresses implements IBitcoinAddresses {
  constructor(
    public id?: number,
    public address?: string,
    public date?: dayjs.Dayjs,
    public systemAddress?: YesNo,
    public hotWallet?: YesNo,
    public warmWallet?: YesNo,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getBitcoinAddressesIdentifier(bitcoinAddresses: IBitcoinAddresses): number | undefined {
  return bitcoinAddresses.id;
}
