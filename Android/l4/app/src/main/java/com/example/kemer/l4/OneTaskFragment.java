package com.example.kemer.l4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Kemer on 2017-04-14.
 */

public class OneTaskFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.one_task_layout, container, false);
        return rootView;
    }

    private void setTextToTextView(int id, String txt) {
        TextView tv = (TextView) rootView.findViewById(id);
        tv.setText(txt);
    }

    public void initFragment(String title, String text, String day, String month, String year, String time, Priority priority) {
        setTextToTextView(R.id.titleTextView, title);
        setTextToTextView(R.id.textTextView, text);
        setTextToTextView(R.id.dayTextView, day);
        setTextToTextView(R.id.monthTextView, month);
        setTextToTextView(R.id.yearTextView, year);
        setTextToTextView(R.id.timeTextView, time);
        BackgroundPrioritySetter.setBackgroundColor(rootView, priority);
    }
}
