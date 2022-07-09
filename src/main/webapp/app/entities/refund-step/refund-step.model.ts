import dayjs from 'dayjs/esm';
import { IResultAttributes } from 'app/entities/result-attributes/result-attributes.model';
import { RefundStepAction } from 'app/entities/enumerations/refund-step-action.model';
import { RefundStatus } from 'app/entities/enumerations/refund-status.model';

export interface IRefundStep {
  id?: number;
  reference?: number | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  action?: RefundStepAction | null;
  status?: RefundStatus | null;
  description?: string | null;
  resultAttributes?: IResultAttributes | null;
}

export class RefundStep implements IRefundStep {
  constructor(
    public id?: number,
    public reference?: number | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public action?: RefundStepAction | null,
    public status?: RefundStatus | null,
    public description?: string | null,
    public resultAttributes?: IResultAttributes | null
  ) {}
}

export function getRefundStepIdentifier(refundStep: IRefundStep): number | undefined {
  return refundStep.id;
}
