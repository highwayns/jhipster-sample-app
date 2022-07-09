import { Currency } from 'app/entities/enumerations/currency.model';

export interface IPaymentMethodInfo {
  id?: number;
  paymentMethod?: string | null;
  logo?: string | null;
  supportsTokenisation?: boolean | null;
  currencies?: Currency | null;
  surchargeAmount?: number | null;
  surchargeAmountExclVat?: number | null;
  surchargeAmountVat?: number | null;
  surchargeVatPercentage?: number | null;
  description?: string | null;
}

export class PaymentMethodInfo implements IPaymentMethodInfo {
  constructor(
    public id?: number,
    public paymentMethod?: string | null,
    public logo?: string | null,
    public supportsTokenisation?: boolean | null,
    public currencies?: Currency | null,
    public surchargeAmount?: number | null,
    public surchargeAmountExclVat?: number | null,
    public surchargeAmountVat?: number | null,
    public surchargeVatPercentage?: number | null,
    public description?: string | null
  ) {
    this.supportsTokenisation = this.supportsTokenisation ?? false;
  }
}

export function getPaymentMethodInfoIdentifier(paymentMethodInfo: IPaymentMethodInfo): number | undefined {
  return paymentMethodInfo.id;
}
