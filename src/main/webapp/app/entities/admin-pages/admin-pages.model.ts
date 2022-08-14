import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IAdminPages {
  id?: number;
  fId?: number;
  name?: string;
  url?: string;
  icon?: string;
  order?: number;
  pageMapReorders?: boolean;
  oneRecord?: YesNo;
}

export class AdminPages implements IAdminPages {
  constructor(
    public id?: number,
    public fId?: number,
    public name?: string,
    public url?: string,
    public icon?: string,
    public order?: number,
    public pageMapReorders?: boolean,
    public oneRecord?: YesNo
  ) {
    this.pageMapReorders = this.pageMapReorders ?? false;
  }
}

export function getAdminPagesIdentifier(adminPages: IAdminPages): number | undefined {
  return adminPages.id;
}
