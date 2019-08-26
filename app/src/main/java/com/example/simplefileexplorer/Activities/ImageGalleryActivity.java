package com.example.simplefileexplorer.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.simplefileexplorer.Fragments.ImageFragment;
import com.example.simplefileexplorer.Models.FilesLab;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;

import java.util.ArrayList;

public class ImageGalleryActivity extends AppCompatActivity {
    private Gallery mGallery;
    private ViewPager mPager;
    private ArrayList<MediaFile> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImages = FilesLab.getInstance(this).getImages();
        mPager = new ViewPager(this);
        mPager.setId(R.id.viewPager);
        int position = getIntent().getIntExtra("position",0);

        mPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return ImageFragment.getInstance(i);
            }

            @Override
            public int getCount() {
                return mImages.size();
            }
        });
        mPager.setCurrentItem(position);
        mPager.setOffscreenPageLimit(1);
        setContentView(mPager);
        /*
        setContentView(R.layout.activity_image_gallery);
        mGallery = (Gallery) findViewById(R.id.g_gallery);
        mGallery.setAdapter(new CustomGalleryAdapter(FilesLab.getInstance(this).getImages() , this));
        int position = getIntent().getIntExtra("position",0);
        mGallery.setSelection(position , true);*/


    }
    private class CustomGalleryAdapter extends BaseAdapter{
        private ArrayList<MediaFile> mImages;
        Context mContext;
        public CustomGalleryAdapter(ArrayList<MediaFile> images , Context context){
            mImages = images;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(mContext);
            Bitmap bitmap = BitmapFactory.decodeFile(mImages.get(i).getAbsolutePathName());
            imageView.setImageBitmap(bitmap);
            //imageView.setLayoutParams(m);
            return imageView;
        }
    }
}
