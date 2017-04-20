package com.example.kemer.l4;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kemer on 2017-04-13.
 */

public class TaskData {

    private String title;
    private String text;
    private MyDate date;
    private Priority priority;

    public TaskData(String title, String text, MyDate date, Priority priority) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public MyDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return  priority;
    }
}
