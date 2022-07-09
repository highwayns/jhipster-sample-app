import { Locale } from 'app/entities/enumerations/locale.model';

export interface IErrorReport {
  id?: number;
  language?: Locale | null;
  isFatalError?: boolean | null;
}

export class ErrorReport implements IErrorReport {
  constructor(public id?: number, public language?: Locale | null, public isFatalError?: boolean | null) {
    this.isFatalError = this.isFatalError ?? false;
  }
}

export function getErrorReportIdentifier(errorReport: IErrorReport): number | undefined {
  return errorReport.id;
}
