package com.example.kemer.l4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kemer on 2017-04-13.
 */

public class TaskListFragment extends AbstractTaskListFragment {

    private int mCurCheckPosition = 0;

    @Override
    protected void afterOnCreateView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurCheckPosition = position;
                showTask(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeTask(position);
                return true;
            }
        });
    }

    @Override
    protected void readSavedState(Bundle savedState) {
        mCurCheckPosition = savedState.getInt("curChoice", 0);
    }

    @Override
    protected void operationOnStartInDualPane() {
        showTask(mCurCheckPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    private void showTask(int index) {
        if(taskList.size() == 0) return;
        if(mDualPane)
            showTaskInSameActivity(taskList.get(index));
        else
            showTaskInNewActivity(taskList.get(index));
    }

    public void addTask(String title, String text, MyDate date, Priority priority) {
        TaskData newTask = new TaskData(title, text, date, priority);
        taskList.add(newTask); //dodajemy do listy
        FileOperation.writeJsonStream(taskList);
        adapter.notifyDataSetChanged(); //aktualizujemy listView
        listView.setSelection(adapter.getCount() - 1); //przesowamy liste na sam koniec
    }

    public void removeTask(int position){
        taskList.remove(position);
        FileOperation.writeJsonStream(taskList);
        adapter.notifyDataSetChanged();
    }

}
