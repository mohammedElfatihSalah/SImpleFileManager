package com.example.simplefileexplorer.Models;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public class FilesLab {
    private Context mContext;
    private static FilesLab sFilesLab;
    private ArrayList<MediaFile> mMusics;
    private ArrayList<MediaFile> mVideos;
    private ArrayList<MediaFile> mImages;
    public static boolean startedInitializingMusics = false;
    public static boolean isMusicsInitialized       = false;
    public static boolean isVideosInitialized       = false;
    public static boolean isImagesInitialized       = false;

    public static final File ROOT_DIR= new File("/sdcard/");


    private FilesLab(Context c){
        mContext = c.getApplicationContext();
        mMusics  = new ArrayList<MediaFile>();
        mVideos =  new ArrayList<MediaFile>();
        mImages =  new ArrayList<MediaFile>();
    }


    public static FilesLab getInstance(Context c){
        if(sFilesLab == null){
            sFilesLab = new FilesLab(c);
        }
        return sFilesLab;
    }
/*
    public boolean initializeMusics(File currentDirectory){

        //Log.v("file : " , currentDirectory.isDirectory()+"" );
        for(File file : currentDirectory.listFiles()){
            if(isFileMusic(file.getName()) && file.isFile()){
                String name = file.getName();
                String absolutePathName = file.getAbsolutePath();
                double size =  (file.length())/(1024.0*1024.0);
                Date lastModifiedDate = new Date(file.lastModified());
                Music music = new Music(name , absolutePathName , size ,lastModifiedDate);
                mMusics.add(music);
            }
        }
        for(File file:currentDirectory.listFiles()){
            if(file.isDirectory() && !file.getName().startsWith(".")){
                initializeMusics(file);
            }
        }
        return true;
    }*/

    public ArrayList<MediaFile> getMusics(){
        return mMusics;
    }

    public ArrayList<MediaFile> getVideos(){
        return mVideos;
    }public ArrayList<MediaFile> getImages(){
        return mImages;
    }

    private boolean isFileMusic(String fileName){
        return fileName.endsWith(".mp3");
    }

}
