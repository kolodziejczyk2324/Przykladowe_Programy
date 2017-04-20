package com.example.kemer.l4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;

/**
 * Created by Kemer on 2017-04-13.
 */

public class InsertTaskFragment extends Fragment {

    DataSpinnerManager dataSpinnerManager;
    View rootView;

    private String getTextFromEditText(int id) {
        EditText titleEdit = (EditText) rootView.findViewById(id);
        return titleEdit.getText().toString();
    }

    private Priority getPriorityFromSpinner() {
        Spinner s = (Spinner) rootView.findViewById(R.id.spinner);
        return Priority.values()[s.getSelectedItemPosition()];
    }

    private int getIntFromSpinner(int id) {
        Spinner s = (Spinner) rootView.findViewById(id);
        return Integer.parseInt(s.getSelectedItem().toString());
    }

    private void clearEditText(int id) {
        EditText et = (EditText) rootView.findViewById(id);
        et.setText("");
    }

    private void addButtonAction(final View rootView){
        Button button = (Button) rootView.findViewById(R.id.inserButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                TaskListFragment frag = (TaskListFragment) getFragmentManager().findFragmentById(R.id.taskListFragment);
                frag.addTask(   getTextFromEditText(R.id.titleEditText),
                                getTextFromEditText(R.id.textEditText),
                                new MyDate( getIntFromSpinner(R.id.daySpinner),
                                            getIntFromSpinner(R.id.monthSpinner),
                                            getIntFromSpinner(R.id.yearSpinner),
                                            getIntFromSpinner(R.id.hourSpinner),
                                            getIntFromSpinner(R.id.minSpinner)),
                                getPriorityFromSpinner());
                clearEditText(R.id.titleEditText);
                clearEditText(R.id.textEditText);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("spinner_day", getIntFromSpinner(R.id.daySpinner));
        outState.putInt("spinner_month", getIntFromSpinner(R.id.monthSpinner));
        outState.putInt("spinner_year", getIntFromSpinner(R.id.yearSpinner));
        outState.putInt("spinner_hour", getIntFromSpinner(R.id.hourSpinner));
        outState.putInt("spinner_minute", getIntFromSpinner(R.id.minSpinner));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.insert_task_layout, container, false);
        addButtonAction(rootView);
        dataSpinnerManager = new DataSpinnerManager(getActivity(), rootView);
        if(savedInstanceState == null)
            dataSpinnerManager.setActuallDate();
        else
            dataSpinnerManager.setDate( savedInstanceState.getInt("spinner_day"),
                                        savedInstanceState.getInt("spinner_month"),
                                        savedInstanceState.getInt("spinner_year"),
                                        savedInstanceState.getInt("spinner_hour"),
                                        savedInstanceState.getInt("spinner_minute"));
        return rootView;
    }
}
