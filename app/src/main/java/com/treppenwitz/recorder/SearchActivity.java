package com.treppenwitz.recorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        RecyclerView recordingListView = (RecyclerView) findViewById(R.id.searchResults);
//        ArrayList<Recording> recordings = Recording.createRecordingList(20);
//        RecordingsAdapter recordingsAdapter = new RecordingsAdapter(recordings);
//        recordingListView.setAdapter(recordingsAdapter);
//        recordingListView.setLayoutManager(new LinearLayoutManager(this));
    }
}