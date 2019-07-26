package com.example.simplefileexplorer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
}
