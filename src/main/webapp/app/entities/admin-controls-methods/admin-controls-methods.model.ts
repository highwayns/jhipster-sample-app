import { IAdminCron } from 'app/entities/admin-cron/admin-cron.model';
import { IAdminControls } from 'app/entities/admin-controls/admin-controls.model';

export interface IAdminControlsMethods {
  id?: number;
  method?: string | null;
  argument?: string;
  order?: number;
  pId?: number;
  adminCrons?: IAdminCron[] | null;
  controlId?: IAdminControls | null;
}

export class AdminControlsMethods implements IAdminControlsMethods {
  constructor(
    public id?: number,
    public method?: string | null,
    public argument?: string,
    public order?: number,
    public pId?: number,
    public adminCrons?: IAdminCron[] | null,
    public controlId?: IAdminControls | null
  ) {}
}

export function getAdminControlsMethodsIdentifier(adminControlsMethods: IAdminControlsMethods): number | undefined {
  return adminControlsMethods.id;
}
