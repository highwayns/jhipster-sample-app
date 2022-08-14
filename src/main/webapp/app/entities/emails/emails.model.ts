export interface IEmails {
  id?: number;
  key?: string;
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

export class Emails implements IEmails {
  constructor(
    public id?: number,
    public key?: string,
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

export function getEmailsIdentifier(emails: IEmails): number | undefined {
  return emails.id;
}
