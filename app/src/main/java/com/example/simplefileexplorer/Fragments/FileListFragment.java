package com.example.simplefileexplorer.Fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.simplefileexplorer.Adapters.RecyclerAdapter;
import com.example.simplefileexplorer.Models.Image;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
abstract public class FileListFragment extends Fragment implements RecyclerAdapter.RecyclerAdapterListener {
    public static final java.io.File ROOT_DIR= new File("/sdcard/");
    protected ArrayList<MediaFile> mFiles;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecycler;
    private RecyclerAdapter mAdapter;
    private FileListListener listener;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make a reference to the array of files in filesLab
        listener =(FileListListener) getActivity();
        initializeFiles();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_recycler_view,container,false);
        listener.setTitle(getTitle());
        mLoadingIndicator = (ProgressBar) v.findViewById(R.id.pb_loading_indicator);
        mRecycler = (RecyclerView) v.findViewById(R.id.rv_items);
        new FileLoader().execute();

        return v;
    }

    //this aynctask  load all the files ,

    public class FileLoader extends AsyncTask<RecyclerAdapter,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(RecyclerAdapter... adapters) {

            loadFiles(ROOT_DIR);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            //mLoadingIndicator.setVisibility(View.INVISIBLE);

            mAdapter = new RecyclerAdapter(mFiles , FileListFragment.this , FileListFragment.this);
            mRecycler.setAdapter(mAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setHasFixedSize(true);
            mRecycler.setLayoutManager(manager);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onLoadFinished();
                }
            },30);
           // getView().setVisibility(View.VISIBLE);
            //loadThumbnails();
        }
    }

    private void loadFiles(File currentDirectory){

/*
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
        }*/

        String [] columnns   = {MediaStore.MediaColumns.DISPLAY_NAME,MediaStore.MediaColumns.DATA ,
                MediaStore.MediaColumns.SIZE , MediaStore.MediaColumns.DATE_MODIFIED};
        Cursor cursor        = getActivity().getContentResolver().query(getUri(), columnns
                ,null,null, MediaStore.MediaColumns.DATE_MODIFIED + " DESC");
        int nameIndex     = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
        int dataIndex     = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
        int sizeIndex     = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE);
        int dateIndex     = cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED);
        while(cursor.moveToNext()){

            String name  = cursor.getString(nameIndex);
            String data  = cursor.getString(dataIndex);
            String dateString  = cursor.getString(dateIndex);
            String sizeString  = cursor.getString(sizeIndex);

            Date date          = new Date(Long.parseLong(dateString)*1000);
            Double size        = Double.valueOf(sizeString);
            size = size/(1024*1024);
            mFiles.add(getFileInstance(name , data , size , date));
        }

    }


    public interface FileListListener{
        void onLoadFinished();
        void setTitle(String title);

    }

    public String getTitle(){
        return "All Files";
    }

    public RecyclerAdapter getAdapter(){
        return mAdapter;
    }
    abstract protected Uri getUri();
    abstract protected MediaFile getFileInstance(String name , String absolutePathName , double size , Date lastDateModified);
    abstract  protected boolean isRequiredFile(String name);
    abstract protected void setFilesInitialized(boolean bool);
    abstract protected boolean isFilesInitialized();
    abstract protected void initializeFiles();

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

   abstract public void click(int position,View view) ;

    @Override
    public boolean onLongClick(int position , View view) {
        return false;
    }
}
