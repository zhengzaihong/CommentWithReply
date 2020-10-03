package com.emoji.core.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.emoji.core.emoji.EditWatcher;
import com.emoji.core.emoji.PanelController;
import com.emoji.core.interfaces.PanelViewListener;
import com.emoji.core.interfaces.SoftInputlListener;
import com.emoji.core.ui.EmojiFragment;

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/2/11 0011
 * create_time: 17:05
 * describe: TODO
 **/


public class EmotionInputDetector {

    private static final String SHARE_PREFERENCE_NAME = "com.easy.emoji";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private boolean outTouchOffSort = false;
    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private View mEmotionLayout;
    private CheckBox mEmojiView;
    private EditText mEditText;
    private View mSendButton;
    private View contentPannel;
    private PanelViewListener panelViewListener;
    private SoftInputlListener softInputlListener;

    private FragmentTransaction fragmentTransaction;

    private EmotionInputDetector() {
    }

    public static EmotionInputDetector with(Activity activity) {
        EmotionInputDetector emotionInputDetector = new EmotionInputDetector();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }


    public EmotionInputDetector setPanelViewListener(PanelViewListener panelViewListener) {
        this.panelViewListener = panelViewListener;
        return this;
    }

    public EmotionInputDetector setSoftInputlListener(SoftInputlListener softInputlListener) {
        this.softInputlListener = softInputlListener;
        return this;
    }

    public EmotionInputDetector setOutTouchOffSort(boolean outTouchOffSort) {
        this.outTouchOffSort = outTouchOffSort;
        return this;
    }

    /**
     * 开启表情面板
     *
     * @param containerViewId 容器id
     * @param emojiType       表情包的key
     */
    public EmotionInputDetector setPannelFragment(@IdRes int containerViewId, String emojiType) {
        this.setPannelFragment(containerViewId, emojiType, null);
        return this;
    }


    public EmotionInputDetector setPannelFragment(@IdRes int containerViewId, String emojiType, Bundle bundle) {
        fragmentTransaction = ((AppCompatActivity) mActivity).getSupportFragmentManager().beginTransaction();
        EmojiFragment emoJiFragment = new EmojiFragment();
        if (null == bundle) {
            bundle = new Bundle();
        }
        bundle.putString(PanelController.EMOJITYPE, emojiType);
        emoJiFragment.setArguments(bundle);

        fragmentTransaction.replace(containerViewId, emoJiFragment).commitAllowingStateLoss();
        return this;
    }


    public EmotionInputDetector build() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();
        if(null!=softInputlListener){
            softInputlListener.softInputClosed();
        }
        return this;
    }


    public EmotionInputDetector create() {

        this.mEditText = panelViewListener.bindInputEditView();
        this.mEmotionLayout = panelViewListener.bindEmotionView();
        this.mEmojiView = panelViewListener.bindEmotionButton();
        this.mSendButton = panelViewListener.bindSendButton();
        this.contentPannel = panelViewListener.bindContentView();

        if (null == mEditText || mEmotionLayout == null || mEmojiView == null || mSendButton == null) {
            throw new RuntimeException("please implement PanelViewListener and return not null view");
                                      }

                mEmojiView.setOnClickListener(new View.OnClickListener() {
                    @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        mEmojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });

        if (outTouchOffSort) {
            contentPannel.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    build();
                    return false;
                }
            });
        }
        mEditText.addTextChangedListener(new EditWatcher(mSendButton, mEditText));
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    mEmojiView.setChecked(false);
                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });


        return this;
    }

    public boolean interceptBackPress() {
        mEmojiView.setChecked(!mEmojiView.isChecked());
        if (mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 400);
        }
        hideSoftInput();
        mEmotionLayout.getLayoutParams().height = softInputHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);
    }

    private void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentPannel.getLayoutParams();
        params.height = contentPannel.getHeight();
        params.weight = 0.0F;
        contentPannel.setLayoutParams(params);
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentPannel.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

}
