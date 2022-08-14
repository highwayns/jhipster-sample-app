import dayjs from 'dayjs/esm';

export interface IAdminSessions {
  id?: number;
  sessionId?: string;
  sessionTime?: dayjs.Dayjs;
  sessionStart?: dayjs.Dayjs;
  sessionValue?: string;
  ipAddress?: string;
  userAgent?: string;
}

export class AdminSessions implements IAdminSessions {
  constructor(
    public id?: number,
    public sessionId?: string,
    public sessionTime?: dayjs.Dayjs,
    public sessionStart?: dayjs.Dayjs,
    public sessionValue?: string,
    public ipAddress?: string,
    public userAgent?: string
  ) {}
}

export function getAdminSessionsIdentifier(adminSessions: IAdminSessions): number | undefined {
  return adminSessions.id;
}
