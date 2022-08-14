import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';

export interface IBankAccounts {
  id?: number;
  accountNumber?: number;
  description?: string;
  siteUser?: ISiteUsers | null;
  currency?: ICurrencies | null;
}

export class BankAccounts implements IBankAccounts {
  constructor(
    public id?: number,
    public accountNumber?: number,
    public description?: string,
    public siteUser?: ISiteUsers | null,
    public currency?: ICurrencies | null
  ) {}
}

export function getBankAccountsIdentifier(bankAccounts: IBankAccounts): number | undefined {
  return bankAccounts.id;
}
