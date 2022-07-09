import dayjs from 'dayjs/esm';
import { RecurrenceType } from 'app/entities/enumerations/recurrence-type.model';

export interface IRecurrenceCriteria {
  id?: number;
  recurrenceType?: RecurrenceType | null;
  recurringExpiry?: dayjs.Dayjs | null;
  recurringFrequency?: number | null;
  instalments?: number | null;
}

export class RecurrenceCriteria implements IRecurrenceCriteria {
  constructor(
    public id?: number,
    public recurrenceType?: RecurrenceType | null,
    public recurringExpiry?: dayjs.Dayjs | null,
    public recurringFrequency?: number | null,
    public instalments?: number | null
  ) {}
}

export function getRecurrenceCriteriaIdentifier(recurrenceCriteria: IRecurrenceCriteria): number | undefined {
  return recurrenceCriteria.id;
}
