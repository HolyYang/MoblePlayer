package com.example.mobileplayer.pager;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileplayer.Base.BasePager;
import com.example.mobileplayer.domain.MediaItem;
import com.example.ynagy.mobileplayer.R;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by YnagY on 2016/12/6.
 */

public class VideoPager extends BasePager {

    private ListView listView;
    private ProgressBar pb_loading;
    private TextView tv_nomedia;
//装数据集合
    private ArrayList<MediaItem> mediaItems;

    public VideoPager(Context context) {
        super(context);
    }

//    private static boolean isGrantExternal(Activity activity){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_DENIED){
//            activity.requestPermissions(new String[]){
//
//                Manifest.
//            };
//        }
//        return false;
//    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (mediaItems != null &&mediaItems.size()>0){
                //有数据
                //设置适配器
                //文本隐藏
            }else {
                //没有数据
                //文本显示
            }
                //把文本和Praogress隐藏
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.video_pager,null);

        listView = (ListView) view.findViewById(R.id.listView);

        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);

        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("本地视频页面的数据说初始化了","0");
        getDataFromLocal();
    }
//从本地得到数据
    //1   遍历sd卡  后缀名(基本不用)
    //2   从内容提供者里面获取视频信息
    //3   如果是6.0需要加上动态读取sd卡的权限
    private void getDataFromLocal() {

        mediaItems = new ArrayList<>();

        new Thread(){

            @Override
            public void run() {
                super.run();
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI ;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sd卡中的名字
                        MediaStore.Video.Media.DURATION,//视频文件总时长
                        MediaStore.Video.Media.SIZE,//大小
                        MediaStore.Video.Media.DATA,//视频的绝对地址
                        MediaStore.Video.Media.ARTIST,//音乐的作者
                };
                Cursor cursor = resolver.query(uri,objs,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();

                        mediaItems.add(mediaItem);

                        String name = cursor.getString(0);//视频文件在sd卡中的名字
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频文件总时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//大小
                        mediaItem.setDuration(size);

                        String data = cursor.getString(3);//视频的绝对地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//音乐的作者
                        mediaItem.setArtist(artist);
                    }

                    cursor.close();
                }
                //发消息
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


}
