import dayjs from 'dayjs/esm';

export interface IMonthlyReports {
  id?: number;
  date?: dayjs.Dayjs;
  transactionsBtc?: number;
  avgTransactionSizeBtc?: number;
  transactionVolumePerUser?: number;
  totalFeesBtc?: number;
  feesPerUserBtc?: number;
  grossProfitBtc?: number;
}

export class MonthlyReports implements IMonthlyReports {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public transactionsBtc?: number,
    public avgTransactionSizeBtc?: number,
    public transactionVolumePerUser?: number,
    public totalFeesBtc?: number,
    public feesPerUserBtc?: number,
    public grossProfitBtc?: number
  ) {}
}

export function getMonthlyReportsIdentifier(monthlyReports: IMonthlyReports): number | undefined {
  return monthlyReports.id;
}
