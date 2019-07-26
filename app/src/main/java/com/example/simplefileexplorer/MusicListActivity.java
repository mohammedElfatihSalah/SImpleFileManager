package com.example.simplefileexplorer;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MusicListActivity extends AppCompatActivity {
    private Fragment mMusicListFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        FragmentManager fm = getSupportFragmentManager();
        mMusicListFragment =fm.findFragmentById(R.id.fragment_container);
        if(mMusicListFragment== null){
            mMusicListFragment  =new VideoListFragment();
            fm.beginTransaction().add(R.id.fragment_container , mMusicListFragment).commit();
        }
    }


}
