package com.example.simplefileexplorer;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.util.Date;

public class Video extends MediaFile {
    private Bitmap bitmap;
    private int mPosition;
    private RecyclerAdapter mAdapter;
    public Video(String name , String absolutePathName , double size , Date lastDateModified){
        super(name , absolutePathName , size , lastDateModified);

    }

    @Override
    public void setImage(ImageView image , RecyclerAdapter adapter , int position) {
       // Bitmap thumb = ThumbnailUtils.createVideoThumbnail(getAbsolutePathName(), MediaStore.Images.Thumbnails.MINI_KIND);
        //image.setImageBitmap(thumb);
        mPosition = position;
        mAdapter = adapter;
        if(bitmap != null){
            image.setImageBitmap(bitmap);
        }
        else{
            image.setImageResource(R.drawable.music_icon);
            new ThumbnailLoder().execute();
        }
    }

    public class ThumbnailLoder extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            bitmap = ThumbnailUtils.createVideoThumbnail(getAbsolutePathName(), MediaStore.Images.Thumbnails.MINI_KIND);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter.notifyItemChanged(mPosition);
        }
    }
}
