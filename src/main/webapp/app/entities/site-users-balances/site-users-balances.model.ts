import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';

export interface ISiteUsersBalances {
  id?: number;
  balance?: number;
  siteUser?: ISiteUsers | null;
  currency?: ICurrencies | null;
}

export class SiteUsersBalances implements ISiteUsersBalances {
  constructor(public id?: number, public balance?: number, public siteUser?: ISiteUsers | null, public currency?: ICurrencies | null) {}
}

export function getSiteUsersBalancesIdentifier(siteUsersBalances: ISiteUsersBalances): number | undefined {
  return siteUsersBalances.id;
}
