import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';
import { IAdminControlsMethods } from 'app/entities/admin-controls-methods/admin-controls-methods.model';

export interface IAdminCron {
  id?: number;
  day?: string;
  month?: string;
  year?: string;
  sendCondition?: string;
  controlId?: IAdminControls | null;
  methodId?: IAdminControlsMethods | null;
}

export class AdminCron implements IAdminCron {
  constructor(
    public id?: number,
    public day?: string,
    public month?: string,
    public year?: string,
    public sendCondition?: string,
    public controlId?: IAdminControls | null,
    public methodId?: IAdminControlsMethods | null
  ) {}
}

export function getAdminCronIdentifier(adminCron: IAdminCron): number | undefined {
  return adminCron.id;
}
