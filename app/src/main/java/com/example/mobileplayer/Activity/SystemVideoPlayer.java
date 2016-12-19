package com.example.mobileplayer.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ynagy.mobileplayer.R;

/**
 * Created by YnagY on 2016/12/19.
 */
public class SystemVideoPlayer extends Activity{

    private VideoView videoview;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);

        videoview = (VideoView) findViewById(R.id.videoview);


        //准备好播放的监听
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoview.start();//开始播放
            }
        });
        //播放出错的监听
        videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(SystemVideoPlayer.this,"视频播放出错",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //播放完成的监听
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(SystemVideoPlayer.this,"视频播放完毕",Toast.LENGTH_SHORT).show();
            }
        });

        uri = getIntent().getData();
        if (uri != null){
            videoview.setVideoURI(uri);
        }
        //设置播放器的播放面板
        videoview.setMediaController(new MediaController(this));
    }
}
