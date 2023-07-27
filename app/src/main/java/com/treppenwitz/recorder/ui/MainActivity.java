package com.treppenwitz.recorder.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.snackbar.Snackbar;

import com.treppenwitz.recorder.R;
import com.treppenwitz.recorder.RecorderService;
import com.treppenwitz.recorder.data.RecordingFileModel;
import com.treppenwitz.recorder.RecordingsAdapter;
import com.treppenwitz.recorder.utils.RecordingsRetriever;
import com.treppenwitz.recorder.Timer;
import com.treppenwitz.recorder.utils.PermissionUtility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public View content_view;
    public Timer timer;
    public boolean isRecording = false;

    public RecordingsRetriever recordings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        ExtendedFloatingActionButton recordButton = findViewById(R.id.recordButton);
        SearchBar searchButton = findViewById(R.id.searchView);

        ViewCompat.setOnApplyWindowInsetsListener(recordButton, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.bottomMargin = insets.bottom;
            v.setLayoutParams(params);

            return WindowInsetsCompat.CONSUMED;
        });

        ViewCompat.setOnApplyWindowInsetsListener(searchButton, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            params.topMargin = insets.top;
            v.setLayoutParams(params);

            return WindowInsetsCompat.CONSUMED;
        });

        content_view = (View) findViewById(R.id.activity_main);

        if (!PermissionUtility.isApplicationPermitted(this)) {
            PermissionUtility.requestPermissions(this);
        }

        populateRecordingList();

        handleRecordClicked();
    }

    private void populateRecordingList() {
        RecyclerView recordingListView = findViewById(R.id.recordingListView);
        recordings = RecordingsRetriever.getDefault();
        RecordingsAdapter recordingsAdapter = new RecordingsAdapter(recordings);
        recordings.setAdapter(recordingsAdapter);
        recordingListView.setAdapter(recordingsAdapter);
        recordingListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void handleRecordClicked () {
        ExtendedFloatingActionButton button = findViewById(R.id.recordButton);
        timer = new Timer(button);
        button.setText(R.string.record_indicator);
        button.setOnClickListener(new View.OnClickListener() {
            RecorderService recorderService;
            @Override
            public void onClick(View view) {
                isRecording = !isRecording;

                if (isRecording) {
                    recorderService = new RecorderService(view.getContext());
                    timer.start();
                } else {
                    recorderService.stopRecording();
                    recordings.add(new RecordingFileModel(recorderService.file, recorderService.file.getName()));
                    timer.stop();
                    button.setText(R.string.record_indicator);
                    recordings.reload();
                    recordings.adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 30) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Snackbar.make(this, content_view, "Permission Denied", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                Snackbar.make(this, content_view, "Permission Granted", Snackbar.LENGTH_SHORT).show();
                recordings.reload();
                recordings.adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(this, content_view, "Please accept the permissions to continue using this app. ", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}