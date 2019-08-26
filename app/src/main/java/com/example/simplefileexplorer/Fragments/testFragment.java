package com.example.simplefileexplorer.Fragments;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.simplefileexplorer.Adapters.RecyclerAdapter;
import com.example.simplefileexplorer.Models.Image;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class testFragment extends Fragment implements RecyclerAdapter.RecyclerAdapterListener {
    private ArrayList<MediaFile> mFiles  = new ArrayList<MediaFile>();
    private RecyclerView mRecycler;
    private ProgressBar mLoading;
    public testFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view , container ,false);
        mRecycler    = (RecyclerView) v.findViewById(R.id.rv_items);
        mLoading     = (ProgressBar)  v.findViewById(R.id.pb_loading_indicator);
        new LoadFilesContent().execute();
        return v;
    }

    @Override
    public void click(int position , View view) {

    }


    private class LoadFilesContent extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String [] columnns   = {MediaStore.MediaColumns.DISPLAY_NAME,MediaStore.MediaColumns.DATA ,
                    MediaStore.MediaColumns.SIZE , MediaStore.MediaColumns.DATE_MODIFIED};
            Cursor cursor        = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , columnns
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
                mFiles.add(new Image(name , data , size , date));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mLoading.setVisibility(View.INVISIBLE);
            RecyclerAdapter.RecyclerAdapterListener listener =  testFragment.this;
            RecyclerAdapter mAdapter =  new RecyclerAdapter(mFiles , testFragment.this , testFragment.this);
            mRecycler.setAdapter(mAdapter);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setHasFixedSize(true);
            mRecycler.setLayoutManager(manager);
        }
    }

    @Override
    public boolean onLongClick(int position , View view) {
        return false;
    }
}
