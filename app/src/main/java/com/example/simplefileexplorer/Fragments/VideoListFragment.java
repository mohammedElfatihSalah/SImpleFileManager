package com.example.simplefileexplorer.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.example.simplefileexplorer.BuildConfig;
import com.example.simplefileexplorer.Fragments.FileListFragment;
import com.example.simplefileexplorer.Models.FilesLab;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.Models.Video;

import java.io.File;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends FileListFragment {


    @Override
    protected MediaFile getFileInstance(String name, String absolutePathName, double size, Date lastDateModified) {
        return new Video(name , absolutePathName , size , lastDateModified);
    }

    @Override
    protected boolean isRequiredFile(String name) {
        return name.endsWith(".mp4");
    }

    @Override
    protected void setFilesInitialized(boolean bool) {
        FilesLab.getInstance(getActivity()).isVideosInitialized = true;
    }

    @Override
    protected boolean isFilesInitialized() {
        return FilesLab.getInstance(getActivity()).isVideosInitialized  ;
    }

    @Override
    protected void initializeFiles() {
        mFiles = FilesLab.getInstance(getActivity()).getVideos();
    }

    @Override
    public void click(int position , View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MediaFile file  = mFiles.get(position);
        File file1 = new File(file.getAbsolutePathName());
        Uri fileUri = FileProvider.getUriForFile(getActivity() , BuildConfig.APPLICATION_ID + ".fileprovider" , file1);
        intent.setDataAndType(fileUri,file.getMimeType());
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(intent , "music");
        startActivity(chooser);
    }

    protected Uri getUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }
    @Override
    public String getTitle() {
        return "All Videos";
    }
}
