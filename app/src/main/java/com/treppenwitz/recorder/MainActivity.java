package com.treppenwitz.recorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public View content_view;
    public Timer timer;
    public boolean isRecording = false;

    public ArrayList<Recording> recordings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content_view = (View) findViewById(R.id.activity_main);

        if (!PermissionUtility.isApplicationPermitted(this)) {
            PermissionUtility.requestPermissions(this);
        }

        populateRecordingList();

        Button searchButton = findViewById(R.id.searchView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        handleRecordClicked();
    }

    private void populateRecordingList() {
        RecyclerView recordingListView = findViewById(R.id.recordingListView);
        recordings = new RecordingsRetriever();
        RecordingsAdapter recordingsAdapter = new RecordingsAdapter(recordings);
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
                    recordings.add(new Recording(recorderService.file, recorderService.file.getName(), "0:00"));
                    timer.stop();
                    button.setText(R.string.record_indicator);
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
            } else {
                Snackbar.make(this, content_view, "Please accept the permissions to continue using this app. ", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}