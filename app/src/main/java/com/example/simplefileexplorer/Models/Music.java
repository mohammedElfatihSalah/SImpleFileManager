package com.example.simplefileexplorer.Models;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;

import java.util.Date;

public class Music extends MediaFile {
    public Music(String name , String absolutePathName , double size , Date lastModifiedDate){
        super(name,absolutePathName,size,lastModifiedDate);
    }

    public void setImage(ImageView image , Fragment fragment){
        image.setImageResource(R.drawable.music_icon);
    }

    @Override
    public String getMimeType() {
        return "audio/*";
    }
}
