package com.room.core.view

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.room.core.config.CommentLayoutParams
import com.room.core.config.MsgType
import com.room.core.interfaces.FillCommentListener
import com.room.core.interfaces.OnPackingItemListener

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/18
 * creat_time: 19:25
 * describe 用于包裹每行评论的容器
 */
class CommentWithReplyLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    private val MATCH_PARENT = LayoutParams.MATCH_PARENT
    private val SPAN_INCLUSIVE_EXCLUSIVE = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    private var config: CommentLayoutParams? = null
    private var onPackingItemListener: OnPackingItemListener? = null


    init {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        orientation = HORIZONTAL
    }

    /**
     * 设置配置文件
     *
     * @param config
     */
    fun setConfigAndListener(
        config: CommentLayoutParams?,
        onPackingItemListener: OnPackingItemListener?
    ) {
        this.config = config
        this.onPackingItemListener = onPackingItemListener
    }

    /**
     * 作者回复评论
     *
     * @param selfName 作者昵称
     * @param userName 被回复者昵称
     */
    fun authorReply(
        selfName: String?,
        userName: String?,
        content: String
    ) {
        fillContent(
            notNullStr(selfName),
            notNullStr(userName),
            content,
            MsgType.AUTHOR_REPLY
        )
    }

    /**
     * 回复作者
     *
     * @param userName 回复者昵称
     * @param selfName 作者昵称
     */
    fun replyAuthor(userName: String, selfName: String, content: String) {
        fillContent(
            notNullStr(userName),
            notNullStr(selfName),
            content,
            MsgType.REPLY_AUTHOR
        )
    }

    /**
     * 评论者回复评论者
     *
     * @param user1Name 评论者昵称
     * @param user2Name 被评论者昵称
     */
    fun userReplyUser(user1Name: String, user2Name: String, content: String) {
        fillContent(
            notNullStr(user1Name),
            notNullStr(user2Name),
            content,
            MsgType.USER_USER
        )
    }

    /**
     * 评论
     *
     * @param userName 评论者昵称
     */
    fun commentAuthor(userName: String, content: String) {
        fillContent(
            notNullStr(userName),
            notNullStr(null),
            content,
            MsgType.COMMENT_AUTHOR
        )
    }

    /**
     * 填充内容
     */
    private fun fillContent(
        fromUser: String,
        toUser: String,
        content: String,
        type: MsgType
    ) {

        var itmeView = onPackingItemListener?.packView(fromUser, toUser, content, type)
        itmeView?.let {
            removeAllViews()
            this.addView(it)
        }

    }

    private fun notNullStr(str: String?) = "$str"



/**
 *    如果只是单独使用该无线列表控件则可以像如下添加每行数据
 *    具体信息可参看完整demo
 *
    val buffer = StringBuffer()
    val reply = config!!.replyTips

    //创建显示文本的TextView
    val richTextView = TextView(mContext)
    val params =
        LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    richTextView.layoutParams = params
    richTextView.setTextColor(config!!.textColor)
    richTextView.textSize = config!!.textSize.toFloat()
    val nickNameColor = config!!.nickNameColor
    val tipsColor = config!!.replySplitColor
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
            EmojiHelper.getInstance().parseEmoji(mContext, spannableString, buffer.toString())
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
            EmojiHelper.getInstance().parseEmoji(mContext, spannableString, buffer.toString())
        spannableString.setSpan(
            ForegroundColorSpan(nickNameColor),
            0,
            selfLength,
            SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
    richTextView.text = spannableString

    viewGroup.addView(richTextView)

 */
}