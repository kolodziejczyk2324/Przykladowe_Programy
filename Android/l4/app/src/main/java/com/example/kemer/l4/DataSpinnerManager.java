package com.example.kemer.l4;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Calendar;

/**
 * Created by Kemer on 2017-04-13.
 */

public class DataSpinnerManager {

    private static int startYear;
    private Spinner s_d, s_m, s_y, s_h, s_min;
    private ArrayAdapter<String> daysAdapter;


    public DataSpinnerManager(Activity activity, View rootView) {
        setReferenceToSpinners(rootView);
        setSpinnersAction(activity, rootView);
        setDataSpinners(activity);
    }

    public void setDate(int day, int month, int year, int hours, int min) {
        s_d.setSelection(day-1);
        s_m.setSelection(month-1);
        s_y.setSelection(year-DataSpinnerManager.startYear);
        s_h.setSelection(hours);
        s_min.setSelection(min/5);
    }

    public void setActuallDate() {
        Calendar c = Calendar.getInstance();
        setDate(c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.MONTH)+1,
                c.get(Calendar.YEAR),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE));
    }

    public static String[] getDays(int month, int year) {
        int numberOfDays = 0;
        switch (month) {
            case 1: numberOfDays = 31; break;
            case 2: numberOfDays = (year%4==0 ? 29 : 28); break;
            case 3: numberOfDays = 31; break;
            case 4: numberOfDays = 30; break;
            case 5: numberOfDays = 31; break;
            case 6: numberOfDays = 30; break;
            case 7: numberOfDays = 31; break;
            case 8: numberOfDays = 31; break;
            case 9: numberOfDays = 30; break;
            case 10: numberOfDays = 31; break;
            case 11: numberOfDays = 30; break;
            case 12: numberOfDays = 31; break;
        }
        String days[] = new String[numberOfDays];
        for(int i = 0; i<numberOfDays ; i++)
            days[i] = (i+1)+"";
        return days;
    }

    public static String[] getYears(int ile) {
        startYear = Calendar.getInstance().get(Calendar.YEAR);
        String years[] = new String[ile];
        for(int i=0 ; i<ile ; i++)
            years[i] = (startYear + i) + "";
        return years;
    }

    public static String[] getMinthsNumber() {
        String m[] = new String[12];
        for(int i=0 ; i<12 ; i++) m[i] = (i+1) + "";
        return m;
    }

    public static String[] getMonthsString(Resources res) {
        return res.getStringArray(R.array.month_array);
    }

    public static String[] getHours() {
        String h[] = new String[24];
        for(int i=0 ; i<24 ; i++) h[i] = i+"";
        return h;
    }

    public static String[] getMinute() {
        String m[] = new String[12];
        for(int i=0 ; i<12 ; i++) m[i] = i*5 + "";
        return m;
    }




    private int getIntFromSpinner(Spinner s) {
        return Integer.parseInt(s.getSelectedItem().toString());
    }

    private void setSpinnersAction(final Activity activity, final View rootView) {
        s_m.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDaySpinner(   activity,
                        getIntFromSpinner(s_d),
                        getIntFromSpinner(s_m),
                        getIntFromSpinner(s_y));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        s_y.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDaySpinner(   activity,
                        getIntFromSpinner(s_d),
                        getIntFromSpinner(s_m),
                        getIntFromSpinner(s_y));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateDaySpinner(Activity activity, int day, int month, int year) {
        daysAdapter = new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getDays(month, year));
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_d.setAdapter(daysAdapter);
        s_d.setSelection(Math.min(day-1, daysAdapter.getCount()-1));
    }

    private void setReferenceToSpinners(View rootView) {
        s_d = (Spinner) rootView.findViewById(R.id.daySpinner);
        s_m = (Spinner) rootView.findViewById(R.id.monthSpinner);
        s_y = (Spinner) rootView.findViewById(R.id.yearSpinner);
        s_h = (Spinner) rootView.findViewById(R.id.hourSpinner);
        s_min = (Spinner) rootView.findViewById(R.id.minSpinner);
    }

    private void setDataSpinners(Activity activity) {
        s_y.setAdapter(new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getYears(10)));
        s_m.setAdapter(new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getMinthsNumber()));
        daysAdapter = new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getDays( Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.YEAR)));
        s_d.setAdapter(daysAdapter);
        s_h.setAdapter(new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getHours()));
        s_min.setAdapter(new ArrayAdapter<String>(activity,
                R.layout.support_simple_spinner_dropdown_item,
                DataSpinnerManager.getMinute()));
    }

}
