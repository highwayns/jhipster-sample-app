export interface ILang {
  id?: number;
  key?: string;
  esp?: string;
  eng?: string;
  order?: string;
  pId?: number;
  zh?: string;
  ru?: string;
}

export class Lang implements ILang {
  constructor(
    public id?: number,
    public key?: string,
    public esp?: string,
    public eng?: string,
    public order?: string,
    public pId?: number,
    public zh?: string,
    public ru?: string
  ) {}
}

export function getLangIdentifier(lang: ILang): number | undefined {
  return lang.id;
}
