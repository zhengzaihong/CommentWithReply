package com.zzh.corelib.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zzh.corelib.R;
import com.zzh.corelib.adapter.EmojiContainerAdapter;
import com.zzh.corelib.interfaces.EmojiViewListener;
import com.zzh.corelib.utils.EmojiHelper;
import com.zzh.corelib.view.CircleIndicator;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/25
 * creat_time: 13:51
 * describe 用于显示 表情的界面,外部请不要直接使用该界面，UiHelper提供
 **/
public class EmojiFragment extends Fragment {

    public EmojiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_emoji, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity fragmentActivity = getActivity();

        EditText et_input_container;
        if (fragmentActivity instanceof EmojiViewListener) {
            et_input_container = ((EmojiViewListener) fragmentActivity).getInputEditView();
        } else {
            throw new RuntimeException("please implements EmojiViewListener and return not null EditView");
        }

        String emoJiType = getArguments().getString("EmoJiType");
        ViewPager viewPager = getView().findViewById(R.id.viewPager);
        CircleIndicator circleIndicator = getView().findViewById(R.id.circleIndicator);
        EmojiHelper emojiHelper = new EmojiHelper(getContext(), emoJiType, et_input_container);
        EmojiContainerAdapter mAdapter = new EmojiContainerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(mAdapter);
        circleIndicator.setViewPager(viewPager);
    }

}
