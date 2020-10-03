package com.emoji.core.emoji

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.room.core.config.MsgType
import com.room.core.view.CommentWithReplyContainer

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/30
 * create_time: 14:21
 * describe 表情面板的一些常量和其他方法
 */
object PanelController {

    private const val WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT
    private const val MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT
    private const val SPAN_INCLUSIVE_EXCLUSIVE = Spanned.SPAN_INCLUSIVE_EXCLUSIVE

    /**
     * 表情的类型
     */
    const val EMOJITYPE = "EMOJITYPE"

    /**
     * 表情的开始标志位 如:[
     */
    @JvmField
    var EMOJISTARTTAG = "["
    /**
     * 表情的结束标志位 如:]
     */
    @JvmField
    var EMOJIENDTAG = "]"

    @JvmField
    var DELETKEY = "[删除]"

    /**
     * 创造每一行数据的View方法
     *
     * 这是默认提供的，你完全可以在外部通过布局或者code实现
     */

    fun <T, E> CommentWithReplyContainer<T, E>.createDefaultView(
        fromUser: String,
        toUser: String,
        content: String,
        type: MsgType
    ): View? {
        if (type == MsgType.EMPTY) {
            return null
        }
        var config = this.commentLayoutParams

        val buffer = StringBuffer()
        val reply = config?.replyTips

        //创建显示文本的TextView
        val richTextView = TextView(this.context)
        val params =
            ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        richTextView.layoutParams = params
        richTextView.setTextColor(config!!.textColor)
        richTextView.textSize = config?.textSize.toFloat()
        val nickNameColor = config?.nickNameColor
        val tipsColor = config?.replySplitColor
        var spannableString: SpannableString

        //获取下标,拓展昵称标色
        val selfLength = fromUser.length + 1
        val startToUserLength = fromUser.length + reply.length
        val startToContentLength = fromUser.length + reply.length + toUser.length + 1

        //非回复 不加标色
        if ("" != toUser && type != MsgType.COMMENT_AUTHOR) {
            buffer.append(fromUser)
                .append(reply)
                .append(toUser)
                .append(": ")
                .append(content)


            //解析表情并配置颜色
            spannableString = SpannableString(buffer.toString())
            spannableString =
                EmojiFactory.getInstance()
                    .parseEmoji(this.context, spannableString, buffer.toString())
            spannableString.setSpan(
                ForegroundColorSpan(nickNameColor),
                0,
                selfLength,
                SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(tipsColor),
                selfLength,
                selfLength + reply.length,
                SPAN_INCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(nickNameColor),
                startToUserLength,
                startToContentLength,
                SPAN_INCLUSIVE_EXCLUSIVE
            )
        } else {
            buffer.append(fromUser)
                .append(": ")
                .append(content)

            //解析表情并配置颜色
            spannableString = SpannableString(buffer.toString())
            spannableString =
                EmojiFactory.getInstance()
                    .parseEmoji(this.context, spannableString, buffer.toString())
            spannableString.setSpan(
                ForegroundColorSpan(nickNameColor),
                0,
                selfLength,
                SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        richTextView.text = spannableString
        return richTextView
    }
}