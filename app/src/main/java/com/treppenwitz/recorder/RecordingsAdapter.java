package com.treppenwitz.recorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.treppenwitz.recorder.data.RecordingFileModel;

import java.io.File;
import java.util.List;

public class RecordingsAdapter extends RecyclerView.Adapter<RecordingsAdapter.ViewHolder> {

    private List<RecordingFileModel> recordingList;
    private boolean isPlaying = false;
    private PlayerService playerService;

    public RecordingsAdapter (List<RecordingFileModel> recordings) {
        recordingList = recordings;
        playerService = new PlayerService();
    }

    @NonNull
    @Override
    public RecordingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View recordingView = layoutInflater.inflate(R.layout.recording_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(recordingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordingsAdapter.ViewHolder holder, int position) {
        final RecordingFileModel recording = recordingList.get(position);
        holder.nameTextView.setText(recording.getDisplayName());
        File recordingFile = recording.getFile();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView playerStatusIcon = view.findViewById(R.id.playerStatusIcon);
                if (!isPlaying) { // play audio
                    playerService.play(view, recordingFile);
                    playerStatusIcon.setImageResource(R.drawable.icon_stop_indicator);
                    isPlaying = true;
                } else {
                    // stop audio
                    if (playerService.currentFile.equals(recordingFile)) {
                        playerService.stop();
                        playerStatusIcon.setImageResource(R.drawable.icon_recording_item_indicator);
                        isPlaying = false;
                    } else { // if another item is played, stop current and play new audio
                        playerService.currentIcon.setImageResource(R.drawable.icon_recording_item_indicator);
                        playerService.stop();
                        playerService.play(view, recordingFile);
                        playerStatusIcon.setImageResource(R.drawable.icon_stop_indicator);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.recordingFileName);
        }
    }
}
