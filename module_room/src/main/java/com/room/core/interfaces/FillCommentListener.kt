package com.room.core.interfaces

import com.room.core.config.MsgType
import com.room.core.view.CommentWithReplyLayout

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/18
 * create_time: 20:08
 * describe 填充评论的监听
 */
interface FillCommentListener<T> {


    /**
     * 返回 监听类型，上面面的标识
     * @param data
     * @return
     */
    fun commentType(data: T): MsgType

    /**
     * 作者回复的监听
     * @param item 回复的该条信息数据
     * @param viewLayout 该条的布局
     */
    fun authorReply(item: T, viewLayout: CommentWithReplyLayout)

    /**
     * 回复作者的监听
     * @param item 回复的该条信息数据
     * @param viewLayout 该条的布局
     */
    fun replyAuthor(item: T, viewLayout: CommentWithReplyLayout)

    /**
     * 评论作者的监听
     * @param item 指评论的那条动态
     * @param viewLayout 布局
     */
    fun commentAuthor(item: T, viewLayout: CommentWithReplyLayout)

    /**
     * 评论者和评论者之间的监听
     * @param item 被评论的那条数据
     * @param viewLayout 布局
     */
    fun userReplyUser(item: T, viewLayout: CommentWithReplyLayout)


}