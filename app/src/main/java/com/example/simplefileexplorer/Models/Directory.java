package com.example.simplefileexplorer.Models;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import com.example.simplefileexplorer.R;

import java.util.Date;


public class Directory extends MediaFile {

    public Directory(String name, String absolutePathName, double size, Date lastModifiedDate) {
        super(name, absolutePathName, size, lastModifiedDate);
    }

    @Override
    public void setImage(ImageView image, Fragment fragment) {
        //do it later when using recyclerview
        image.setImageResource(R.drawable.folder);
    }
}
