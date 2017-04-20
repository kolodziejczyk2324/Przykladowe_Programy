package com.example.kemer.l4;

import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by Kemer on 2017-04-19.
 */

public class TaskListCalendarFragment extends AbstractTaskListFragment {

    ArrayList<TaskData> curTaskList;

    @Override
    protected void afterOnCreateView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTask(position);
            }
        });
        CalendarFragment fragment = (CalendarFragment) getFragmentManager().findFragmentById(R.id.calendarFragment);
        Calendar c = Calendar.getInstance();
        setDayTasks(fragment.getDay(),
                fragment.getMonth(),
                fragment.getYear());
    }

    @Override
    protected void readSavedState(Bundle savedState) {

    }

    @Override
    protected void operationOnStartInDualPane() {

    }
    private void showTask(int index) {
        showTaskInNewActivity(curTaskList.get(index));
    }

    private boolean isTheSameDay(TaskData td, int d, int m, int y) {
        return  td.getDate().getDay() == d &&
                td.getDate().getMonthInt() == m &&
                td.getDate().getYear() == y;
    }

    public void setDayTasks(int day, int month, int year) {
        curTaskList = new ArrayList<TaskData>();
        for(TaskData td : taskList)
            if(isTheSameDay(td, day, month, year))
                curTaskList.add(td);
        adapter = new TaskAdapter(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                curTaskList);
        listView.setAdapter(adapter);
    }
}
