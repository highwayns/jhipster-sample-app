export interface IRequestDescriptions {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class RequestDescriptions implements IRequestDescriptions {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getRequestDescriptionsIdentifier(requestDescriptions: IRequestDescriptions): number | undefined {
  return requestDescriptions.id;
}
