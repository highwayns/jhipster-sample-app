import dayjs from 'dayjs/esm';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface IIdentity {
  id?: number;
  debtorId?: string | null;
  emailAddress?: string | null;
  gender?: Gender | null;
  dateOfBirth?: dayjs.Dayjs | null;
  socialSecurityNumber?: string | null;
  chamberOfCommerceNumber?: string | null;
  vatNumber?: string | null;
}

export class Identity implements IIdentity {
  constructor(
    public id?: number,
    public debtorId?: string | null,
    public emailAddress?: string | null,
    public gender?: Gender | null,
    public dateOfBirth?: dayjs.Dayjs | null,
    public socialSecurityNumber?: string | null,
    public chamberOfCommerceNumber?: string | null,
    public vatNumber?: string | null
  ) {}
}

export function getIdentityIdentifier(identity: IIdentity): number | undefined {
  return identity.id;
}
