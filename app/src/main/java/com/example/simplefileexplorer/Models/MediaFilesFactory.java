package com.example.simplefileexplorer.Models;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.simplefileexplorer.R;

import java.io.File;
import java.util.Date;

public class MediaFilesFactory {
    private MediaFilesFactory(){

    }

    public static MediaFile getInstance(File file){
        MediaFile requiredFile = null;

        String name = file.getName();
        String absolutePathName = file.getAbsolutePath();
        double size =  (file.length())/(1024.0*1024.0);
        Date lastModifiedDate = new Date(file.lastModified());

        if(file.isDirectory()){
            requiredFile = new Directory(name , absolutePathName , size ,lastModifiedDate);

        }
        else if(name.endsWith(".mp3")){
            requiredFile = new Music(name , absolutePathName , size , lastModifiedDate);
        }
        else if (name.endsWith(".mp4")){
            requiredFile = new Video(name , absolutePathName , size , lastModifiedDate);
        }
        else if (name.endsWith(".jpg")){
            requiredFile = new Image(name , absolutePathName , size , lastModifiedDate);
        }
        else if (name.endsWith(".pdf")){
            requiredFile = new Pdf(name , absolutePathName ,size , lastModifiedDate);
        }
        else {
            requiredFile = new MediaFile(name , absolutePathName,size , lastModifiedDate) {
                @Override
                public void setImage(ImageView image, Fragment fragment) {
                    image.setImageResource(R.drawable.unknown);
                }
            };
        }

        return requiredFile;
    }
}
