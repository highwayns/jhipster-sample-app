import { IIsoCountries } from 'app/entities/iso-countries/iso-countries.model';
import { IAdminOrder } from 'app/entities/admin-order/admin-order.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IAdminUsers {
  id?: number;
  user?: string;
  pass?: string;
  firstName?: string;
  lastName?: string;
  company?: string;
  address?: string;
  city?: string;
  phone?: string;
  email?: string;
  website?: string;
  fId?: number;
  order?: number;
  isAdmin?: YesNo;
  countryCode?: number;
  verifiedAuthy?: YesNo;
  authyId?: string;
  countryId?: IIsoCountries | null;
  adminOrders?: IAdminOrder[] | null;
}

export class AdminUsers implements IAdminUsers {
  constructor(
    public id?: number,
    public user?: string,
    public pass?: string,
    public firstName?: string,
    public lastName?: string,
    public company?: string,
    public address?: string,
    public city?: string,
    public phone?: string,
    public email?: string,
    public website?: string,
    public fId?: number,
    public order?: number,
    public isAdmin?: YesNo,
    public countryCode?: number,
    public verifiedAuthy?: YesNo,
    public authyId?: string,
    public countryId?: IIsoCountries | null,
    public adminOrders?: IAdminOrder[] | null
  ) {}
}

export function getAdminUsersIdentifier(adminUsers: IAdminUsers): number | undefined {
  return adminUsers.id;
}
