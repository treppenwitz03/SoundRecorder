package com.treppenwitz.recorder;

import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_RECORDINGS;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class RecordingsRetriever extends ArrayList<Recording> {
    public RecordingsRetriever() {
        String recordingsFolder = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ? DIRECTORY_RECORDINGS : DIRECTORY_MUSIC;
        File folder = Environment.getExternalStoragePublicDirectory(recordingsFolder);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(".mp3");
            }
        };

        for (File recordingFile : folder.listFiles(filenameFilter)) {
            Recording recording = new Recording(recordingFile, recordingFile.getName(), "0:00");
            add(recording);
        }
    }
}
