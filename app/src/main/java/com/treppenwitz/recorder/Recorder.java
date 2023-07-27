package com.treppenwitz.recorder;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class Recorder extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
