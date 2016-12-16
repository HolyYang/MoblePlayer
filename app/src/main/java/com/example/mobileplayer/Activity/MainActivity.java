package com.example.mobileplayer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mobileplayer.Base.BasePager;
import com.example.mobileplayer.pager.AudioPager;
import com.example.mobileplayer.pager.NetAudioPager;
import com.example.mobileplayer.pager.NetVideoPager;
import com.example.mobileplayer.pager.VideoPager;
import com.example.ynagy.mobileplayer.R;

import java.util.ArrayList;

/**
 * Created by YnagY on 2016/12/5.
 */

public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main_content;

    private RadioGroup rg_bottom_tag;
//页面的集合
    private ArrayList<BasePager> basePagers;
//选中的位置
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl_main_content = (FrameLayout) findViewById(R.id.fl_main_content);

        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);


        basePagers = new ArrayList<>();
        basePagers.add(new VideoPager(this));
        basePagers.add(new AudioPager(this));
        basePagers.add(new NetVideoPager(this));
        basePagers.add(new NetAudioPager(this));

        //设置RadioGroup的监听
        rg_bottom_tag.setOnCheckedChangeListener(new MyOnClickedChangeListener());

        rg_bottom_tag.check(R.id.br_video);//默认选中首页认选中首页
    }
    class MyOnClickedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                default:

                    position = 0;
                    break;
                case R.id.br_audio:
                    position = 1;
                    break;
                case R.id.br_net_video:
                    position = 2;
                    break;
                case R.id.br_net_audio:
                    position = 3;
                    break;
            }
            setFragemnt();
        }
//将页面添加到Fragment
        private void setFragemnt() {
            //  1  得到Fragmentnger
            FragmentManager manager = getSupportFragmentManager();
            //  2  开启事物
            FragmentTransaction ft = manager.beginTransaction();
            //  3  替换
            ft.replace(R.id.fl_main_content,new Fragment(){
                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    BasePager basePager = getBasePager();
                    if (basePager != null){
                        //返回各个页面的视图
                        return basePager.rootView;
                    }
                    return null;
                }
            });
            //  4  提交事务
            ft.commit();
        }
        private BasePager getBasePager() {
            BasePager basePager = basePagers.get(position);
            if (basePager != null&&!basePager.isinitData){
                basePager.initData();
                basePager.isinitData = true;
            }
            return basePager;
        }
    }
}
