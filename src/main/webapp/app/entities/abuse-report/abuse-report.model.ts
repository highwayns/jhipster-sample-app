import dayjs from 'dayjs/esm';
import { IAbuseTrigger } from 'app/entities/abuse-trigger/abuse-trigger.model';

export interface IAbuseReport {
  id?: number;
  score?: number | null;
  createdDateTimeUtc?: dayjs.Dayjs | null;
  triggers?: IAbuseTrigger | null;
}

export class AbuseReport implements IAbuseReport {
  constructor(
    public id?: number,
    public score?: number | null,
    public createdDateTimeUtc?: dayjs.Dayjs | null,
    public triggers?: IAbuseTrigger | null
  ) {}
}

export function getAbuseReportIdentifier(abuseReport: IAbuseReport): number | undefined {
  return abuseReport.id;
}
