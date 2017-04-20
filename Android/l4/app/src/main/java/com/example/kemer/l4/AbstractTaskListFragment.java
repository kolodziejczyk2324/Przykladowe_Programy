package com.example.kemer.l4;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kemer on 2017-04-19.
 */

public abstract class AbstractTaskListFragment extends Fragment {

    protected TaskAdapter adapter;
    protected ArrayList<TaskData> taskList;
    protected ListView listView;
    protected boolean mDualPane;

    protected abstract void afterOnCreateView();
    protected abstract void readSavedState(Bundle savedState);
    protected abstract void operationOnStartInDualPane();

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        mDualPane = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedState != null)
            readSavedState(savedState);

        if (mDualPane)
            operationOnStartInDualPane();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_list_layout, container, false);
        listView = (ListView) rootView.findViewById(R.id.taskList);

        taskList = FileOperation.getTasksFromFile();

        adapter = new TaskAdapter(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                taskList);
        listView.setAdapter(adapter);

        afterOnCreateView();

        return rootView;
    }

    protected void showTaskInSameActivity(TaskData taskData) {
        OneTaskFragment frag = (OneTaskFragment) getActivity().getFragmentManager().findFragmentById(R.id.oneTask);
        frag.initFragment(  taskData.getTitle(),
                taskData.getText(),
                taskData.getDate().getDay() + "",
                taskData.getDate().getMonthString(getResources()),
                taskData.getDate().getYear() + "",
                taskData.getDate().getHour() + ":" +
                        taskData.getDate().getMinute(),
                taskData.getPriority());
    }

    protected void showTaskInNewActivity(TaskData taskData) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), OneTaskActivity.class);
        intent.putExtra("title", taskData.getTitle());
        intent.putExtra("text", taskData.getText());
        intent.putExtra("day", taskData.getDate().getDay() + "");
        intent.putExtra("month", taskData.getDate().getMonthString(getResources()));
        intent.putExtra("year", taskData.getDate().getYear() + "");
        intent.putExtra("time", taskData.getDate().getHour() + ":" +
                                taskData.getDate().getMinute());
        intent.putExtra("priority", taskData.getPriority());
        startActivity(intent);
    }

}
