export interface ITransactionTypes {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class TransactionTypes implements ITransactionTypes {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getTransactionTypesIdentifier(transactionTypes: ITransactionTypes): number | undefined {
  return transactionTypes.id;
}
