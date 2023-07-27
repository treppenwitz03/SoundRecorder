package com.treppenwitz.recorder;

import static android.os.Environment.DIRECTORY_MUSIC;
import static android.os.Environment.DIRECTORY_RECORDINGS;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecorderService {
    public MediaRecorder recorder;
    public File file;
    public RecorderService (Context context) {
        try {
            String recordingsDirectory = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ? DIRECTORY_RECORDINGS : DIRECTORY_MUSIC;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            String recordingName = String.format("Recording-%s.mp3", currentDateandTime);
            file = new File(Environment.getExternalStoragePublicDirectory(recordingsDirectory), recordingName);

            recorder = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ? new MediaRecorder(context) : new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(file);

            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording () {
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;
    }
}
