import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { IAdminGroups } from 'app/entities/admin-groups/admin-groups.model';

export interface IAdminGroupsPages {
  id?: number;
  permission?: boolean;
  pageId?: IAdminPages | null;
  groupId?: IAdminGroups | null;
}

export class AdminGroupsPages implements IAdminGroupsPages {
  constructor(public id?: number, public permission?: boolean, public pageId?: IAdminPages | null, public groupId?: IAdminGroups | null) {
    this.permission = this.permission ?? false;
  }
}

export function getAdminGroupsPagesIdentifier(adminGroupsPages: IAdminGroupsPages): number | undefined {
  return adminGroupsPages.id;
}
