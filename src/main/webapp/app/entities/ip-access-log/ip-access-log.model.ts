import dayjs from 'dayjs/esm';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IIpAccessLog {
  id?: number;
  ip?: number;
  timestamp?: dayjs.Dayjs;
  login?: YesNo;
}

export class IpAccessLog implements IIpAccessLog {
  constructor(public id?: number, public ip?: number, public timestamp?: dayjs.Dayjs, public login?: YesNo) {}
}

export function getIpAccessLogIdentifier(ipAccessLog: IIpAccessLog): number | undefined {
  return ipAccessLog.id;
}
