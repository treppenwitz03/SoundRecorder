package com.treppenwitz.recorder;

import java.io.File;
import java.util.ArrayList;

public class Recording {
    private File file;
    private String displayName;
    private String duration;

    public Recording (File recordingFile, String display, String duration_time) {
        file = recordingFile;
        displayName = display;
        duration = duration_time;
    }

    public File getFile() {
        return file;
    }

    public String getDuration() {
        return duration;
    }

    public String getDisplayName() { return displayName; }
}
