package com.emoji.core.emoji;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.emoji.core.R;
import com.emoji.core.adapter.EmojiAdapter;
import com.emoji.core.interfaces.EmojiViewListener;
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
 * create_time: 15:07
 * describe 处理每页滑动显示的表情
 **/

@SuppressWarnings("all")
public class EmojiPanel {

    private Context mContext;
    private EditText et_input_container;
    private int EMOJI_PAGE_COUNT = 20;
    private int mPageNum;
    private String emojiType;

    private List<String> emojiName = new ArrayList<>();
    private List<Integer> emojiId = new ArrayList<>();

    private EmojiHandlerThread emojiHandlerThread;

    public EmojiPanel(Context mContext, String emojiType) {
        this.mContext = mContext;

        if (mContext instanceof EmojiViewListener) {
            this.et_input_container = ((EmojiViewListener) mContext).bindInputEditView();
        }
        this.emojiType = emojiType;
        emojiHandlerThread = new EmojiHandlerThread();
        emojiHandlerThread.start();
    }


    /**
     * 开启线程处理表情
     */
    public class EmojiHandlerThread extends Thread {

        public EmojiHandlerThread() {
            super(EmojiHandlerThread.class.getSimpleName() + "---Thread");
        }

        @Override
        public void run() {
            super.run();

            final List<View> pageViewList = new ArrayList<>();
            LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = EmojiHelper.getInstance().emojiPack;
            LinkedHashMap<String, Integer> emojiDatas = emojiPack.get(emojiType);
            if (null != emojiDatas) {
                //遍历取出表情信息 分别装入 名称和 文件id 列表
                Set<Map.Entry<String, Integer>> mapSet = emojiDatas.entrySet();
                Iterator<Map.Entry<String, Integer>> iterator = mapSet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Integer> entry = iterator.next();
                    emojiName.add(entry.getKey());
                    emojiId.add(entry.getValue());
                    Log.e("----->", emojiName.get(emojiName.size() - 1));
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

    private PanelFaceListener panelFaceListener;

    public void setPanelFaceListener(PanelFaceListener panelFaceListener) {
        this.panelFaceListener = panelFaceListener;
    }

    public interface PanelFaceListener {
        void panelIsOk(List<View> viewsPanel);
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
        } else {
            emojiPageList = emojiName.subList((position - 1) * EMOJI_PAGE_COUNT, EMOJI_PAGE_COUNT * position);
            emojiPageListID = emojiId.subList((position - 1) * EMOJI_PAGE_COUNT, EMOJI_PAGE_COUNT * position);
        }
        mEmoJiListName.addAll(emojiPageList);
        mEmoJiListName.add("[删除]");
        mEmoJiListID.addAll(emojiPageListID);
        mEmoJiListID.add(emojiId.get(emojiId.size() - 1));

        final EmojiAdapter mEmoJiAdapter = new EmojiAdapter(mContext, mEmoJiListName, mEmoJiListID);
        eg_gridView.setAdapter(mEmoJiAdapter);
        eg_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionIndex, long id) {
                String fileName = mEmoJiAdapter.getItemEmojiName(positionIndex);
                // 不是删除键，显示表情
                if (fileName != "[删除]") {
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
        int selectionStart = et_input_container.getSelectionStart();
        String body = et_input_container.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(body);
        stringBuilder.insert(selectionStart, fileName);
        //显示到外部输入框
        et_input_container.setText(EmojiHelper.getInstance().parseEmojiType(mContext, emojiType, stringBuilder.toString()));
        et_input_container.setSelection(selectionStart + fileName.length());
    }

    /**
     * 删除表情或文字
     */
    private void deleteContent() {
        if (!TextUtils.isEmpty(et_input_container.getText())) {
            //获取光标位置
            int selectionStart = et_input_container.getSelectionStart();
            if (selectionStart > 0) {
                String body = et_input_container.getText().toString();
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
                        if (EmojiHelper.getInstance().emojiNames.contains(tempStr)) {
                            //删除表情
                            et_input_container.getEditableText().delete(i, selectionStart);
                        } else {
                            //删除一个字符
                            et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                        }
                    } else {
                        et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                    }
                } else {//非表情
                    et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                }
            }
        }
    }
}
