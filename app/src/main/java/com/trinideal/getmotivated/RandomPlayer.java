package com.trinideal.getmotivated;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomPlayer {

    private MediaPlayer mPlayer;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void playRandomFromFolder(Context c, String folder, AssetManager am) {
        stop();
        String file = getRandomSound(c, folder);
        if (file == null)
            return;
        try {
            AssetFileDescriptor descriptor = am.openFd(folder + "/" + file);
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mPlayer != null) {
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
        }
    }

    // Gets a random sound file from an asset folder
    private String getRandomSound(Context context, String path) {

        try {
            String[] files = context.getAssets().list(path);
            if (files.length == 0)
                return null;
            String file =  files[(int) (Math.random() * files.length)];
            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
