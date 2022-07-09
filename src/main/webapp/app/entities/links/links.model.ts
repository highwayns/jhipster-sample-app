import { ILink } from 'app/entities/link/link.model';

export interface ILinks {
  id?: number;
  data?: ILink | null;
  action?: ILink | null;
  documentation?: ILink | null;
}

export class Links implements ILinks {
  constructor(public id?: number, public data?: ILink | null, public action?: ILink | null, public documentation?: ILink | null) {}
}

export function getLinksIdentifier(links: ILinks): number | undefined {
  return links.id;
}
