package com.treppenwitz.recorder.data;

import java.io.File;

public class RecordingFileModel {
    private final File file;
    private final String displayName;

    public RecordingFileModel (File recordingFile, String display) {
        file = recordingFile;
        displayName = display;
    }

    public File getFile() {
        return file;
    }

    public String getDisplayName() { return displayName; }
}
