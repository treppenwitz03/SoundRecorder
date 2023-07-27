package com.treppenwitz.recorder.utils;

import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_RECORDINGS;

import android.os.Build;
import android.os.Environment;

import com.treppenwitz.recorder.RecordingsAdapter;
import com.treppenwitz.recorder.data.RecordingFileModel;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Objects;

public class RecordingsRetriever extends ArrayList<RecordingFileModel> {
    public RecordingsAdapter adapter;
    public RecordingsRetriever() {
        reload();
    }

    public void reload() {
        clear();
        String recordingsFolder = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ? DIRECTORY_RECORDINGS : DIRECTORY_MUSIC;
        File folder = Environment.getExternalStoragePublicDirectory(recordingsFolder);
        FilenameFilter filenameFilter = (dir, name) -> name.contains(".mp3");

        for (File recordingFile : Objects.requireNonNull(folder.listFiles(filenameFilter))) {
            RecordingFileModel recording = new RecordingFileModel(recordingFile, recordingFile.getName());
            add(recording);
        }
    }

    public static RecordingsRetriever getDefault() {
        return new RecordingsRetriever();
    }

    public void setAdapter(RecordingsAdapter recordingsAdapter) {
        adapter = recordingsAdapter;
    }
}
