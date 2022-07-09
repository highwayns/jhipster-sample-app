import { IParameters } from 'app/entities/parameters/parameters.model';

export interface IEntry {
  id?: number;
  code?: string | null;
  message?: string | null;
  translatedMessage?: string | null;
  parameters?: IParameters | null;
}

export class Entry implements IEntry {
  constructor(
    public id?: number,
    public code?: string | null,
    public message?: string | null,
    public translatedMessage?: string | null,
    public parameters?: IParameters | null
  ) {}
}

export function getEntryIdentifier(entry: IEntry): number | undefined {
  return entry.id;
}
