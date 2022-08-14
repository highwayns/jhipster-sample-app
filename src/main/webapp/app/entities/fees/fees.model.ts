import dayjs from 'dayjs/esm';

export interface IFees {
  id?: number;
  fee?: number;
  date?: dayjs.Dayjs;
}

export class Fees implements IFees {
  constructor(public id?: number, public fee?: number, public date?: dayjs.Dayjs) {}
}

export function getFeesIdentifier(fees: IFees): number | undefined {
  return fees.id;
}
