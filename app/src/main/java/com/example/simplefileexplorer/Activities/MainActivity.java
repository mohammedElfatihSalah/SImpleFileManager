package com.example.simplefileexplorer.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.simplefileexplorer.BuildConfig;
import com.example.simplefileexplorer.Fragments.ExplorerFragment;
import com.example.simplefileexplorer.Fragments.FileListFragment;
import com.example.simplefileexplorer.Fragments.ImageListFragment;
import com.example.simplefileexplorer.Fragments.MusicListFragment;
import com.example.simplefileexplorer.Fragments.VideoListFragment;
import com.example.simplefileexplorer.Models.Directory;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.Models.Music;
import com.example.simplefileexplorer.R;

import java.io.File;
import java.security.Provider;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExplorerFragment.ExplorerFragmentListener
, NavigationView.OnNavigationItemSelectedListener , FileListFragment.FileListListener {

    private DrawerLayout drawerLayout;
    private Button mMusicButton;
    private Button mVideoButton;
    private Button mImagesButton;
    private FrameLayout mFrameLayout;
    private ProgressBar progressBar;
    private int numClicks = 0;
    private int selectedId;


    private String SELECTED_ID_KEY = "selected_id";
// ======================== Life Cycle's Method for the Activity =============//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedId = R.id.sd_card;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout     = findViewById(R.id.drawer_layout);
        final NavigationView navigationView   = findViewById(R.id.nav_view);
        mFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        progressBar = (ProgressBar) findViewById(R.id.pb_myProgressBar);
        ActionBarDrawerToggle  toggle   = new ActionBarDrawerToggle(this ,drawerLayout , toolbar , R.string.open_drawer ,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setCheckedItem(selectedId);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getSupportFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof ExplorerFragment ) {
                            selectedId = R.id.sd_card;
                        }
                        if (fragment instanceof MusicListFragment) {
                            selectedId = R.id.all_musics;
                        }
                        if (fragment instanceof VideoListFragment) {
                            selectedId = R.id.all_videos;
                        }
                        if (fragment instanceof ImageListFragment) {
                            selectedId = R.id.all_images;
                        }
                       // setActionBarTitle(currentPosition);
                        if(selectedId == R.id.sd_card){
                            getSupportActionBar().setTitle("SimpleFileManager");
                        }
                        navigationView.setCheckedItem(selectedId);
                    }
                });
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fm.beginTransaction().add(R.id.fragment_container , ExplorerFragment.getInstance("/sdcard/") , "visible_fragment").addToBackStack("0").commit();
        }
      }



// ================= helper Methods used inside the Activity ===========================//

//used to close the drawer smoothly
    private void closeDrawer(final DrawerLayout drawerLayout){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        } , 1);
    }

// replace the container fragment by fragment
// tag  used as the transaction name
    private void replaceFragment(final Fragment fragment , final String tag){
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fragment instanceof ExplorerFragment) {
                    getSupportFragmentManager().popBackStack("0", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    getSupportFragmentManager().popBackStack("0", 0);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container ,fragment,"visible_fragment").addToBackStack(tag).commit();
                //showFrameLayout();
            }
        },290);
    }

    private void showFrameLayout(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFrameLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        } , 1);
    }
    private void hideFrameLayout(){
        progressBar.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.INVISIBLE);
    }


// ====================== Interface's Methods Implementations ================== //

    @Override
    public void onLoadFinished() {
        mFrameLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onClick(int i) {
        ExplorerFragment fragment  = (ExplorerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        ArrayList<MediaFile> files = fragment.getFiles();
        String root                = fragment.getRoot();

        MediaFile clickedFile      = files.get(i);

        if(clickedFile instanceof Directory){
            String newRoot         = root + clickedFile.getName() + "/";
            Fragment newFragment   = ExplorerFragment.getInstance(newRoot);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container , newFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
        }
        else{

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);

            File file = new File(clickedFile.getAbsolutePathName());
            Uri fileUri = FileProvider.getUriForFile(this , BuildConfig.APPLICATION_ID + ".fileprovider" , file);
            intent.setDataAndType(fileUri,clickedFile.getMimeType());
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent chooser = Intent.createChooser(intent , "music");
            startActivity(chooser);

        }
    }


    @Override
    public void onBackPressed() {


        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if(fragment != null &&(fragment instanceof ExplorerFragment)){
                if(((ExplorerFragment)fragment).getRoot() == "/sdcard/"){
                    numClicks++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            numClicks =0;
                        }
                    } , 1000);
                    if(numClicks == 2){
                        getSupportFragmentManager().popBackStack();
                        super.onBackPressed();
                    }
                    else{
                        Toast.makeText(this , "press back again" , Toast.LENGTH_SHORT).show();
                    }
                }else{
                    super.onBackPressed();
                }
            }
            else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id  = menuItem.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        selectedId = id;
        switch (id){
            case R.id.all_musics:
                hideFrameLayout();
                replaceFragment(new MusicListFragment() , null);
                closeDrawer(drawerLayout);
                return true;
            case R.id.all_videos:
                hideFrameLayout();
                replaceFragment(new VideoListFragment() , null);
                closeDrawer(drawerLayout);
                return true;
            case R.id.sd_card:
                getSupportActionBar().setTitle(menuItem.getTitle());
                replaceFragment(ExplorerFragment.getInstance("/sdcard/")  , "0");
                closeDrawer(drawerLayout);
                return true;
            case R.id.all_images:
                hideFrameLayout();
                getSupportActionBar().setTitle(menuItem.getTitle());
                replaceFragment(new ImageListFragment() ,null);
                closeDrawer(drawerLayout);
                return true;
            case R.id.share:
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

}
