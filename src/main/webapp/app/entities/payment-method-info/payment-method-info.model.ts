import { ICurrencys } from 'app/entities/currencys/currencys.model';
import { IIssuer } from 'app/entities/issuer/issuer.model';
import { ICardTokenData } from 'app/entities/card-token-data/card-token-data.model';

export interface IPaymentMethodInfo {
  id?: number;
  paymentMethod?: string | null;
  logo?: string | null;
  supportsTokenisation?: boolean | null;
  surchargeAmount?: number | null;
  surchargeAmountExclVat?: number | null;
  surchargeAmountVat?: number | null;
  surchargeVatPercentage?: number | null;
  description?: string | null;
  currencies?: ICurrencys | null;
  issuerList?: IIssuer | null;
  tokenizedCards?: ICardTokenData | null;
}

export class PaymentMethodInfo implements IPaymentMethodInfo {
  constructor(
    public id?: number,
    public paymentMethod?: string | null,
    public logo?: string | null,
    public supportsTokenisation?: boolean | null,
    public surchargeAmount?: number | null,
    public surchargeAmountExclVat?: number | null,
    public surchargeAmountVat?: number | null,
    public surchargeVatPercentage?: number | null,
    public description?: string | null,
    public currencies?: ICurrencys | null,
    public issuerList?: IIssuer | null,
    public tokenizedCards?: ICardTokenData | null
  ) {
    this.supportsTokenisation = this.supportsTokenisation ?? false;
  }
}

export function getPaymentMethodInfoIdentifier(paymentMethodInfo: IPaymentMethodInfo): number | undefined {
  return paymentMethodInfo.id;
}
