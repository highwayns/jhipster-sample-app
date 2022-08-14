export interface IIsoCountries {
  id?: number;
  locale?: string;
  code?: string;
  name?: string | null;
  prefix?: string | null;
}

export class IsoCountries implements IIsoCountries {
  constructor(
    public id?: number,
    public locale?: string,
    public code?: string,
    public name?: string | null,
    public prefix?: string | null
  ) {}
}

export function getIsoCountriesIdentifier(isoCountries: IIsoCountries): number | undefined {
  return isoCountries.id;
}
