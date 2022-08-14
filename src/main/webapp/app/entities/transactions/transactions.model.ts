import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { ITransactionTypes } from 'app/entities/transaction-types/transaction-types.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface ITransactions {
  id?: number;
  date?: dayjs.Dayjs;
  btc?: number;
  btcPrice?: number;
  fiat?: number;
  fee?: number;
  fee1?: number;
  btcNet?: number;
  btcNet1?: number;
  btcBefore1?: number;
  btcAfter1?: number;
  fiatBefore1?: number;
  fiatAfter1?: number;
  btcBefore?: number;
  btcAfter?: number;
  fiatBefore?: number;
  fiatAfter?: number;
  feeLevel?: number;
  feeLevel1?: number;
  origBtcPrice?: number;
  conversionFee?: number;
  convertAmount?: number;
  convertRateGiven?: number;
  convertSystemRate?: number;
  conversion?: YesNo;
  bidAtTransaction?: number;
  askAtTransaction?: number;
  factored?: YesNo;
  siteUser?: ISiteUsers | null;
  siteUser1?: ISiteUsers | null;
  transactionType?: ITransactionTypes | null;
  transactionType1?: ITransactionTypes | null;
  currency1?: ICurrencies | null;
  convertFromCurrency?: ICurrencies | null;
  convertToCurrency?: ICurrencies | null;
}

export class Transactions implements ITransactions {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public btc?: number,
    public btcPrice?: number,
    public fiat?: number,
    public fee?: number,
    public fee1?: number,
    public btcNet?: number,
    public btcNet1?: number,
    public btcBefore1?: number,
    public btcAfter1?: number,
    public fiatBefore1?: number,
    public fiatAfter1?: number,
    public btcBefore?: number,
    public btcAfter?: number,
    public fiatBefore?: number,
    public fiatAfter?: number,
    public feeLevel?: number,
    public feeLevel1?: number,
    public origBtcPrice?: number,
    public conversionFee?: number,
    public convertAmount?: number,
    public convertRateGiven?: number,
    public convertSystemRate?: number,
    public conversion?: YesNo,
    public bidAtTransaction?: number,
    public askAtTransaction?: number,
    public factored?: YesNo,
    public siteUser?: ISiteUsers | null,
    public siteUser1?: ISiteUsers | null,
    public transactionType?: ITransactionTypes | null,
    public transactionType1?: ITransactionTypes | null,
    public currency1?: ICurrencies | null,
    public convertFromCurrency?: ICurrencies | null,
    public convertToCurrency?: ICurrencies | null
  ) {}
}

export function getTransactionsIdentifier(transactions: ITransactions): number | undefined {
  return transactions.id;
}
