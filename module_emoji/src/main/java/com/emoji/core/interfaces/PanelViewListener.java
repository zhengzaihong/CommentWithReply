package com.emoji.core.interfaces;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/25
 * create_time: 13:53
 * describe 均由外部提供给内部控件的接口
 **/
public interface PanelViewListener {

    /**
     * 外层传递输入框给内库
     *
     * @return
     */
    EditText bindInputEditView();


    /**
     * 表情提示面板view
     *
     * @return
     */
    View bindEmotionView();


    /**
     * 表情面板和输入法的切换开关
     * @return
     */
    CheckBox bindEmotionButton();


    /**
     * 发送按钮
     * @return
     */
    View bindSendButton();

    /**
     * 和表情面板关联的View
     * @return
     */
    View bindContentView();
}
