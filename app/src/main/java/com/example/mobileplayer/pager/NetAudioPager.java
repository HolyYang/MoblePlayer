package com.example.mobileplayer.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.mobileplayer.Base.BasePager;

/**
 * Created by YnagY on 2016/12/6.
 */

public class NetAudioPager extends BasePager {
    private TextView textView;

    public NetAudioPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        Log.e("网络音乐页面说初始化了","0");
        textView = new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("网络音乐页面的数据说初始化了","0");
        textView.setText("网络音乐页面");
    }
}
