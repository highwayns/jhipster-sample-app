export interface IContent {
  id?: number;
  url?: string;
  titleEn?: string;
  titleEs?: string;
  contentEnContentType?: string;
  contentEn?: string;
  contentEsContentType?: string;
  contentEs?: string;
  titleRu?: string;
  titleZh?: string;
  contentRuContentType?: string;
  contentRu?: string;
  contentZhContentType?: string;
  contentZh?: string;
}

export class Content implements IContent {
  constructor(
    public id?: number,
    public url?: string,
    public titleEn?: string,
    public titleEs?: string,
    public contentEnContentType?: string,
    public contentEn?: string,
    public contentEsContentType?: string,
    public contentEs?: string,
    public titleRu?: string,
    public titleZh?: string,
    public contentRuContentType?: string,
    public contentRu?: string,
    public contentZhContentType?: string,
    public contentZh?: string
  ) {}
}

export function getContentIdentifier(content: IContent): number | undefined {
  return content.id;
}
