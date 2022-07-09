export interface IParameters {
  id?: number;
  parameter?: string | null;
}

export class Parameters implements IParameters {
  constructor(public id?: number, public parameter?: string | null) {}
}

export function getParametersIdentifier(parameters: IParameters): number | undefined {
  return parameters.id;
}
