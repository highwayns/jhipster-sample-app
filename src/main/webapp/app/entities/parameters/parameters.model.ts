import { IAbuseTrigger } from 'app/entities/abuse-trigger/abuse-trigger.model';
import { IEntry } from 'app/entities/entry/entry.model';

export interface IParameters {
  id?: number;
  parameter?: string | null;
  abuseTriggers?: IAbuseTrigger[] | null;
  entries?: IEntry[] | null;
}

export class Parameters implements IParameters {
  constructor(
    public id?: number,
    public parameter?: string | null,
    public abuseTriggers?: IAbuseTrigger[] | null,
    public entries?: IEntry[] | null
  ) {}
}

export function getParametersIdentifier(parameters: IParameters): number | undefined {
  return parameters.id;
}
