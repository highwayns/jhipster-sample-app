import dayjs from 'dayjs/esm';

export interface IStatus {
  id?: number;
  lastSweep?: dayjs.Dayjs;
  deficitBtc?: number;
  hotWalletBtc?: number;
  warmWalletBtc?: number;
  totalBtc?: number;
  receivedBtcPending?: number;
  pendingWithdrawals?: number;
  tradingStatus?: string;
  withdrawalsStatus?: string;
  dbVersion?: number;
  cronDailyStats?: dayjs.Dayjs;
  cronGetStats?: dayjs.Dayjs;
  cronMaintenance?: dayjs.Dayjs;
  cronMonthlyStats?: dayjs.Dayjs;
  cronReceiveBitcoin?: dayjs.Dayjs;
  cronSendBitcoin?: dayjs.Dayjs;
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public lastSweep?: dayjs.Dayjs,
    public deficitBtc?: number,
    public hotWalletBtc?: number,
    public warmWalletBtc?: number,
    public totalBtc?: number,
    public receivedBtcPending?: number,
    public pendingWithdrawals?: number,
    public tradingStatus?: string,
    public withdrawalsStatus?: string,
    public dbVersion?: number,
    public cronDailyStats?: dayjs.Dayjs,
    public cronGetStats?: dayjs.Dayjs,
    public cronMaintenance?: dayjs.Dayjs,
    public cronMonthlyStats?: dayjs.Dayjs,
    public cronReceiveBitcoin?: dayjs.Dayjs,
    public cronSendBitcoin?: dayjs.Dayjs
  ) {}
}

export function getStatusIdentifier(status: IStatus): number | undefined {
  return status.id;
}
