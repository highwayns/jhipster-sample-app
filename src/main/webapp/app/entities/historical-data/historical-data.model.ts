import dayjs from 'dayjs/esm';

export interface IHistoricalData {
  id?: number;
  date?: dayjs.Dayjs;
  usd?: number;
}

export class HistoricalData implements IHistoricalData {
  constructor(public id?: number, public date?: dayjs.Dayjs, public usd?: number) {}
}

export function getHistoricalDataIdentifier(historicalData: IHistoricalData): number | undefined {
  return historicalData.id;
}
