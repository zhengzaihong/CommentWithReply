package com.zzh.corelib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zzh.corelib.R;
import com.zzh.corelib.utils.Utils;
import com.zzh.corelib.config.CommentLayoutConfig;
import com.zzh.corelib.interfaces.CommentItemClickListener;
import com.zzh.corelib.interfaces.FillCommentListener;

import java.util.List;

import static com.zzh.corelib.interfaces.FillCommentListener.AUTHOR_REPLY;
import static com.zzh.corelib.interfaces.FillCommentListener.COMMENT_AUTHOR;
import static com.zzh.corelib.interfaces.FillCommentListener.REPLY_AUTHOR;
import static com.zzh.corelib.interfaces.FillCommentListener.USER_USER;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/18
 * creat_time: 19:57
 * describe 评论列表的容器
 **/

@SuppressWarnings("all")
public class CommentWithReplyContainer extends LinearLayout {

    private final String TAG = CommentWithReplyContainer.class.getSimpleName();

    private Context mContext;

    private final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
    private final int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    private FillCommentListener commentListener;

    private CommentItemClickListener commentItemClickListener;

    private CommentLayoutConfig commentLayoutConfig = new CommentLayoutConfig();

    public CommentWithReplyContainer(Context context) {
        this(context, null);
    }

    public CommentWithReplyContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentWithReplyContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
        mContext = this.getContext();
        setOrientation(VERTICAL);


        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.CommentStyle);

        commentLayoutConfig.setTextColor(ta.getColor(R.styleable.CommentStyle_textColor, Color.parseColor("#666666")))
                .setNickNameColor(ta.getColor(R.styleable.CommentStyle_nickNameColor, Color.parseColor("#81d4fa")))
                .setReplySplitColor(ta.getColor(R.styleable.CommentStyle_replySplitColor, Color.parseColor("#666666")))
                .setTextSize((int) Utils.px2sp(mContext,ta.getDimensionPixelSize(R.styleable.CommentStyle_textSize, 14)))
                .setReplyTips(TextUtils.isEmpty(ta.getString(R.styleable.CommentStyle_replyTips)) ?
                        commentLayoutConfig.getReplyTips() : ta.getString(R.styleable.CommentStyle_replyTips));


        ta.recycle();
    }


    /**
     * 填充数据
     *
     * @param itemPosition 该条动态的下标
     * @param datas        所有的动态数据源
     */
    public void setData(final int itemPosition, List<?> datas) {

        if (null == datas || datas.size() == 0) {
            return;
        }

        if (null != commentListener) {
            this.removeAllViews();
            for (int i = 0; i < datas.size(); i++) {
                final int index = i;
                CommentWithReplyLayout viewLine = getCommentWithReplyLayout();
                final Object obj = datas.get(i);

                //匹配评论类型
                int type = commentListener.commentType(obj);
                if (type == AUTHOR_REPLY) {
                    commentListener.authorReply(obj, viewLine);
                } else if (type == REPLY_AUTHOR) {
                    commentListener.replyAuthor(obj, viewLine);
                } else if (type == COMMENT_AUTHOR) {
                    commentListener.commentAuthor(obj, viewLine);
                } else if (type == USER_USER) {
                    commentListener.userReplyUser(obj, viewLine);
                }

                //添加 每行评论的点击事件
                viewLine.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentItemClickListener.onClick(itemPosition, index, obj);
                    }
                });

                //填充到容器
                this.addView(viewLine);
            }
        } else {
            Log.e(TAG, "请先设置FillCommentListener 监听后再添加数据");
            throw new IllegalArgumentException();
        }
    }


    /**
     * 创建一行 评论布局
     *
     * @return
     */
    private CommentWithReplyLayout getCommentWithReplyLayout() {
        return new CommentWithReplyLayout(mContext).setConfig(commentLayoutConfig);
    }

    /**
     * 填充数据的监听,必须设置该监听，否则不填充数据
     *
     * @param listener
     */
    public void setFillCommentListener(FillCommentListener listener) {
        this.commentListener = listener;
    }


    /**
     * 设置评论的点击事件
     *
     * @param clickListener
     */
    public void setCommentItemClickListener(CommentItemClickListener clickListener) {
        this.commentItemClickListener = clickListener;
    }


}
