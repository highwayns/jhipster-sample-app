export interface IIssuer {
  id?: string;
  name?: string | null;
}

export class Issuer implements IIssuer {
  constructor(public id?: string, public name?: string | null) {}
}

export function getIssuerIdentifier(issuer: IIssuer): string | undefined {
  return issuer.id;
}
