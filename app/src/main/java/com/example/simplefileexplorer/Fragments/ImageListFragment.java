package com.example.simplefileexplorer.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.simplefileexplorer.Activities.ImageGalleryActivity;
import com.example.simplefileexplorer.Models.FilesLab;
import com.example.simplefileexplorer.Models.Image;
import com.example.simplefileexplorer.Models.MediaFile;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends FileListFragment {


    @Override
    protected MediaFile getFileInstance(String name, String absolutePathName, double size, Date lastDateModified) {
        return new Image(name, absolutePathName, size, lastDateModified);
    }

    @Override
    protected boolean isRequiredFile(String name) {
        return name.endsWith(".jpg");
    }

    @Override
    protected void setFilesInitialized(boolean bool) {
        FilesLab.getInstance(getActivity()).isImagesInitialized = bool;
    }

    @Override
    protected boolean isFilesInitialized() {
        return FilesLab.getInstance(getActivity()).isImagesInitialized;
    }

    @Override
    protected void initializeFiles() {
        mFiles = FilesLab.getInstance(getActivity()).getImages();
    }

    @Override
    public void click(int position , View view) {
        Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
        intent.putExtra("position", position);

        startActivity(intent);

    }

    protected Uri getUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    @Override
    public String getTitle() {
        return "All Images";
    }
}


