package com.room.core.utils;

import android.content.Context;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/19
 * create_time: 20:23
 * describe 单位换算
 **/
public class Utils {

    /**
     * 将px值转换为sp值
     * @param pxValue
     * @return
     */
    public static float px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue
     * @return
     */
    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

}
