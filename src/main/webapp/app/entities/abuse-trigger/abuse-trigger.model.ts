export interface IAbuseTrigger {
  id?: number;
  score?: number | null;
  code?: string | null;
  description?: string | null;
}

export class AbuseTrigger implements IAbuseTrigger {
  constructor(public id?: number, public score?: number | null, public code?: string | null, public description?: string | null) {}
}

export function getAbuseTriggerIdentifier(abuseTrigger: IAbuseTrigger): number | undefined {
  return abuseTrigger.id;
}
