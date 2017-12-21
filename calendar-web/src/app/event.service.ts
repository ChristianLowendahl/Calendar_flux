import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { from } from 'rxjs/observable/from';
import { Subject } from 'rxjs/Subject';

import { Event } from './event';
import {forEach} from '@angular/router/src/utils/collection';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class EventService {

  events: Event[] = [
    {
      title: 'All Day Event',
      start: '2017-09-01',
    },
    {
      title: 'Long Event',
      start: '2017-09-07',
      end: '2017-09-10'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2017-09-09T16:00:00'
    },
    {
      id: 999,
      title: 'Repeating Event',
      start: '2017-09-16T16:00:00'
    },
    {
      title: 'Conference',
      start: '2017-09-11',
      end: '2017-09-13'
    },
    {
      title: 'Meeting',
      start: '2017-09-12T10:30:00',
      end: '2017-09-12T12:30:00'
    },
    {
      title: 'Lunch',
      start: '2017-09-12T12:00:00'
    },
    {
      title: 'Meeting',
      start: '2017-09-12T14:30:00'
    },
    {
      title: 'Happy Hour',
      start: '2017-09-12T17:30:00'
    },
    {
      title: 'Dinner',
      start: '2017-09-12T20:00:00'
    },
    {
      title: 'Birthday Party',
      start: '2017-09-13T07:00:00'
    },
    {
      title: 'Click for Google',
      url: 'http://google.com/',
      start: '2017-09-28'
    }];
  eventSubject = new ReplaySubject<Event>();

  constructor(private httpClient: HttpClient) { }

  getEvents(): Event[] {
    return this.events;
  }

  asyncGetEvents(): Observable<Event> {
    // const events: Event[] = this.getEvents();
    // this.eventObservable = of(events);
    const events: Event[] = this.getEvents();
    for (const event of events) {
      this.eventSubject.next(event);
    }
    return this.eventSubject;
  }

  serverGetEvents(): Observable<Event[]> {
    return this.httpClient.get<Event[]>('http://localhost:8080/events/');
  }

  loadEvents() {
    const events: Event[] = this.getEvents();
    for (const event of events) {
      this.eventSubject.next(event);
    }
  }

  addEvent(event: Event) {
    this.eventSubject.next(event);
  }

  serverAddEvent(event: Event): Promise<Event> {
    console.log('serverAddEvent');
    console.log(event);
    return this.httpClient.post<Event>('http://localhost:8080/events/', event).toPromise();
  }

}
