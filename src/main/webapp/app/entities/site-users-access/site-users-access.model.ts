import { ISiteUsers } from 'app/entities/site-users/site-users.model';

export interface ISiteUsersAccess {
  id?: number;
  start?: number;
  last?: number;
  attempts?: number;
  siteUser?: ISiteUsers | null;
}

export class SiteUsersAccess implements ISiteUsersAccess {
  constructor(
    public id?: number,
    public start?: number,
    public last?: number,
    public attempts?: number,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getSiteUsersAccessIdentifier(siteUsersAccess: ISiteUsersAccess): number | undefined {
  return siteUsersAccess.id;
}
