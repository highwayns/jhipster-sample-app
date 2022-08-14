import dayjs from 'dayjs/esm';

export interface INews {
  id?: number;
  titleEn?: string;
  titleEs?: string;
  date?: dayjs.Dayjs;
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

export class News implements INews {
  constructor(
    public id?: number,
    public titleEn?: string,
    public titleEs?: string,
    public date?: dayjs.Dayjs,
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

export function getNewsIdentifier(news: INews): number | undefined {
  return news.id;
}
