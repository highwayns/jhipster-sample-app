export interface IResultAttributes {
  id?: number;
  key?: string | null;
  value?: string | null;
}

export class ResultAttributes implements IResultAttributes {
  constructor(public id?: number, public key?: string | null, public value?: string | null) {}
}

export function getResultAttributesIdentifier(resultAttributes: IResultAttributes): number | undefined {
  return resultAttributes.id;
}
