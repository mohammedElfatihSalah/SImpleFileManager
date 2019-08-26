package com.example.simplefileexplorer.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.simplefileexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenameFragment extends DialogFragment {
    private EditText mEditText;

    public static final String EXTRA_NAME  = "name";


    public RenameFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        String name = (String)getArguments().get(EXTRA_NAME);
        View v = getActivity().getLayoutInflater().inflate(R.layout.rename_dialog ,null);
        mEditText = (EditText) v.findViewById(R.id.et_edit_name);
        mEditText.setText(name);
        Dialog mDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.rename_dialog).setView(v)
                .setPositiveButton(R.string.edit_name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_NAME , mEditText.getText().toString());
                        getTargetFragment().onActivityResult(getTargetRequestCode() , Activity.RESULT_OK , intent);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getTargetFragment().onActivityResult(getTargetRequestCode() , Activity.RESULT_CANCELED ,null );
                    }
                })
                .create();
        return mDialog;
    }
    public static RenameFragment newInstance(String name){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME , name);
        RenameFragment fragment = new RenameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
