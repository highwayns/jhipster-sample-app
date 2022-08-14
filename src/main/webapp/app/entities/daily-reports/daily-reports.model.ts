import dayjs from 'dayjs/esm';

export interface IDailyReports {
  id?: number;
  date?: dayjs.Dayjs;
  totalBtc?: number;
  totalFiatUsd?: number;
  openOrdersBtc?: number;
  btcPerUser?: number;
  transactionsBtc?: number;
  avgTransactionSizeBtc?: number;
  transactionVolumePerUser?: number;
  totalFeesBtc?: number;
  feesPerUserBtc?: number;
  usdPerUser?: number;
  grossProfitBtc?: number;
}

export class DailyReports implements IDailyReports {
  constructor(
    public id?: number,
    public date?: dayjs.Dayjs,
    public totalBtc?: number,
    public totalFiatUsd?: number,
    public openOrdersBtc?: number,
    public btcPerUser?: number,
    public transactionsBtc?: number,
    public avgTransactionSizeBtc?: number,
    public transactionVolumePerUser?: number,
    public totalFeesBtc?: number,
    public feesPerUserBtc?: number,
    public usdPerUser?: number,
    public grossProfitBtc?: number
  ) {}
}

export function getDailyReportsIdentifier(dailyReports: IDailyReports): number | undefined {
  return dailyReports.id;
}
