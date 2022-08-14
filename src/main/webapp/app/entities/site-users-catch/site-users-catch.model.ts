import { ISiteUsers } from 'app/entities/site-users/site-users.model';

export interface ISiteUsersCatch {
  id?: number;
  attempts?: number;
  siteUser?: ISiteUsers | null;
}

export class SiteUsersCatch implements ISiteUsersCatch {
  constructor(public id?: number, public attempts?: number, public siteUser?: ISiteUsers | null) {}
}

export function getSiteUsersCatchIdentifier(siteUsersCatch: ISiteUsersCatch): number | undefined {
  return siteUsersCatch.id;
}
