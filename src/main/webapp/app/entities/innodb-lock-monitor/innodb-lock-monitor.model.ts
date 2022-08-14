export interface IInnodbLockMonitor {
  id?: number;
  a?: number | null;
}

export class InnodbLockMonitor implements IInnodbLockMonitor {
  constructor(public id?: number, public a?: number | null) {}
}

export function getInnodbLockMonitorIdentifier(innodbLockMonitor: IInnodbLockMonitor): number | undefined {
  return innodbLockMonitor.id;
}
