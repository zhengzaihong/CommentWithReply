package com.zzh.commentwithreply.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.dz.utlis.ScreenUtils;
import com.dz.utlis.ToastTool;
import com.zzh.commentwithreply.R;


public class InputInfoDialog {

    private TextView fvSend;

    private EditText etInputContent;

    private Dialog dialog;
    private View view;

    private boolean autoClose = true;

    private boolean outTouchside = true;

    private String content;

    private Context mContext;

    public InputInfoDialog(final Context context) {

        mContext = context;
        dialog = new Dialog(context, R.style.mOftenDialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_layout_input_info, null);
        dialog.setContentView(view);
        DisplayMetrics outMetrics = new DisplayMetrics();
        dialog.setCanceledOnTouchOutside(outTouchside);
        window.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        window.getAttributes().width = ScreenUtils.getScreenWidth(mContext);
        fvSend = window.findViewById(R.id.fvSend);

        etInputContent = window.findViewById(R.id.etInputContent);

        fvSend.setOnClickListener(v -> {

            content = etInputContent.getText().toString();

            if (TextUtils.isEmpty(content)) {
                ToastTool.showContent("请输入内容！");
                return;
            }
            if (null != callBack) {
                callBack.callBack(content);
            }

            if (autoClose) {
                dismiss();
            }
        });
    }


    private CallBack callBack;

    public InputInfoDialog initTips(String content) {
        etInputContent.setHint(content);
        return this;
    }

    public InputInfoDialog setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {

        void callBack(String str);
    }

    public InputInfoDialog setOutTouchside(boolean outTouchside) {
        dialog.setCanceledOnTouchOutside(outTouchside);
        return this;
    }


    public void showDialog() {
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }


    public boolean isShowing() {
        return null != dialog && dialog.isShowing();
    }

    public void dismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
