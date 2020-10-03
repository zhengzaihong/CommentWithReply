package com.room.core.interfaces

import android.view.View
import com.room.core.config.MsgType


/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/9/30
 * create_time: 13:48
 * describe: 填充每行的数据监听
 * 实现该接口自定义 可实现每行展示不同的样式类型 完全交由外部控制
 **/
open interface OnPackingItemListener {

    /**
     * 当前数据的填充信息
     * @param fromUser 数据的发起者
     * @param toUser 数据的接受者
     * @param content 内容
     * @param 消息类型
     */
    fun packView(
        fromUser: String,
        toUser: String,
        content: String,
        type: MsgType
    ): View?


}