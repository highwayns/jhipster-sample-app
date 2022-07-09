import dayjs from 'dayjs/esm';
import { CaptureStatus } from 'app/entities/enumerations/capture-status.model';
import { Currency } from 'app/entities/enumerations/currency.model';

export interface ICapture {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  status?: CaptureStatus | null;
  amountToCapture?: number | null;
  convertedAmountToCapture?: number | null;
  convertedCurrency?: Currency | null;
  conversionRate?: number | null;
  isFinalCapture?: boolean | null;
}

export class Capture implements ICapture {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public status?: CaptureStatus | null,
    public amountToCapture?: number | null,
    public convertedAmountToCapture?: number | null,
    public convertedCurrency?: Currency | null,
    public conversionRate?: number | null,
    public isFinalCapture?: boolean | null
  ) {
    this.isFinalCapture = this.isFinalCapture ?? false;
  }
}

export function getCaptureIdentifier(capture: ICapture): number | undefined {
  return capture.id;
}
