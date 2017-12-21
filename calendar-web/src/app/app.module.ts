import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatButtonModule, MatDialogModule, MatFormFieldModule, MatInputModule} from '@angular/material';

import { AppComponent } from './app.component';
import { CalendarComponent } from 'ap-angular2-fullcalendar';
import { EventService } from './event.service';
import { EventDialogComponent } from './event-dialog/event-dialog.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    CalendarComponent,
    EventDialogComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatDialogModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  providers: [
    EventService
  ],
  bootstrap: [AppComponent],
  entryComponents: [EventDialogComponent]
})
export class AppModule { }
