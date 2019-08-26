package com.example.simplefileexplorer.Models;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;


import java.util.Date;

public class Video extends MediaFile {
    private boolean startedInitializing = false;
    private Bitmap bitmap;
    private int mPosition;

    public Video(String name , String absolutePathName , double size , Date lastDateModified){

        super(name , absolutePathName , size , lastDateModified);


    }

    @Override
    public void setImage(ImageView image , Fragment fragment) {
       // Bitmap thumb = ThumbnailUtils.createVideoThumbnail(getAbsolutePathName(), MediaStore.Images.Thumbnails.MINI_KIND);
        //image.setImageBitmap(thumb);

        Glide.with(fragment).load(this.getAbsolutePathName()).override(500,500).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.video).into(image);

    }

    @Override
    public String getMimeType() {
        return "video/*";
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public Bitmap getBitmap(){
        return this.bitmap;
    }
}
