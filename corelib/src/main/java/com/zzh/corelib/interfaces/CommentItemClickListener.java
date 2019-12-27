package com.zzh.corelib.interfaces;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/19
 * creat_time: 11:07
 * describe 单击每条评论的事件监听
 **/
public interface CommentItemClickListener<T> {

    /**
     * 每个评论的 点击事件
     * @param itemPosition 动态的的 index
     * @param commentIndex 被评论的 评论索引
     * @param data 该条评论数据
     */
    void onClick(int itemPosition,int commentIndex,T data);
}
