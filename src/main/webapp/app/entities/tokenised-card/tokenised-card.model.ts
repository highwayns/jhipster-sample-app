export interface ITokenisedCard {
  id?: number;
  token?: string | null;
  cardExpiryMonth?: string | null;
  cardExpiryYear?: string | null;
  truncatedCardNumber?: string | null;
}

export class TokenisedCard implements ITokenisedCard {
  constructor(
    public id?: number,
    public token?: string | null,
    public cardExpiryMonth?: string | null,
    public cardExpiryYear?: string | null,
    public truncatedCardNumber?: string | null
  ) {}
}

export function getTokenisedCardIdentifier(tokenisedCard: ITokenisedCard): number | undefined {
  return tokenisedCard.id;
}
