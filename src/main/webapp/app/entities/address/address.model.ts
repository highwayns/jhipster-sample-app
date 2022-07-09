import { Country } from 'app/entities/enumerations/country.model';
import { PhoneNumberType } from 'app/entities/enumerations/phone-number-type.model';

export interface IAddress {
  id?: number;
  title?: string | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  country?: Country | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  zipCode?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  phoneNumber1?: string | null;
  phoneNumber1Type?: PhoneNumberType | null;
  phoneNumber2?: string | null;
  phoneNumber2Type?: PhoneNumberType | null;
  organisation?: string | null;
  department?: string | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public title?: string | null,
    public firstName?: string | null,
    public middleName?: string | null,
    public lastName?: string | null,
    public country?: Country | null,
    public addressLine1?: string | null,
    public addressLine2?: string | null,
    public zipCode?: string | null,
    public city?: string | null,
    public stateProvince?: string | null,
    public phoneNumber1?: string | null,
    public phoneNumber1Type?: PhoneNumberType | null,
    public phoneNumber2?: string | null,
    public phoneNumber2Type?: PhoneNumberType | null,
    public organisation?: string | null,
    public department?: string | null
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
