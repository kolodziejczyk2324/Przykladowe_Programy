package com.example.kemer.l4;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Kemer on 2017-04-19.
 */

public class CalendarButtonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_button_layout, container, false);
        setButtonAction(rootView);
        return rootView;
    }

    private void setButtonAction(View rootView) {
        Button button = (Button) rootView.findViewById(R.id.calendarButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showCalendar();
            }
        });

    }

    private void showCalendar() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CalendarActivity.class);
        startActivity(intent);
    }
}
