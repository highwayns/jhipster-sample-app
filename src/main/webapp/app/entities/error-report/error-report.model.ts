import { IEntry } from 'app/entities/entry/entry.model';
import { Locale } from 'app/entities/enumerations/locale.model';

export interface IErrorReport {
  id?: number;
  language?: Locale | null;
  isFatalError?: boolean | null;
  errors?: IEntry | null;
  warnings?: IEntry | null;
}

export class ErrorReport implements IErrorReport {
  constructor(
    public id?: number,
    public language?: Locale | null,
    public isFatalError?: boolean | null,
    public errors?: IEntry | null,
    public warnings?: IEntry | null
  ) {
    this.isFatalError = this.isFatalError ?? false;
  }
}

export function getErrorReportIdentifier(errorReport: IErrorReport): number | undefined {
  return errorReport.id;
}
