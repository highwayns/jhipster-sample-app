import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { IIdentity } from 'app/entities/identity/identity.model';

export interface IOrder {
  id?: number;
  orderNumber?: string | null;
  note?: string | null;
  createDateTimeUtc?: dayjs.Dayjs | null;
  customerReference?: number | null;
  billingAddress?: IAddress | null;
  billingIdentity?: IIdentity | null;
  shippingAddress?: IAddress | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderNumber?: string | null,
    public note?: string | null,
    public createDateTimeUtc?: dayjs.Dayjs | null,
    public customerReference?: number | null,
    public billingAddress?: IAddress | null,
    public billingIdentity?: IIdentity | null,
    public shippingAddress?: IAddress | null
  ) {}
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
