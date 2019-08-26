package com.example.simplefileexplorer.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.example.simplefileexplorer.BuildConfig;
import com.example.simplefileexplorer.Fragments.FileListFragment;
import com.example.simplefileexplorer.Models.FilesLab;
import com.example.simplefileexplorer.Models.MediaFile;
import com.example.simplefileexplorer.Models.Music;

import java.io.File;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends FileListFragment {
    @Override
    protected MediaFile getFileInstance(String name, String absolutePathName, double size, Date lastDateModified) {
        return new Music(name , absolutePathName , size , lastDateModified);
    }

    @Override
    protected boolean isRequiredFile(String name) {
        return name.endsWith(".mp3");
    }

    @Override
    protected void setFilesInitialized(boolean bool) {
        FilesLab.getInstance(getActivity()).isMusicsInitialized = bool;
    }

    @Override
    protected boolean isFilesInitialized() {
        return FilesLab.getInstance(getActivity()).isMusicsInitialized;
    }

    @Override
    protected void initializeFiles() {
        mFiles = FilesLab.getInstance(getActivity()).getMusics();
    }

    @Override
    public void click(int position , View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MediaFile file  = mFiles.get(position);
        File file1 = new File(file.getAbsolutePathName());
        Uri fileUri = FileProvider.getUriForFile(getActivity() , BuildConfig.APPLICATION_ID + ".fileprovider" , file1);
        intent.setDataAndType(fileUri,file.getMimeType());
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooser = Intent.createChooser(intent , "music");
        startActivity(chooser);
    }

    @Override
    public String getTitle() {
        return "All Musics";
    }

    @Override
    protected Uri getUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
    /*
    private ArrayList<Music> mMusics;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecycler;

    public MusicListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusics = FilesLab.getInstance(getActivity()).getMusics();

       /* setRetainInstance(true);
        mAudio = new AudioPlayer(getActivity());
        mMusics = FilesLab.getInstance(getActivity()).getMusics();
        ArrayAdapter<Music> arrayAdapter = new ArrayAdapter<>(getActivity() , android.R.layout.simple_list_item_1
        ,mMusics);
        setListAdapter(new MusicArrayAdapter(mMusics));
        Log.v("music : ", FilesLab.isMusicsInitialized + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_recycler_view,container,false);
        mLoadingIndicator = (ProgressBar) v.findViewById(R.id.pb_loading_indicator);
        mRecycler = (RecyclerView) v.findViewById(R.id.rv_items);
        if(FilesLab.isMusicsInitialized){

            mRecycler.setAdapter(new RecyclerArrayAdapter(mMusics));
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setLayoutManager(manager);
        }else{
            new MusicLoader().execute();
        }
        return v;
    }

    /*public void updateData(){
        if(getListAdapter() != null) {
            ((ArrayAdapter<Music>) getListAdapter()).notifyDataSetChanged();
        }
    }
    public class RecyclerArrayAdapter extends RecyclerView.Adapter<RecyclerArrayAdapter.MyViewHolder>{
        ArrayList<Music> mMusics;
        public RecyclerArrayAdapter(ArrayList<Music> musics){
            mMusics = musics;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView date;
            public TextView size;

            public MyViewHolder(View v){
                super(v);
                name = (TextView) v.findViewById(R.id.tv_item_name);
                date = (TextView) v.findViewById(R.id.tv_item_date);
                size = (TextView) v.findViewById(R.id.tv_item_size);
            }

        }

        @Override
        public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
            View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup , false);
            return (new MyViewHolder(item));
        }


        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            Music music = mMusics.get(i);
            String name =  music.getName();
            Date date  = music.getLastModifiedDate();
            double size = music.getSize();

            myViewHolder.name.setText(name);

            DateFormat format = new SimpleDateFormat("MMM dd, yyyy, hh:mm a");
            String formatedDate = format.format(date);
            myViewHolder.date.setText(formatedDate);

            String formattedSize = String.format("%.2f" , size);
            myViewHolder.size.setText(formattedSize + "MB");

        }

        @Override
        public int getItemCount() {
            return mMusics.size();
        }
    }

    public class MusicLoader extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            FilesLab.getInstance(getActivity()).initializeMusics(FilesLab.ROOT_DIR);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            FilesLab.isMusicsInitialized = true;
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mRecycler.setAdapter(new RecyclerArrayAdapter(mMusics));
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecycler.setLayoutManager(manager);
        }
    }

  /*  @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Music music = mMusics.get(position);
        String path = music.getAbsolutePathName();
        //Toast.makeText(getActivity() , path , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path),"audio/*");
        startActivity(intent);
    }*/

  /*
    private class MusicArrayAdapter extends ArrayAdapter<Music>{
        public MusicArrayAdapter(ArrayList<Music> musics){
            super(getActivity() , 0 , musics);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item,null);
            }
            Music music = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.tv_item_name);
            TextView date = (TextView) convertView.findViewById(R.id.tv_item_date);
            TextView size = (TextView) convertView.findViewById(R.id.tv_item_size);

            name.setText(music.getName());

            DateFormat format = new SimpleDateFormat("MMM dd, yyyy, hh:mm a");
            String formatedDate = format.format(music.getLastModifiedDate());
            date.setText(formatedDate);

            String formattedSize = String.format("%.2f" , music.getSize());
            size.setText(formattedSize + "MB");



            return convertView;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }*/
}

