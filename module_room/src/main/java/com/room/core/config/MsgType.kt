package com.room.core.config

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/9/30
 * create_time: 14:02
 * describe: 每行数据的类型
 **/
enum class MsgType {


    /**
     * 作者回复的标识
     */
    AUTHOR_REPLY,

    /**
     * 回复作者的标识
     */
    REPLY_AUTHOR,

    /**
     * 评论作者的标识
     */
    COMMENT_AUTHOR,

    /**
     * 评论者回复评论者标识
     */
    USER_USER,


    /**
     * 无评论
     */
    EMPTY,


    /**
     * 其他
     */
    OTHER;


}