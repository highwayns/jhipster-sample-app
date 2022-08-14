export interface IRequestStatus {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class RequestStatus implements IRequestStatus {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getRequestStatusIdentifier(requestStatus: IRequestStatus): number | undefined {
  return requestStatus.id;
}
