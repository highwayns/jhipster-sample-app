import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { IAdminUsers } from 'app/entities/admin-users/admin-users.model';

export interface IAdminOrder {
  id?: number;
  orderBy?: string;
  orderAsc?: number;
  controlId?: IAdminControls | null;
  userId?: IAdminUsers | null;
}

export class AdminOrder implements IAdminOrder {
  constructor(
    public id?: number,
    public orderBy?: string,
    public orderAsc?: number,
    public controlId?: IAdminControls | null,
    public userId?: IAdminUsers | null
  ) {}
}

export function getAdminOrderIdentifier(adminOrder: IAdminOrder): number | undefined {
  return adminOrder.id;
}
