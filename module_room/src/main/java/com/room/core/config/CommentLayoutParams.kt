package com.room.core.config

import android.graphics.Color

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/19
 * create_time: 19:38
 * describe 每条评论的 属性配置
 */
class CommentLayoutParams(

    /**
     * 字体颜色 内容部分
     */
    var textColor: Int = Color.parseColor("#666666"),

    /**
     * 字体大小
     */
    var textSize: Int = 14,

    /**
     * 标识
     */
    var replyTips: String = " 回复 ",

    /**
     * 回复字体颜色
     */
    var replySplitColor: Int = Color.parseColor("#666666"),

    /**
     * 昵称的颜色
     */
    var nickNameColor: Int = Color.parseColor("#81d4fa")
)