package com.example.simplefileexplorer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MusicListActivity extends AppCompatActivity {
    public static final String TYPE = "type";
    private Fragment mListFragment = null;
    private int fragmentNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        Intent intent = getIntent();
        fragmentNo = intent.getIntExtra(TYPE,0);
        FragmentManager fm = getSupportFragmentManager();
        mListFragment =fm.findFragmentById(R.id.fragment_container);
        if(mListFragment== null){
            mListFragment  =getListFragment();
            fm.beginTransaction().add(R.id.fragment_container , mListFragment).commit();
        }
    }

    private Fragment getListFragment(){
       Fragment fragment = null;
       switch(fragmentNo){
           case 0:
               return new MusicListFragment();
           case 1:
               return new VideoListFragment();
       }
       return fragment;
    }

}
