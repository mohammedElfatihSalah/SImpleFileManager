package com.example.simplefileexplorer.Models;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.simplefileexplorer.R;

import java.util.Date;

public class Pdf extends MediaFile {
    public Pdf(String name, String absolutePathName, double size, Date lastModifiedDate) {
        super(name, absolutePathName, size, lastModifiedDate);
    }

    @Override
    public void setImage(ImageView image, Fragment fragment) {
        image.setImageResource(R.drawable.pdf);
    }

    @Override
    public String getMimeType() {
        return "application/pdf";
    }
}
