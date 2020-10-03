package com.emoji.core.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.emoji.core.R;
import com.emoji.core.adapter.EmojiAdapter;
import com.emoji.core.adapter.EmojiContainerAdapter;
import com.emoji.core.emoji.EmojiFactory;
import com.emoji.core.emoji.PanelController;
import com.emoji.core.interfaces.PanelViewListener;
import com.emoji.core.interfaces.PanelFaceListener;
import com.emoji.core.view.CircleIndicator;
import com.emoji.core.view.ExpandGridView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/25
 * create_time: 13:51
 * describe 用于显示 表情的界面,外部请不要直接使用该界面
 **/

@SuppressWarnings("all")
public class EmojiFragment extends Fragment {

    private Activity mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_emoji, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();

        String emoJiType = getArguments().getString(PanelController.EMOJITYPE);
        final ViewPager viewPager = getView().findViewById(R.id.viewPager);
        final CircleIndicator circleIndicator = getView().findViewById(R.id.circleIndicator);
        final PanelView panel = new PanelView(emoJiType);

        panel.setPanelFaceListener(new PanelFaceListener() {
            @Override
            public void panelIsOk(List<View> viewsPanel) {
                EmojiContainerAdapter mAdapter = new EmojiContainerAdapter(viewsPanel);
                viewPager.setAdapter(mAdapter);
                circleIndicator.setViewPager(viewPager);
            }
        });
    }

    /**
     * 处理每页滑动显示的表情
     */
    private class PanelView {

        private EditText inputContainer;
        private int EMOJI_PAGE_COUNT = 20;
        private int mPageNum;
        private String emojiType;

        private List<String> emojiName = new ArrayList<>();
        private List<Integer> emojiId = new ArrayList<>();

        private EmojiHandlerThread emojiHandlerThread;
        private PanelFaceListener panelFaceListener;

        public PanelView(String emojiType) {
            this.inputContainer = ((PanelViewListener) mContext).bindInputEditView();
            this.emojiType = emojiType;
            emojiHandlerThread = new EmojiHandlerThread();
            emojiHandlerThread.start();
        }


        public void setPanelFaceListener(PanelFaceListener panelFaceListener) {
            this.panelFaceListener = panelFaceListener;
        }

        /**
         * 开启线程处理表情
         */
        public class EmojiHandlerThread extends Thread {

            @Override
            public void run() {
                super.run();

                final List<View> pageViewList = new ArrayList<>();
                LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = EmojiFactory.getInstance().getEmojiPack();
                LinkedHashMap<String, Integer> emojiDatas = emojiPack.get(emojiType);
                if (null != emojiDatas) {
                    //遍历取出表情信息 分别装入 名称和 文件id 列表
                    Set<Map.Entry<String, Integer>> mapSet = emojiDatas.entrySet();
                    Iterator<Map.Entry<String, Integer>> iterator = mapSet.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Integer> entry = iterator.next();
                        emojiName.add(entry.getKey());
                        emojiId.add(entry.getValue());
                    }
                }

                mPageNum = (int) Math.ceil(emojiName.size() * 1.0f / EMOJI_PAGE_COUNT);
                for (int position = 1; position <= mPageNum; position++) {
                    pageViewList.add(getGridView(position));
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        panelFaceListener.panelIsOk(pageViewList);
                    }
                });
            }
        }


        private View getGridView(int position) {

            List<String> mEmoJiListName = new ArrayList<>();
            List<Integer> mEmoJiListID = new ArrayList<>();
            View containerView = View.inflate(mContext, R.layout.container_gridview, null);
            ExpandGridView eg_gridView = containerView.findViewById(R.id.eg_gridView);
            eg_gridView.setGravity(Gravity.CENTER_VERTICAL);
            List<String> emojiPageList;
            List<Integer> emojiPageListID;
            //最后一页
            if (position == mPageNum) {
                emojiPageList = emojiName.subList((position - 1) * EMOJI_PAGE_COUNT, emojiName.size());
                emojiPageListID = emojiId.subList((position - 1) * EMOJI_PAGE_COUNT, emojiId.size());
                mEmoJiListName.addAll(emojiPageList);
                mEmoJiListID.addAll(emojiPageListID);
            } else {
                emojiPageList = emojiName.subList((position - 1) * EMOJI_PAGE_COUNT, EMOJI_PAGE_COUNT * position);
                emojiPageListID = emojiId.subList((position - 1) * EMOJI_PAGE_COUNT, EMOJI_PAGE_COUNT * position);
                mEmoJiListName.addAll(emojiPageList);
                mEmoJiListName.add(PanelController.DELETKEY);
                mEmoJiListID.addAll(emojiPageListID);
                mEmoJiListID.add(emojiId.get(emojiId.size() - 1));
            }


            final EmojiAdapter mEmoJiAdapter = new EmojiAdapter(mContext, mEmoJiListName, mEmoJiListID);
            eg_gridView.setAdapter(mEmoJiAdapter);
            eg_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int positionIndex, long id) {
                    String fileName = mEmoJiAdapter.getItemEmojiName(positionIndex);
                    // 不是删除键，显示表情
                    if (fileName != PanelController.DELETKEY) {
                        showEmoJi(fileName);
                    } else {
                        // 删除文字或者表情
                        deleteContent();
                    }
                }
            });
            return containerView;
        }

        /**
         * 显示EmoJi表情
         *
         * @param fileName
         */
        private void showEmoJi(String fileName) {
            int selectionStart = inputContainer.getSelectionStart();
            String body = inputContainer.getText().toString();
            StringBuilder stringBuilder = new StringBuilder(body);
            stringBuilder.insert(selectionStart, fileName);
            //显示到外部输入框
            inputContainer.setText(EmojiFactory.getInstance().parseEmojiType(mContext, emojiType, stringBuilder.toString()));
            inputContainer.setSelection(selectionStart + fileName.length());
        }

        /**
         * 删除表情或文字
         */
        private void deleteContent() {
            if (!TextUtils.isEmpty(inputContainer.getText())) {
                //获取光标位置
                int selectionStart = inputContainer.getSelectionStart();
                if (selectionStart > 0) {
                    String body = inputContainer.getText().toString();
                    //获取最后一个字符
                    String lastStr = body.substring(selectionStart - 1, selectionStart);
                    //表情
                    if (lastStr.equals(PanelController.EMOJIENDTAG)) {
                        //从中间开始删除
                        if (selectionStart < body.length()) {
                            body = body.substring(0, selectionStart);
                        }
                        int i = body.lastIndexOf(PanelController.EMOJISTARTTAG);
                        if (i != -1) {
                            //截取表情码
                            String tempStr = body.substring(i, selectionStart);
                            //校验是否是表情
                            if (EmojiFactory.getInstance().getEmojiNames().contains(tempStr)) {
                                //删除表情
                                inputContainer.getEditableText().delete(i, selectionStart);
                            } else {
                                //删除一个字符
                                inputContainer.getEditableText().delete(selectionStart - 1, selectionStart);
                            }
                        } else {
                            inputContainer.getEditableText().delete(selectionStart - 1, selectionStart);
                        }
                    } else {//非表情
                        inputContainer.getEditableText().delete(selectionStart - 1, selectionStart);
                    }
                }
            }
        }


    }

}
