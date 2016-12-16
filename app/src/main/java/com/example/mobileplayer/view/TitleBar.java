package com.example.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ynagy.mobileplayer.R;

/**
 * Created by YnagY on 2016/12/14.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private View tv_search;

    private View rl_game;

    private View iv_record;

    private Context context;
    /*在代码中实例化该类的时候调用这个方法*/
    public TitleBar(Context context) {
        this(context,null);
    }
/*挡在布局文件中使用该类的时候  android系统通过这个构造方法实例化该类*/
    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
/*当需要设置样式的时候可以使用该实例化方法*/
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
/*当布局文件加载完成的时候回调这个方法*/
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //得到孩子的对象
        tv_search = getChildAt(1);

        rl_game = getChildAt(2);

        iv_record = getChildAt(3);

        //设置点击事件
        tv_search.setOnClickListener(this);
        rl_game.setOnClickListener(this);
        iv_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                Toast.makeText(context,"搜索",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_game:
                Toast.makeText(context,"游戏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_record:
                Toast.makeText(context,"记录",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
