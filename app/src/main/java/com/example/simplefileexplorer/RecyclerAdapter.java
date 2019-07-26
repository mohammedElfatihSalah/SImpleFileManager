package com.example.simplefileexplorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    ArrayList<MediaFile> mFiles;
    public RecyclerAdapter(ArrayList<MediaFile> files){
        mFiles = files;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView date;
        public TextView size;
        public ImageView image;

        public MyViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.tv_item_name);
            date = (TextView) v.findViewById(R.id.tv_item_date);
            size = (TextView) v.findViewById(R.id.tv_item_size);
            image = (ImageView) v.findViewById(R.id.item_icon);
        }

    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup , false);
        return (new RecyclerAdapter.MyViewHolder(item));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder myViewHolder, int i) {
        MediaFile file = mFiles.get(i);
        String name =  file.getName();
        Date date  = file.getLastModifiedDate();
        double size = file.getSize();

        myViewHolder.name.setText(name);

        DateFormat format = new SimpleDateFormat("MMM dd, yyyy, hh:mm a");
        String formatedDate = format.format(date);
        myViewHolder.date.setText(formatedDate);

        String formattedSize = String.format("%.2f" , size);
        myViewHolder.size.setText(formattedSize + "MB");

        file.setImage(myViewHolder.image , this , i);

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }
}