import { IAbuseReport } from 'app/entities/abuse-report/abuse-report.model';
import { IParameters } from 'app/entities/parameters/parameters.model';

export interface IAbuseTrigger {
  id?: number;
  score?: number | null;
  code?: string | null;
  description?: string | null;
  abuseReports?: IAbuseReport[] | null;
  parameters?: IParameters | null;
}

export class AbuseTrigger implements IAbuseTrigger {
  constructor(
    public id?: number,
    public score?: number | null,
    public code?: string | null,
    public description?: string | null,
    public abuseReports?: IAbuseReport[] | null,
    public parameters?: IParameters | null
  ) {}
}

export function getAbuseTriggerIdentifier(abuseTrigger: IAbuseTrigger): number | undefined {
  return abuseTrigger.id;
}
