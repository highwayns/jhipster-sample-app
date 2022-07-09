import { IOrder } from 'app/entities/order/order.model';
import { OrderLineType } from 'app/entities/enumerations/order-line-type.model';

export interface IOrderLine {
  id?: number;
  lineNumber?: number | null;
  type?: OrderLineType | null;
  skuCode?: string | null;
  name?: string | null;
  description?: string | null;
  quantity?: number | null;
  unitPriceExclVat?: number | null;
  unitPriceInclVat?: number | null;
  vatPercentage?: number | null;
  vatPercentageLabel?: string | null;
  discountPercentageLabel?: string | null;
  totalLineAmount?: number | null;
  url?: string | null;
  orders?: IOrder[] | null;
}

export class OrderLine implements IOrderLine {
  constructor(
    public id?: number,
    public lineNumber?: number | null,
    public type?: OrderLineType | null,
    public skuCode?: string | null,
    public name?: string | null,
    public description?: string | null,
    public quantity?: number | null,
    public unitPriceExclVat?: number | null,
    public unitPriceInclVat?: number | null,
    public vatPercentage?: number | null,
    public vatPercentageLabel?: string | null,
    public discountPercentageLabel?: string | null,
    public totalLineAmount?: number | null,
    public url?: string | null,
    public orders?: IOrder[] | null
  ) {}
}

export function getOrderLineIdentifier(orderLine: IOrderLine): number | undefined {
  return orderLine.id;
}
