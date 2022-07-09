export interface ILink {
  id?: number;
  url?: string | null;
  type?: string | null;
}

export class Link implements ILink {
  constructor(public id?: number, public url?: string | null, public type?: string | null) {}
}

export function getLinkIdentifier(link: ILink): number | undefined {
  return link.id;
}
