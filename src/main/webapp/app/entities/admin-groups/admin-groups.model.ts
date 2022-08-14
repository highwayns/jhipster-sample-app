import { IAdminGroupsPages } from 'app/entities/admin-groups-pages/admin-groups-pages.model';
import { IAdminGroupsTabs } from 'app/entities/admin-groups-tabs/admin-groups-tabs.model';

export interface IAdminGroups {
  id?: number;
  name?: string;
  order?: number;
  adminGroupsPages?: IAdminGroupsPages[] | null;
  adminGroupsTabs?: IAdminGroupsTabs[] | null;
}

export class AdminGroups implements IAdminGroups {
  constructor(
    public id?: number,
    public name?: string,
    public order?: number,
    public adminGroupsPages?: IAdminGroupsPages[] | null,
    public adminGroupsTabs?: IAdminGroupsTabs[] | null
  ) {}
}

export function getAdminGroupsIdentifier(adminGroups: IAdminGroups): number | undefined {
  return adminGroups.id;
}
