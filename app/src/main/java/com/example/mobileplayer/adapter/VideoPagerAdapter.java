package com.example.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileplayer.domain.MediaItem;
import com.example.mobileplayer.pager.VideoPager;
import com.example.ynagy.mobileplayer.R;

import java.util.ArrayList;

/**
 * Created by YnagY on 2016/12/19.
 */

public class VideoPagerAdapter extends BaseAdapter {


    private final Context context;
    private final ArrayList<MediaItem> mediaItems;

    public VideoPagerAdapter(Context context, ArrayList<MediaItem> mediaItems){
        this.context = context;
        this.mediaItems = mediaItems;
    }
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
            view = View.inflate(context, R.layout.item_video_pager,null);
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
        viewHoder.tv_time.setText(formatTime(mediaItem.getDuration()));
        return view;
    }
    static class ViewHoder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;

    }
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        return sb.toString();
    }
}

