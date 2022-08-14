export interface IContentFiles {
  id?: number;
  fId?: number;
  ext?: string;
  dir?: string;
  url?: string;
  oldName?: string;
  fieldName?: string;
}

export class ContentFiles implements IContentFiles {
  constructor(
    public id?: number,
    public fId?: number,
    public ext?: string,
    public dir?: string,
    public url?: string,
    public oldName?: string,
    public fieldName?: string
  ) {}
}

export function getContentFilesIdentifier(contentFiles: IContentFiles): number | undefined {
  return contentFiles.id;
}
