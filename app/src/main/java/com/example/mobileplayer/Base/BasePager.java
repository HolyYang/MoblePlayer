package com.example.mobileplayer.Base;

import android.content.Context;
import android.view.View;

/**
 * Created by YnagY on 2016/12/6.
 */

public abstract class BasePager {

//上下文
    public final Context context;

    public View rootView;
    public boolean isinitData;

    public BasePager(Context context){
        this.context = context;
        rootView = initView();
    }
//c抽象方法  由子类实现
    public abstract View initView();

//当子页面初始化数据,请求数据,或者帮顶数据的时候调用该方法
    public void initData(){

    }
}
