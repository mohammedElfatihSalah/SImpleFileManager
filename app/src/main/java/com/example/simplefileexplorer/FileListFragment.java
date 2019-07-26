package com.example.simplefileexplorer;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
abstract public class FileListFragment extends Fragment {
    public static final java.io.File ROOT_DIR= new File("/sdcard/");
    protected ArrayList<MediaFile> mFiles;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecycler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFiles();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_recycler_view,container,false);
        mLoadingIndicator = (ProgressBar) v.findViewById(R.id.pb_loading_indicator);
        mRecycler = (RecyclerView) v.findViewById(R.id.rv_items);
        if(isFilesInitialized()){

            mRecycler.setAdapter(new RecyclerAdapter(mFiles));
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setHasFixedSize(true);
            mRecycler.setLayoutManager(manager);
        }else{
            new FileLoader().execute();
        }
        return v;
    }


    public class FileLoader extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            loadFiles(ROOT_DIR);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setFilesInitialized(true);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mRecycler.setAdapter(new RecyclerAdapter(mFiles));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setHasFixedSize(true);
            mRecycler.setLayoutManager(manager);
        }
    }

    private void loadFiles(File currentDirectory){
        //Log.v("file : " , currentDirectory.isDirectory()+"" );
        for(File file : currentDirectory.listFiles()){
            if(isRequiredFile(file.getName()) && file.isFile()){
                String name = file.getName();
                String absolutePathName = file.getAbsolutePath();
                double size =  (file.length())/(1024.0*1024.0);
                Date lastModifiedDate = new Date(file.lastModified());
                MediaFile mFile = getFileInstance(name , absolutePathName , size , lastModifiedDate);

                mFiles.add(mFile);
            }
        }
        for(File file:currentDirectory.listFiles()){
            if(file.isDirectory() && !file.getName().startsWith(".")){
                loadFiles(file);
            }
        }
    }

    abstract protected MediaFile getFileInstance(String name , String absolutePathName , double size , Date lastDateModified);
    abstract  protected boolean isRequiredFile(String name);
    abstract protected void setFilesInitialized(boolean bool);
    abstract protected boolean isFilesInitialized();
    abstract protected void initializeFiles();

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
