package com.example.mobileplayer.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ynagy.mobileplayer.R;

/**
 * Created by YnagY on 2016/12/19.
 */
public class SystemVideoPlayer extends Activity implements View.OnClickListener {
//视频进度
    private static final int PROGRESS = 1;

    private VideoView videoview;
    private Uri uri;

    private LinearLayout llTop;
    private TextView tvName;
    private TextView tvTime;
    private Button btnVoice;
    private SeekBar seekbarSound;
    private Button btnSwitchPlayer;
    private LinearLayout llBottom;
    private TextView tvCurrtentTime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button tvTuichu;
    private Button tvHoutui;
    private Button tvZanting;
    private Button tvQianjin;
    private Button tvQuanping;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-20 16:35:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        llTop = (LinearLayout)findViewById( R.id.ll_top );
        tvName = (TextView)findViewById( R.id.tv_name );
        tvTime = (TextView)findViewById( R.id.tv_time );
        btnVoice = (Button)findViewById( R.id.btn_voice );
        seekbarSound = (SeekBar)findViewById( R.id.seekbar_sound );
        btnSwitchPlayer = (Button)findViewById( R.id.btn_switch_player );
        llBottom = (LinearLayout)findViewById( R.id.ll_bottom );
        tvCurrtentTime = (TextView)findViewById( R.id.tv_currtent_time );
        seekbarVideo = (SeekBar)findViewById( R.id.seekbar_video );
        tvDuration = (TextView)findViewById( R.id.tv_duration );
        tvTuichu = (Button)findViewById( R.id.tv_tuichu );
        tvHoutui = (Button)findViewById( R.id.tv_houtui );
        tvZanting = (Button)findViewById( R.id.tv_zanting );
        tvQianjin = (Button)findViewById( R.id.tv_qianjin );
        tvQuanping = (Button)findViewById( R.id.tv_quanping );

        btnVoice.setOnClickListener( this );
        btnSwitchPlayer.setOnClickListener( this );
        tvTuichu.setOnClickListener( this );
        tvHoutui.setOnClickListener( this );
        tvZanting.setOnClickListener( this );
        tvQianjin.setOnClickListener( this );
        tvQuanping.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-20 16:35:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnVoice ) {
            // Handle clicks for btnVoice
        } else if ( v == btnSwitchPlayer ) {
            // Handle clicks for btnSwitchPlayer
        } else if ( v == tvTuichu ) {
            // Handle clicks for tvTuichu
        } else if ( v == tvHoutui ) {
            // Handle clicks for tvHoutui
        } else if ( v == tvZanting ) {
            // Handle clicks for tvZanting
            if (videoview.isPlaying()){
                videoview.pause();
                tvZanting.setText("开始");
            }else {
                videoview.start();
                tvZanting.setText("暂停");
            }
        } else if ( v == tvQianjin ) {
            // Handle clicks for tvQianjin
        } else if ( v == tvQuanping ) {
            // Handle clicks for tvQuanping
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROGRESS:
                    //得到当前的进度
                    int current = videoview.getCurrentPosition();
                    //设置进度条
                    seekbarVideo.setProgress(current);


                    //更新进度条
                    tvCurrtentTime.setText(current+"s");
                    //每秒一次
                    removeMessages(PROGRESS);
                    //演示调用
                    sendEmptyMessageAtTime(PROGRESS,1000);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);

        findViews();
        videoview = (VideoView) findViewById(R.id.videoview);


        //准备好播放的监听
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoview.start();//开始播放

                int duration = videoview.getDuration();
                seekbarVideo.setMax(duration);

                tvDuration.setText(duration/1000+"s");
                handler.sendEmptyMessage(PROGRESS);
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
//        videoview.setMediaController(new MediaController(this));
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
