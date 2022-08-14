import { ICurrencies } from 'app/entities/currencies/currencies.model';
import { IStatus } from 'app/entities/status/status.model';

export interface IStatusEscrows {
  id?: number;
  balance?: number;
  currency?: ICurrencies | null;
  statusId?: IStatus | null;
}

export class StatusEscrows implements IStatusEscrows {
  constructor(public id?: number, public balance?: number, public currency?: ICurrencies | null, public statusId?: IStatus | null) {}
}

export function getStatusEscrowsIdentifier(statusEscrows: IStatusEscrows): number | undefined {
  return statusEscrows.id;
}
