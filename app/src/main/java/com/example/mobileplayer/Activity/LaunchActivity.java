package com.example.mobileplayer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import com.example.ynagy.mobileplayer.R;

public class LaunchActivity extends Activity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //两秒后才执行到这里
                //执行在主线程中
                startMainActivity();
                Log.e(TAG,"当前线程名字=="+Thread.currentThread().getName());
            }
        },2000);
    }
//跳转到主页面  并且关闭当前页面
    private void startMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent == Action"+event.getAction());
        startMainActivity();
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
