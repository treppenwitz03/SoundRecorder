package com.treppenwitz.recorder;

import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Locale;

public class Timer {
    public int seconds = 0;
    public boolean running = false;
    public Timer (ExtendedFloatingActionButton button) {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                if (running) {
                    button.setText(time);
                    seconds++;
                }

                handler.postDelayed(this, 1000);

            }
        });
    }

    public void stop () {
        running = false;
        seconds = 0;
    }

    public void start () {
        running = true;
    }
}
