import dayjs from 'dayjs/esm';
import { ISiteUsers } from 'app/entities/site-users/site-users.model';

export interface IChangeSettings {
  id?: number;
  requestContentType?: string;
  request?: string;
  date?: dayjs.Dayjs;
  siteUser?: ISiteUsers | null;
}

export class ChangeSettings implements IChangeSettings {
  constructor(
    public id?: number,
    public requestContentType?: string,
    public request?: string,
    public date?: dayjs.Dayjs,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getChangeSettingsIdentifier(changeSettings: IChangeSettings): number | undefined {
  return changeSettings.id;
}
