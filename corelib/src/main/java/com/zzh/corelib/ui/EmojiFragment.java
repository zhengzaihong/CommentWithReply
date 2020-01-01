package com.zzh.corelib.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zzh.corelib.R;
import com.zzh.corelib.adapter.EmojiContainerAdapter;
import com.zzh.corelib.emoji.EditWatcher;
import com.zzh.corelib.interfaces.EmojiViewListener;
import com.zzh.corelib.emoji.EmojiPanel;
import com.zzh.corelib.emoji.PanelController;
import com.zzh.corelib.view.CircleIndicator;

import java.util.List;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/25
 * creat_time: 13:51
 * describe 用于显示 表情的界面,外部请不要直接使用该界面，FaceUiHelper 提供
 **/

@SuppressWarnings("all")
public class EmojiFragment extends Fragment {

    private static final String SHARE_PREFERENCE_NAME = "com.easy.emoji";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private View mEmotionLayout;
    private CheckBox mEmojiView;
    private EditText mEditText;
    private View mContentView;
    private View mSendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.fragment_emoji, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();

        this.mInputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.sp = mActivity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (mActivity instanceof EmojiViewListener) {
            EmojiViewListener listener = ((EmojiViewListener) mActivity);
            this.mEditText = listener.bindInputEditView();
            this.mEmotionLayout= listener.bindEmotionView();
            this.mEmojiView= listener.bindEmotionButton();
            this.mSendButton= listener.bindSendButton();
            this.mContentView= listener.bindContentView();

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

        } else {
            throw new RuntimeException("please implements EmojiViewListener and return not null view");
        }



        String emoJiType = getArguments().getString(PanelController.EMOJITYPE);
        final ViewPager viewPager = getView().findViewById(R.id.viewPager);
        final CircleIndicator circleIndicator = getView().findViewById(R.id.circleIndicator);
        final EmojiPanel panel = new EmojiPanel(getContext(), emoJiType, mEditText);

        panel.setPanelFaceListener(new EmojiPanel.PanelFaceListener() {
            @Override
            public void panelIsOk(List<View> viewsPanel) {
                EmojiContainerAdapter mAdapter = new EmojiContainerAdapter(viewsPanel);
                viewPager.setAdapter(mAdapter);
                circleIndicator.setViewPager(viewPager);
            }
        });

    }


    public void build() {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        hideSoftInput();
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;
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
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
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
