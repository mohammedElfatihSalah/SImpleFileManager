package com.example.simplefileexplorer.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplefileexplorer.Adapters.RecyclerAdapter;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.Models.MediaFilesFactory;
import com.example.simplefileexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExplorerFragment extends Fragment implements RecyclerAdapter.RecyclerAdapterListener {
    private ExplorerFragmentListener mListener;
    private String mRoot;
    private ArrayList<MediaFile> mFiles;
    private ActionMode mActionMode;
    private ActionMode.Callback mActionCallBack;
    private RecyclerAdapter mAdapter;

    //two ArrayString containing the paths for
    //specific operation
    //cut or copy
    private static ArrayList<String> itemsToCopy;
    private static ArrayList<String> itemsToCut;
    //constant used for the as a key for arguments
    private static final String ROOT = "root";
    //constant used for request rename operation
    private  static final int REQUEST_RENAME = 20;

    //constant name for the rename dialog fragment
    private static final String RENAME_DIALOG = "rename";




    @Override
    public void onStart() {
        super.onStart();
        mListener = (ExplorerFragmentListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFiles = new ArrayList<MediaFile>();
        mRoot = getArguments().getString(ROOT);

        mActionCallBack = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater1 = actionMode.getMenuInflater();
                inflater1.inflate(R.menu.context_action_bar, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                int id = menuItem.getItemId();
                ArrayList<Integer> selectedItems = mAdapter.getSelectedItemsKeys();
                switch (id){
                    case R.id.context_edit:
                        MediaFile file = mFiles.get(selectedItems.get(0));
                        String name = file.getName();
                        RenameFragment dialog = RenameFragment.newInstance(name);
                        dialog.setTargetFragment(ExplorerFragment.this , REQUEST_RENAME);
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        dialog.show(fm , RENAME_DIALOG);
                        return true;
                    case R.id.context_delete:
                        //mActionMode.finish();
                        for(int position : selectedItems){
                            File file1 = new File(mFiles.get(position).getAbsolutePathName());
                            file1.delete();
                        }
                        Comparator<Integer> c = Collections.reverseOrder();
                        Collections.sort(selectedItems , c);
                        for(int position : selectedItems){
                            mFiles.remove(position);
                        }
                        mAdapter.notifyDataSetChanged();

                        mActionMode.finish();
                        return true;
                    case R.id.context_copy:
                        itemsToCopy = new ArrayList<String>();
                        for(int i : selectedItems){
                            itemsToCopy.add(mFiles.get(i).getAbsolutePathName());
                        }
                        getActivity().invalidateOptionsMenu();
                        mActionMode.finish();

                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                mActionMode = null;
                mAdapter.clearSelection();
            }
        };
        loadFiles();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(itemsToCopy != null){
            inflater.inflate(R.menu.paste_menu , menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_paste:
                //here we implement the copy opertation

                //end of the implementation
                itemsToCopy = null;
                getActivity().invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_items);
        mAdapter = new RecyclerAdapter(mFiles, this, this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        setHasOptionsMenu(true);
        return v;
    }

    public ExplorerFragment() {
        // Required empty public constructor
    }


    private void loadFiles() {

        File currentFile = new File(mRoot);
        for (File file : currentFile.listFiles()) {
            String name = file.getName();
            if (name.startsWith(".")) {
                continue;
            }
            if (file.isDirectory()) {
                mFiles.add(MediaFilesFactory.getInstance(file));
            } else {
                mFiles.add(0, MediaFilesFactory.getInstance(file));
            }

        }
    }

    public static Fragment getInstance(String root) {
        Fragment fragment = new ExplorerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ROOT, root);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void click(int position, View view) {

        if (mActionMode != null){
            mAdapter.toggleSelection(position);
            view.setActivated(mAdapter.isSelected(position));
            updateContextAction();
        } else{
            mListener.onClick(position);
        }
    }

    @Override
    public boolean onLongClick(int position, View view) {
        if (mActionMode != null) {
            mAdapter.toggleSelection(position);
            view.setActivated(mAdapter.isSelected(position));
            updateContextAction();
            return true;
        }else {
            mActionMode = getActivity().startActionMode(mActionCallBack);
            mAdapter.toggleSelection(position);
            view.setActivated(mAdapter.isSelected(position));
            updateContextAction();
        }
        return true;
    }


    private void updateContextAction(){
        int numberOfSelectedItems = mAdapter.getSelectedItemsCount();
        String number             = numberOfSelectedItems + " Items";
        mActionMode.setTitle(number);
    }
    public interface ExplorerFragmentListener {
        void onClick(int i);
    }

    public String getRoot() {
        return mRoot;
    }

    public ArrayList<MediaFile> getFiles() {
        return mFiles;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_RENAME) {

            ArrayList<Integer> selectedItems = mAdapter.getSelectedItemsKeys();
            int position = selectedItems.get(0);
            MediaFile file = mFiles.get(position);


            if(resultCode == Activity.RESULT_OK){
               //make the rename operation
                String newName = data.getStringExtra(RenameFragment.EXTRA_NAME);

                File file1 = new File(file.getAbsolutePathName());
                File file2 = new File(file1.getParent() , newName);
                file1.renameTo(file2);
                file = MediaFilesFactory.getInstance(file2);
                mFiles.set(position ,file);
                mAdapter.notifyItemChanged(position);
                mActionMode.finish();
                mAdapter.clearSelection();
            }

        }
    }
}

    //=====================working area===============//
