package com.example.simplefileexplorer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class AudioPlayer {
    private MediaPlayer mPlayer;
    private Context context;


    public AudioPlayer(Context c){
        this.context = c.getApplicationContext();
    }
    public void play(String absolutePathName){
        stop();
        mPlayer = MediaPlayer.create(context , Uri.parse(absolutePathName));
        mPlayer.start();

    }
    public void stop(){
        if(mPlayer !=null){
            mPlayer.release();
            mPlayer = null;
        }
    }
}
