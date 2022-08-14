import { ISiteUsers } from 'app/entities/site-users/site-users.model';
import { YesNo } from 'app/entities/enumerations/yes-no.model';

export interface IApiKeys {
  id?: number;
  key?: string;
  secret?: string;
  view?: YesNo;
  orders?: YesNo;
  withdraw?: YesNo;
  nonce?: number;
  siteUser?: ISiteUsers | null;
}

export class ApiKeys implements IApiKeys {
  constructor(
    public id?: number,
    public key?: string,
    public secret?: string,
    public view?: YesNo,
    public orders?: YesNo,
    public withdraw?: YesNo,
    public nonce?: number,
    public siteUser?: ISiteUsers | null
  ) {}
}

export function getApiKeysIdentifier(apiKeys: IApiKeys): number | undefined {
  return apiKeys.id;
}
