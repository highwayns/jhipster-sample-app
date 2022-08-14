export interface IOrderTypes {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class OrderTypes implements IOrderTypes {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getOrderTypesIdentifier(orderTypes: IOrderTypes): number | undefined {
  return orderTypes.id;
}
