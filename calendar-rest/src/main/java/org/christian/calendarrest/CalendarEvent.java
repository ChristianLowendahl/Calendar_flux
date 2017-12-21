package org.christian.calendarrest;

import javax.validation.constraints.NotNull;

public class CalendarEvent {

    private String title;
    private String start;
    private String end;
    private String id;
    private String url;

    public CalendarEvent() { }

    public CalendarEvent(String title, String start, String end, String id, String url) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.id = id;
        this.url = url;
    }

    public static CalendarEventBuilder builder() {
        return new CalendarEventBuilder();
    }

    public static class CalendarEventBuilder {

        private String title;
        private String start;
        private String end;
        private String id;
        private String url;

        public CalendarEventBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public CalendarEventBuilder withStart(String start) {
            this.start = start;
            return this;
        }

        public CalendarEventBuilder withEnd(String end) {
            this.end = end;
            return this;
        }

        public CalendarEventBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public CalendarEventBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public CalendarEvent build() {
            return new CalendarEvent(title, start, end, id, url);
        }

    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
