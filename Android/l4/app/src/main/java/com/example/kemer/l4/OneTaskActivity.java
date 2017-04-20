package com.example.kemer.l4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kemer on 2017-04-14.
 */

public class OneTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_task_activity);

        /*if(getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if(savedInstanceState == null) {*/
            final OneTaskFragment oneTaskFragment = (OneTaskFragment) getFragmentManager().findFragmentById(R.id.oneTask);
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            oneTaskFragment.initFragment(   bundle.getString("title"),
                                            bundle.getString("text"),
                                            bundle.getString("day"),
                                            bundle.getString("month"),
                                            bundle.getString("year"),
                                            bundle.getString("time"),
                                            (Priority)bundle.getSerializable("priority"));
        }

}
