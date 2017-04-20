package com.example.kemer.l4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kemer on 2017-04-19.
 */

public class CalendarFragment extends Fragment {

    View rootView;
    private CalendarView calendarView;
    int day, month, year;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.calendar_layout, container, false);

        calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);

        if(savedInstanceState != null)
            selectDate( savedInstanceState.getInt("day"),
                        savedInstanceState.getInt("month"),
                        savedInstanceState.getInt("year"));
        else {
            Calendar c = Calendar.getInstance();
            initDate(   c.get(Calendar.DAY_OF_MONTH),
                        c.get(Calendar.MONTH)+1,
                        c.get(Calendar.YEAR));
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                showList(dayOfMonth, month+1, year);
                initDate(dayOfMonth, month+1, year);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("day", day);
        outState.putInt("month", month);
        outState.putInt("year", year);
    }

    public void initDate(int d, int m, int y) {
        day = d;
        month = m;
        year = y;
    }

    private void showList(int dayOfMonth, int month, int year) {
        TaskListCalendarFragment frag = (TaskListCalendarFragment) getFragmentManager().findFragmentById(R.id.taskListCalendarFragment);
        frag.setDayTasks(dayOfMonth, month, year);
    }

    public void selectDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        System.out.println(day + " " + month + " " + year);
        System.out.println(milliTime);
        initDate(day, month, year);
        calendarView.setDate(milliTime, true, true);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}
