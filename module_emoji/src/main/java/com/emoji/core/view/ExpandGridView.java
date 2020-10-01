package com.emoji.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/25
 * create_time: 16:36
 * describe 处理表情显示不完整问题
 **/
public class ExpandGridView extends GridView {

    public ExpandGridView(Context context) {
        super(context);
    }

    public ExpandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
