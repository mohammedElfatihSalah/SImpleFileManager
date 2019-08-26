package com.example.simplefileexplorer.Models;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplefileexplorer.R;


import java.util.Date;

public class Image extends MediaFile {
    private Bitmap mImage;
    private int mPosition;

    public Image(String name , String absolutePathName , double size , Date lastDateModified){
        super(name,absolutePathName,size,lastDateModified);

    }
    @Override
    public void setImage(ImageView image, Fragment fragment) {
        //image.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(fragment).load(this.getAbsolutePathName()).apply(RequestOptions
        .circleCropTransform()).format(DecodeFormat.PREFER_ARGB_8888).override(500 , 500).placeholder(R.drawable.music_icon).into(image);
    }
    public void setBitmap(Bitmap bitmap){
        mImage = bitmap;
    }

    public Bitmap getBitmap(){
        return this.mImage;
    }

    @Override
    public String getMimeType() {
        return "image/*";
    }
}
