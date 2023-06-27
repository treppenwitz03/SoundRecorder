package com.treppenwitz.recorder;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class PlayerService {
    private MediaPlayer player;
    public File currentFile;
    public ImageView currentIcon;
    public void play(View view, File file) {
        currentFile = file;
        currentIcon = (ImageView) view.findViewById(R.id.playerStatusIcon);
        player = new MediaPlayer();
        try {
            player.setDataSource(view.getContext(), Uri.fromFile(currentFile));
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.start();
    }

    public void stop() {
        player.stop();
        player.release();
    }
}
