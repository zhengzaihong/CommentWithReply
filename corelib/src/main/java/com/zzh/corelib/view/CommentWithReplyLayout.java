package com.zzh.corelib.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zzh.corelib.config.CommentLayoutConfig;
import com.zzh.corelib.utils.EmojiUtils;

import static com.zzh.corelib.interfaces.FillCommentListener.AUTHOR_REPLY;
import static com.zzh.corelib.interfaces.FillCommentListener.COMMENT_AUTHOR;
import static com.zzh.corelib.interfaces.FillCommentListener.REPLY_AUTHOR;
import static com.zzh.corelib.interfaces.FillCommentListener.USER_USER;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/18
 * creat_time: 19:25
 * describe 用于包裹每行评论的容器
 **/

@SuppressWarnings("all")
public class CommentWithReplyLayout extends LinearLayout {

    private Context mContext;

    private final int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;
    private final int MATCH_PARENT = LayoutParams.MATCH_PARENT;
    private final int SPAN_INCLUSIVE_EXCLUSIVE = Spanned.SPAN_INCLUSIVE_EXCLUSIVE;

    private CommentLayoutConfig config = new CommentLayoutConfig();

    public CommentWithReplyLayout(Context context) {
        this(context, null);
    }

    public CommentWithReplyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentWithReplyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new LayoutParams(WRAP_CONTENT, WRAP_CONTENT));

        mContext = this.getContext();
        setOrientation(HORIZONTAL);


    }

    /**
     * 设置配置文件
     *
     * @param config
     */
    public CommentWithReplyLayout setConfig(CommentLayoutConfig config) {
        this.config = config;
        return this;
    }


    /**
     * 作者回复评论
     *
     * @param selfName 作者昵称
     * @param userName 被回复者昵称
     */
    public void authorReply(String selfName, String userName, String content) {
        this.fillContent(getNotNullString(selfName), getNotNullString(userName), content, AUTHOR_REPLY);
    }


    /**
     * 回复作者
     *
     * @param userName 回复者昵称
     * @param selfName 作者昵称
     */
    public void replyAuthor(String userName, String selfName, String content) {
        this.fillContent(getNotNullString(userName), getNotNullString(selfName), content, REPLY_AUTHOR);
    }

    /**
     * 评论者回复评论者
     *
     * @param user1Name 评论者昵称
     * @param user2Name 被评论者昵称
     */
    public void userReplyUser(String user1Name, String user2Name, String content) {
        this.fillContent(getNotNullString(user1Name), getNotNullString(user2Name), content, USER_USER);
    }

    /**
     * 评论
     *
     * @param userName 评论者昵称
     */
    public void commentAuthor(String userName, String content) {
        this.fillContent(getNotNullString(userName), getNotNullString(null), content, COMMENT_AUTHOR);
    }

    /**
     * 填充内容
     */
    private void fillContent(String fromUser, String toUser, String content, int type) {

        StringBuffer buffer = new StringBuffer();
        String reply = config.getReplyTips();

        //创建显示文本的TextView
        TextView richTextView = new TextView(mContext);
        LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        richTextView.setLayoutParams(params);
        richTextView.setTextColor(config.getTextColor());
        richTextView.setTextSize(config.getTextSize());

        int nickNameColor = config.getNickNameColor();
        int tipsColor = config.getReplySplitColor();

        SpannableString spannableString;

        //获取下标,拓展昵称标色
        int selfLength = fromUser.length() + 1;
        int startToUserLength = fromUser.length() + reply.length();
        int startToContentLength = fromUser.length() + reply.length() + toUser.length() + 1;

        //非回复 不加标色
        if (!"".equals(toUser) && type != COMMENT_AUTHOR) {

            buffer.append(fromUser)
                    .append(reply)
                    .append(toUser)
                    .append(": ")
                    .append(content);


            //解析表情并配置颜色
            spannableString = new SpannableString(buffer.toString());
            spannableString = EmojiUtils.parseEmoji(mContext, spannableString, buffer.toString());
            spannableString.setSpan(new ForegroundColorSpan(nickNameColor), 0, selfLength, SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(tipsColor), selfLength, selfLength + reply.length(), SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(nickNameColor), startToUserLength, startToContentLength, SPAN_INCLUSIVE_EXCLUSIVE);

        } else {

            buffer.append(fromUser)
                    .append(": ")
                    .append(content);

            //解析表情并配置颜色
            spannableString = new SpannableString(buffer.toString());
            spannableString = EmojiUtils.parseEmoji(mContext, spannableString, buffer.toString());
            spannableString.setSpan(new ForegroundColorSpan(nickNameColor), 0, selfLength, SPAN_INCLUSIVE_EXCLUSIVE);
        }

        richTextView.setText(spannableString);
        this.removeAllViews();
        this.addView(richTextView);

    }

    private String getNotNullString(String str) {
        return str + "";
    }

}
