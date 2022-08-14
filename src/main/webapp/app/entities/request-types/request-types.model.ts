export interface IRequestTypes {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class RequestTypes implements IRequestTypes {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getRequestTypesIdentifier(requestTypes: IRequestTypes): number | undefined {
  return requestTypes.id;
}
