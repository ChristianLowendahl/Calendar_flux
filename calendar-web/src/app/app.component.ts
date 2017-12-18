import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { CalendarComponent } from 'ap-angular2-fullcalendar';
import { Event } from './event';
import { EventService } from './event.service';
import { EventDialogComponent } from './event-dialog/event-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  @ViewChild('myCalendar') myCalendar: CalendarComponent;
  calendarOptions: Object = {
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'month,agendaWeek,agendaDay'
    },
    slotLabelFormat: 'HH:mm',
    timeFormat: 'HH:mm',
    height: 'parent',
    locale: 'sv',
    fixedWeekCount : false,
    defaultDate: '2017-09-12',
    editable: true,
    buttonText: {
      today:    'idag',
      month:    'månad',
      week:     'vecka',
      day:      'dag',
      list:     'lista'
    },
    selectable: true,
    select: (start, end) => this.onEventSelect(start, end),
    eventLimit: true, // allow "more" link when too many events
    // events: this.getEvents()
  };

  private event: Event;

  constructor(private eventService: EventService, private matDialog: MatDialog) {}

  ngOnInit(): void {

  }

  onInitialized() {
    this.eventService.asyncGetEvents().subscribe(event => {
      this.renderEvent(event);
    });
  }

  renderEvents(events: Event[]) {
    this.myCalendar.fullCalendar('renderEvents', events);
  }

  getEvents() {
    return this.eventService.getEvents();
  }

  onEventSelect(start, end) {
    this.event = new Event('Ny händelse', start, end);
    const dialogRef = this.matDialog.open(EventDialogComponent, {
      width: '250px',
      data: this.event
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(this.event);
      if (result) {
        this.eventService.addEvent(this.event);
      }
    });
  }

  renderEvent(event: Event) {
    this.myCalendar.fullCalendar('renderEvent', event);
  }
}
