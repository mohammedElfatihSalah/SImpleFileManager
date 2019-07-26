package com.example.simplefileexplorer;

import android.media.MediaPlayer;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.util.Date;

public class Music extends MediaFile {
    public Music(String name , String absolutePathName , double size , Date lastModifiedDate){
        super(name,absolutePathName,size,lastModifiedDate);
    }

    public void setImage(ImageView image,RecyclerAdapter adapter , int position){
        image.setImageResource(R.drawable.music_icon);
    }

}
