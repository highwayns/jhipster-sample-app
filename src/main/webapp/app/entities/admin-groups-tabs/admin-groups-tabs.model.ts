import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';

export interface IAdminGroupsTabs {
  id?: number;
  permission?: boolean;
  tabId?: IAdminTabs | null;
  groupId?: IAdminGroups | null;
}

export class AdminGroupsTabs implements IAdminGroupsTabs {
  constructor(public id?: number, public permission?: boolean, public tabId?: IAdminTabs | null, public groupId?: IAdminGroups | null) {
    this.permission = this.permission ?? false;
  }
}

export function getAdminGroupsTabsIdentifier(adminGroupsTabs: IAdminGroupsTabs): number | undefined {
  return adminGroupsTabs.id;
}
