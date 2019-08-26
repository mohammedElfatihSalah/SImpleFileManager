package com.example.simplefileexplorer.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.simplefileexplorer.Models.FilesLab;
import com.example.simplefileexplorer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    public static final String IMAGE = "image";
    private Bitmap mImage;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt(IMAGE);
        mImage  = BitmapFactory.decodeFile(FilesLab.getInstance(getActivity()).getImages().get(position).getAbsolutePathName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView image =(ImageView) v.findViewById(R.id.iv_image);
        image.setImageBitmap(mImage);
        return v;
    }

    public static Fragment getInstance(int i){
        Fragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMAGE , i);
        fragment.setArguments(bundle);
        return fragment;
    }
}
