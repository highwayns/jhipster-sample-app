import { IPaymentMethodInfo } from 'app/entities/payment-method-info/payment-method-info.model';

export interface ICardTokenData {
  id?: number;
  token?: string | null;
  cardExpiryMonth?: string | null;
  cardExpiryYear?: string | null;
  issuerReturnCode?: string | null;
  truncatedCardNumber?: string | null;
  paymentMethodInfos?: IPaymentMethodInfo[] | null;
}

export class CardTokenData implements ICardTokenData {
  constructor(
    public id?: number,
    public token?: string | null,
    public cardExpiryMonth?: string | null,
    public cardExpiryYear?: string | null,
    public issuerReturnCode?: string | null,
    public truncatedCardNumber?: string | null,
    public paymentMethodInfos?: IPaymentMethodInfo[] | null
  ) {}
}

export function getCardTokenDataIdentifier(cardTokenData: ICardTokenData): number | undefined {
  return cardTokenData.id;
}
