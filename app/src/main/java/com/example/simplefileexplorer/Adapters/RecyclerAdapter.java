package com.example.simplefileexplorer.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplefileexplorer.Models.Directory;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private ArrayList<MediaFile> mFiles;
    private RecyclerAdapterListener mRcyclerListener;
    private Fragment mFragment;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public Boolean isSelected(int position){
        return selectedItems.get(position , false);
    }

    public void toggleSelection(int position){
        if(selectedItems.get(position , false)){
            selectedItems.delete(position);
        }else{
            selectedItems.put(position , true);
        }
        //notifyItemChanged(position);
    }
    public void clearSelection(){
        ArrayList<Integer> selectedItemsKeys = getSelectedItemsKeys();
        selectedItems.clear();
        for(int i : selectedItemsKeys){
            notifyItemChanged(i);
        }
    }

    public ArrayList<Integer> getSelectedItemsKeys(){
            ArrayList<Integer> listOfKeys = new ArrayList<Integer>();
            for(int i = 0 ; i < selectedItems.size() ; i++){
                listOfKeys.add(selectedItems.keyAt(i));
            }
            return listOfKeys;
    }

    public int getSelectedItemsCount(){
        return selectedItems.size();
    }

    public RecyclerAdapter(ArrayList<MediaFile> files,RecyclerAdapterListener listener , Fragment fragment){

        mFragment = fragment;
        mFiles = files;
        mRcyclerListener = listener;
    }

    public interface RecyclerAdapterListener{
        void click(int position, View view);
        boolean onLongClick(int position, View view);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView name;
        public TextView date;
        public TextView size;
        public ImageView image;

        public MyViewHolder(View v){
            super(v);
            rootView = v;
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
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRcyclerListener.click(i , view);
            }
        });
        myViewHolder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mRcyclerListener.onLongClick(i,view);

            }
        });
        //mark the items that is selected
        //it is updated using the state drawable
        myViewHolder.rootView.setActivated(isSelected(i));

        MediaFile file = mFiles.get(i);
        String name =  file.getName();
        Date date  = file.getLastModifiedDate();
        double size = file.getSize();
        String absolutePathName = file.getAbsolutePathName();


        myViewHolder.name.setText(name);
        if(file instanceof Directory){
            File fileIo = new File(absolutePathName);
            int numberOfItems = fileIo.listFiles().length;
            myViewHolder.date.setText(numberOfItems + " items");
            myViewHolder.size.setText("");
        }else {
            DateFormat format = new SimpleDateFormat("MMM dd, yyyy, hh:mm a");
            String formatedDate = format.format(date);
            myViewHolder.date.setText(formatedDate);

            String formattedSize = String.format("%.2f", size);
            myViewHolder.size.setText(formattedSize + "MB");
        }
        file.setImage(myViewHolder.image, mFragment);

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }
}