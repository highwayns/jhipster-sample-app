export interface IFeeSchedule {
  id?: number;
  fee?: number;
  fromUsd?: number;
  toUsd?: number;
  order?: number;
  fee1?: number;
  globalBtc?: number;
}

export class FeeSchedule implements IFeeSchedule {
  constructor(
    public id?: number,
    public fee?: number,
    public fromUsd?: number,
    public toUsd?: number,
    public order?: number,
    public fee1?: number,
    public globalBtc?: number
  ) {}
}

export function getFeeScheduleIdentifier(feeSchedule: IFeeSchedule): number | undefined {
  return feeSchedule.id;
}
