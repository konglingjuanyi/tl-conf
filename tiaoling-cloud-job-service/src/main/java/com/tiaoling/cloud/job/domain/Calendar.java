package com.tiaoling.cloud.job.domain;

public class Calendar extends CalendarKey {
    private byte[] calendar;

    public byte[] getCalendar() {
        return calendar;
    }

    public void setCalendar(byte[] calendar) {
        this.calendar = calendar;
    }
}