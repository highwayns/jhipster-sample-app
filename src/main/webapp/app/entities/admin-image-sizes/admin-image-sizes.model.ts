export interface IAdminImageSizes {
  id?: number;
  fieldName?: string;
  value?: string;
}

export class AdminImageSizes implements IAdminImageSizes {
  constructor(public id?: number, public fieldName?: string, public value?: string) {}
}

export function getAdminImageSizesIdentifier(adminImageSizes: IAdminImageSizes): number | undefined {
  return adminImageSizes.id;
}
