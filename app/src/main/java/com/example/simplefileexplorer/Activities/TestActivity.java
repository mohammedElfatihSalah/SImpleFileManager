package com.example.simplefileexplorer.Activities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.simplefileexplorer.Adapters.RecyclerAdapter;
import com.example.simplefileexplorer.Fragments.testFragment;
import com.example.simplefileexplorer.Models.Image;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;

import java.util.ArrayList;
import java.util.Date;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fm.beginTransaction().add(R.id.fragment_container , new testFragment()).commit();
        }

    }


}
