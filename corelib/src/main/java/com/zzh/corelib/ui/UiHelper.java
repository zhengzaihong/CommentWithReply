package com.zzh.corelib.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/25
 * creat_time: 14:05
 * describe 界面处理工具
 **/
public class UiHelper {


    public static void startEmojiFragment(Context context, @IdRes int containerViewId, Bundle bundle) {

        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        EmojiFragment emoJiFragment = new EmojiFragment();
        if (null == bundle) {
            bundle = new Bundle();
        }
        bundle.putString("EmoJiType","0");
        emoJiFragment.setArguments(bundle);
        fragmentTransaction.replace(containerViewId, emoJiFragment).commitAllowingStateLoss();

    }


}
