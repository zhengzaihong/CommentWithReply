package com.emoji.core.emoji;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/30
 * create_time: 14:16
 * describe 处理按钮显示
 **/
public class EditWatcher implements TextWatcher {

    private View view;
    private EditText editText;

    public EditWatcher(View view, EditText editText) {
        this.view = view;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0 || s.length() > 0){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
