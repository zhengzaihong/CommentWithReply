package com.zzh.corelib.interfaces;

import com.zzh.corelib.view.CommentWithReplyLayout;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/18
 * creat_time: 20:08
 * describe 填充评论的监听
 **/
public interface FillCommentListener<T> {

    /**
     * 作者回复的标识
     */
    int AUTHOR_REPLY = 10000;

    /**
     * 回复作者的标识
     */
    int REPLY_AUTHOR = 10001;

    /**
     * 评论作者的标识
     */
    int COMMENT_AUTHOR = 10002;

    /**
     * 评论者回复评论者标识
     */
    int USER_USER = 10003;


    /**
     * 返回 监听类型，上面面的标识
     * @param data
     * @return
     */
    int commentType(T data);

    /**
     * 作者回复的监听
     * @param item 回复的该条信息数据
     * @param viewLayout 该条的布局
     */
    void authorReply(T item, CommentWithReplyLayout viewLayout);

    /**
     * 回复作者的监听
     * @param item 回复的该条信息数据
     * @param viewLayout 该条的布局
     */
    void replyAuthor(T item, CommentWithReplyLayout viewLayout);

    /**
     * 评论作者的监听
     * @param item 指评论的那条动态
     * @param viewLayout 布局
     */
    void commentAuthor(T item, CommentWithReplyLayout viewLayout);

    /**
     * 评论者和评论者之间的监听
     * @param item 被评论的那条数据
     * @param viewLayout 布局
     */
    void userReplyUser(T item, CommentWithReplyLayout viewLayout);


}
