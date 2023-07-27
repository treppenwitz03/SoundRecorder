package com.treppenwitz.recorder;

import java.io.File;

public class Recording {
    private final File file;
    private final String displayName;

    public Recording (File recordingFile, String display) {
        file = recordingFile;
        displayName = display;
    }

    public File getFile() {
        return file;
    }

    public String getDisplayName() { return displayName; }
}
