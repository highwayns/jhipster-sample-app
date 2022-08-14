import { IAdminPages } from 'app/entities/admin-pages/admin-pages.model';
import { IAdminTabs } from 'app/entities/admin-tabs/admin-tabs.model';
import { IAdminControlsMethods } from 'app/entities/admin-controls-methods/admin-controls-methods.model';
import { IAdminCron } from 'app/entities/admin-cron/admin-cron.model';
import { IAdminOrder } from 'app/entities/admin-order/admin-order.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IAdminControls {
  id?: number;
  action?: string;
  controlClass?: string | null;
  argument?: string;
  order?: number;
  isStatic?: YesNo;
  pageId?: IAdminPages | null;
  tabId?: IAdminTabs | null;
  adminControlsMethods?: IAdminControlsMethods[] | null;
  adminCrons?: IAdminCron[] | null;
  adminOrders?: IAdminOrder[] | null;
}

export class AdminControls implements IAdminControls {
  constructor(
    public id?: number,
    public action?: string,
    public controlClass?: string | null,
    public argument?: string,
    public order?: number,
    public isStatic?: YesNo,
    public pageId?: IAdminPages | null,
    public tabId?: IAdminTabs | null,
    public adminControlsMethods?: IAdminControlsMethods[] | null,
    public adminCrons?: IAdminCron[] | null,
    public adminOrders?: IAdminOrder[] | null
  ) {}
}

export function getAdminControlsIdentifier(adminControls: IAdminControls): number | undefined {
  return adminControls.id;
}
