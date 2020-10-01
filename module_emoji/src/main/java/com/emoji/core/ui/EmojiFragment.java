package com.emoji.core.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.emoji.core.R;
import com.emoji.core.adapter.EmojiContainerAdapter;
import com.emoji.core.emoji.EmojiPanel;
import com.emoji.core.emoji.PanelController;
import com.emoji.core.view.CircleIndicator;

import java.util.List;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/25
 * create_time: 13:51
 * describe 用于显示 表情的界面,外部请不要直接使用该界面
 **/

@SuppressWarnings("all")
public class EmojiFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_emoji, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String emoJiType = getArguments().getString(PanelController.EMOJITYPE);
        final ViewPager viewPager = getView().findViewById(R.id.viewPager);
        final CircleIndicator circleIndicator = getView().findViewById(R.id.circleIndicator);
        final EmojiPanel panel = new EmojiPanel(getActivity(), emoJiType);

        panel.setPanelFaceListener(new EmojiPanel.PanelFaceListener() {
            @Override
            public void panelIsOk(List<View> viewsPanel) {
                EmojiContainerAdapter mAdapter = new EmojiContainerAdapter(viewsPanel);
                viewPager.setAdapter(mAdapter);
                circleIndicator.setViewPager(viewPager);
            }
        });
    }
}
