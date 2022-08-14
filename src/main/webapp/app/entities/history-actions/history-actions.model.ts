export interface IHistoryActions {
  id?: number;
  nameEn?: string;
  nameEs?: string;
  nameRu?: string;
  nameZh?: string;
}

export class HistoryActions implements IHistoryActions {
  constructor(public id?: number, public nameEn?: string, public nameEs?: string, public nameRu?: string, public nameZh?: string) {}
}

export function getHistoryActionsIdentifier(historyActions: IHistoryActions): number | undefined {
  return historyActions.id;
}
