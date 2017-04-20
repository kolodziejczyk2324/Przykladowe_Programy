package com.example.kemer.l4;

import android.view.View;

/**
 * Created by Kemer on 2017-04-14.
 */

public class BackgroundPrioritySetter {
    public static void setBackgroundColor(View view, Priority priority) {
        switch (priority) {
            case BRAK:
                view.setBackgroundResource(R.drawable.lack_shape);
                break;
            case WAZNE:
                view.setBackgroundResource(R.drawable.important_shape);
                break;
            case BARDZO_WAZNE:
                view.setBackgroundResource(R.drawable.very_important_shape);
                break;
        }
    }
}
