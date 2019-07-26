package com.example.simplefileexplorer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mMusicButton;
    private Button mVideoButton;
    private Button mImagesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMusicButton = (Button) findViewById(R.id.bt_music_explore);
        mVideoButton = (Button) findViewById(R.id.bt_video_explore);
        mImagesButton = (Button) findViewById(R.id.bt_image_explore);
        mMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this , MusicListActivity.class);
                intent.putExtra(MusicListActivity.TYPE , 0);
                startActivity(intent);
            }
        });

        mVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this , MusicListActivity.class);
                intent.putExtra(MusicListActivity.TYPE , 1);
                startActivity(intent);

            }
        });
    }

}
