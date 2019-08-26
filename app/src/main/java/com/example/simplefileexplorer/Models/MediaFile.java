package com.example.simplefileexplorer.Models;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import java.util.Date;

abstract public class MediaFile {

    private String name;
    private String absolutePathName;
    private Date lastModifiedDate;
    private double size;



    public MediaFile(String name , String absolutePathName , double size , Date lastModifiedDate){
        this.name             = name;
        this.absolutePathName = absolutePathName;
        this.size             = size;
        this.lastModifiedDate = lastModifiedDate;
    }


    public String getName(){
        return name;
    }
    public String getAbsolutePathName(){
        return absolutePathName;
    }
    public double getSize(){
        return size;
    }
    public Date getLastModifiedDate(){
        return lastModifiedDate;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAbsolutePathName(String absolutePathName) {
        this.absolutePathName = absolutePathName;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String toString(){
        return this.name;
    }



    abstract public void setImage(ImageView image , Fragment fragment);
    public String getMimeType(){
        return "";
    }
}


