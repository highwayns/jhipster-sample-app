export interface ISettingsFiles {
  id?: number;
  fId?: number;
  ext?: string;
  dir?: string;
  url?: string;
  oldName?: string;
  fieldName?: string;
}

export class SettingsFiles implements ISettingsFiles {
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

export function getSettingsFilesIdentifier(settingsFiles: ISettingsFiles): number | undefined {
  return settingsFiles.id;
}
