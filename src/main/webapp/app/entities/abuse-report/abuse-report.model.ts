import dayjs from 'dayjs/esm';

export interface IAbuseReport {
  id?: number;
  score?: number | null;
  createdDateTimeUtc?: dayjs.Dayjs | null;
}

export class AbuseReport implements IAbuseReport {
  constructor(public id?: number, public score?: number | null, public createdDateTimeUtc?: dayjs.Dayjs | null) {}
}

export function getAbuseReportIdentifier(abuseReport: IAbuseReport): number | undefined {
  return abuseReport.id;
}
