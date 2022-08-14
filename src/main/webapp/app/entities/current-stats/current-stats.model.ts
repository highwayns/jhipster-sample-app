export interface ICurrentStats {
  id?: number;
  totalBtc?: number;
  marketCap?: number;
  tradeVolume?: number;
}

export class CurrentStats implements ICurrentStats {
  constructor(public id?: number, public totalBtc?: number, public marketCap?: number, public tradeVolume?: number) {}
}

export function getCurrentStatsIdentifier(currentStats: ICurrentStats): number | undefined {
  return currentStats.id;
}
