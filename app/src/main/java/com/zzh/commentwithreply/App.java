package com.zzh.commentwithreply;

import android.app.Application;
import android.graphics.Color;

import com.dz.utlis.JavaUtils;
import com.dz.utlis.ScreenUtils;
import com.dz.utlis.ToastTool;
import com.dz.utlis.UiCompat;
import com.dz.utlis.view.ToastConfig;
import com.zzh.commentwithreply.utils.DataHelper;

public class App extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        this.application = this;
        JavaUtils.isdebug = true;
        // Toast 配置
        ToastConfig config = new ToastConfig()
                .setInterval(2000)
                .setRadiusBg((int) ScreenUtils.dip2px(this, 30))
                .setToastTextColor(Color.WHITE)
                .setToastViewGroupBgColor(UiCompat.getColor(this.getResources(), R.color.light_blue_200))
                .setToastTextSize(16)
                .setBgPadding((int) ScreenUtils.dip2px(this, 15))
                .setShortToast(true)
                .setStrokeWidth(0)
                .setRadiusType(ToastConfig.RadiusType.ALL_RADIUS)
                .setStrokeColor(Color.TRANSPARENT);

        //初始化 Toast工具
        ToastTool.get().initConfig(this, config);


        DataHelper.initConfig(this);

    }

    public static Application get() {
        return application;
    }
}
