import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IAdminTabs {
  id?: number;
  name?: string;
  order?: number;
  icon?: string;
  url?: string;
  hidden?: YesNo;
  isCtrlPanel?: YesNo;
  forGroup?: number;
  oneRecord?: YesNo;
}

export class AdminTabs implements IAdminTabs {
  constructor(
    public id?: number,
    public name?: string,
    public order?: number,
    public icon?: string,
    public url?: string,
    public hidden?: YesNo,
    public isCtrlPanel?: YesNo,
    public forGroup?: number,
    public oneRecord?: YesNo
  ) {}
}

export function getAdminTabsIdentifier(adminTabs: IAdminTabs): number | undefined {
  return adminTabs.id;
}
