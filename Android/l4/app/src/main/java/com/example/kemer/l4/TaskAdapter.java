package com.example.kemer.l4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kemer on 2017-04-13.
 */

public class TaskAdapter extends ArrayAdapter<TaskData> {

    private LayoutInflater inflater;
    private ArrayList<TaskData> objects;
    private Context context;

    public TaskAdapter(Context context, int textViewResourceId, ArrayList<TaskData> objects) {
        super(context, textViewResourceId, objects);
        inflater = LayoutInflater.from(context);
        this.objects = objects;
        this.context = context;
    }

    private void setTextToTextView(View row, int id, String tekst){
        TextView textView = (TextView) row.findViewById(id);
        textView.setText(tekst);
    }

    public View getView(int position, View row, ViewGroup parent){
        if(row == null)
            row = inflater.inflate(R.layout.cell_layout, parent, false);
        setTextToTextView(row, R.id.titleCellTextView, objects.get(position).getTitle());
        setTextToTextView(row, R.id.dayCellTextView, objects.get(position).getDate().getDay() + "");
        setTextToTextView(row, R.id.monthCellTextView, objects.get(position).getDate().getMonthString(context.getResources()));
        setTextToTextView(row, R.id.yearCellTextView, objects.get(position).getDate().getYear() + "");
        BackgroundPrioritySetter.setBackgroundColor(row, objects.get(position).getPriority());
        return row;
    }
}
