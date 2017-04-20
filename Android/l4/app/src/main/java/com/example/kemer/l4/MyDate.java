package com.example.kemer.l4;

import android.content.res.Resources;

/**
 * Created by Kemer on 2017-04-13.
 */

public class MyDate {

    private int year, day, month, hour, minute;

    public MyDate(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public int getMonthInt() {
        return month;
    }

    public String getMonthString(Resources res) {
        return res.getStringArray(R.array.month_array)[month-1];
    }

    public int getDay() {
        return day;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }
}
