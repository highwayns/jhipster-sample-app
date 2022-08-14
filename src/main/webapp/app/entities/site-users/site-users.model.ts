import dayjs from 'dayjs/esm';
import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IFeeSchedule } from 'app/entities/fee-schedule/fee-schedule.model';
import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface ISiteUsers {
  id?: number;
  pass?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  date?: dayjs.Dayjs;
  tel?: string;
  user?: string;
  countryCode?: number;
  authyRequested?: YesNo;
  verifiedAuthy?: YesNo;
  authyId?: number;
  usingSms?: YesNo;
  dontAsk30Days?: YesNo;
  dontAskDate?: dayjs.Dayjs;
  confirmWithdrawalEmailBtc?: YesNo;
  confirmWithdrawal2faBtc?: YesNo;
  confirmWithdrawal2faBank?: YesNo;
  confirmWithdrawalEmailBank?: YesNo;
  notifyDepositBtc?: YesNo;
  notifyDepositBank?: YesNo;
  lastUpdate?: dayjs.Dayjs;
  noLogins?: YesNo;
  notifyLogin?: YesNo;
  deactivated?: YesNo;
  locked?: YesNo;
  google2faCode?: string;
  verifiedGoogle?: YesNo;
  lastLang?: string;
  notifyWithdrawBtc?: YesNo;
  notifyWithdrawBank?: YesNo;
  trusted?: YesNo;
  country?: IIsoCountries | null;
  feeSchedule?: IFeeSchedule | null;
  defaultCurrency?: ICurrencies | null;
}

export class SiteUsers implements ISiteUsers {
  constructor(
    public id?: number,
    public pass?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public date?: dayjs.Dayjs,
    public tel?: string,
    public user?: string,
    public countryCode?: number,
    public authyRequested?: YesNo,
    public verifiedAuthy?: YesNo,
    public authyId?: number,
    public usingSms?: YesNo,
    public dontAsk30Days?: YesNo,
    public dontAskDate?: dayjs.Dayjs,
    public confirmWithdrawalEmailBtc?: YesNo,
    public confirmWithdrawal2faBtc?: YesNo,
    public confirmWithdrawal2faBank?: YesNo,
    public confirmWithdrawalEmailBank?: YesNo,
    public notifyDepositBtc?: YesNo,
    public notifyDepositBank?: YesNo,
    public lastUpdate?: dayjs.Dayjs,
    public noLogins?: YesNo,
    public notifyLogin?: YesNo,
    public deactivated?: YesNo,
    public locked?: YesNo,
    public google2faCode?: string,
    public verifiedGoogle?: YesNo,
    public lastLang?: string,
    public notifyWithdrawBtc?: YesNo,
    public notifyWithdrawBank?: YesNo,
    public trusted?: YesNo,
    public country?: IIsoCountries | null,
    public feeSchedule?: IFeeSchedule | null,
    public defaultCurrency?: ICurrencies | null
  ) {}
}

export function getSiteUsersIdentifier(siteUsers: ISiteUsers): number | undefined {
  return siteUsers.id;
}
