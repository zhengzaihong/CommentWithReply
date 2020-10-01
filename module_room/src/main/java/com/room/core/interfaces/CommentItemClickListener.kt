package com.room.core.interfaces

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/19
 * create_time: 11:07
 * describe 单击每条评论的事件监听
 */
open interface CommentItemClickListener {
    /**
     * 每个评论的 点击事件
     * @param itemPosition 动态的的 index
     * @param commentIndex 被评论的 评论索引
     * @param data 该条评论数据
     */
    open fun onClick(itemPosition: Int, commentIndex: Int, data: Any)
}