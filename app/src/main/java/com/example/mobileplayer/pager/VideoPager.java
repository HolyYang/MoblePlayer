package com.example.mobileplayer.pager;

import android.app.Activity;
import android.app.Fragment;
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
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobileplayer.Base.BasePager;
import com.example.mobileplayer.domain.MediaItem;
import com.example.ynagy.mobileplayer.R;

import java.text.Format;
import java.util.ArrayList;

import java.util.jar.Manifest;

/**
 * Created by YnagY on 2016/12/6.
 */

public class VideoPager extends BasePager {

    private ListView listView;
    private ProgressBar pb_loading;
    private TextView tv_nomedia;
    private VideoPagerAdapter videoPagerAdapter;
//装数据集合
    private ArrayList<MediaItem> mediaItems;

    public VideoPager(Context context) {
        super(context);
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (mediaItems != null &&mediaItems.size()>0){
                //有数据
                //设置适配器
                videoPagerAdapter = new VideoPagerAdapter();
                listView.setAdapter(videoPagerAdapter);
                //文本隐藏
                tv_nomedia.setVisibility(View.GONE);
            }else {
                //没有数据
                //文本显示
                tv_nomedia.setVisibility(View.VISIBLE);
            }
                //把文本和Praogress隐藏
            pb_loading.setVisibility(View.GONE);
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

    class VideoPagerAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mediaItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHoder viewHoder;
            if (view == null){
                view = View.inflate(context,R.layout.item_video_pager,null);
                viewHoder = new ViewHoder();
                viewHoder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                viewHoder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                viewHoder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                viewHoder.tv_size = (TextView) view.findViewById(R.id.tv_size);
                view.setTag(viewHoder);
            }else {
                viewHoder = (ViewHoder) view.getTag();
            }

            //得到数据
            MediaItem mediaItem = mediaItems.get(i);
            viewHoder.tv_name.setText(mediaItem.getName());

            viewHoder.tv_size.setText(Formatter.formatFileSize(context,mediaItem.getSize()));
//            viewHoder.tv_time.setText((int) mediaItem.getDuration());
            return view;
        }
    }
    static class ViewHoder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;

    }
}
