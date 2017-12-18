export class Event {

  title: string;
  start: string;
  end?: string;
  id?: number;
  url?: string;

  constructor(title: string, start: string, end?: string, id?: number, url?: string) {
    this.title = title;
    this.start = start;
    this.end = end;
    this.id = id;
  }

}
