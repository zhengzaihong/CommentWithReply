package com.room.core.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.room.core.R
import com.room.core.config.CommentLayoutParams
import com.room.core.config.MsgType
import com.room.core.interfaces.CommentItemClickListener
import com.room.core.interfaces.FillCommentListener
import com.room.core.interfaces.OnPackingItemListener
import com.room.core.utils.Collections
import com.room.core.utils.Utils

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/18
 * create_time: 19:57
 * describe 评论列表的容器
 */
open class CommentWithReplyContainer<T, E> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val TAG = CommentWithReplyContainer::class.java.simpleName

    private val mContext = context
    private val WRAP_CONTENT = LayoutParams.WRAP_CONTENT
    private val MATCH_PARENT = LayoutParams.MATCH_PARENT
    private var defaultTextColor = Color.parseColor("#666666")

    val commentLayoutParams = CommentLayoutParams()
    private var commentListener: FillCommentListener<E>? = null
    private var commentItemClickListener: CommentItemClickListener? = null
    private var onPackingItemListener: OnPackingItemListener? = null

    init {
        layoutParams = LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        orientation = VERTICAL
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.CommentStyle)

        commentLayoutParams.apply {
            this.textColor =
                ta.getColor(R.styleable.CommentStyle_textColor, defaultTextColor)
            this.nickNameColor =
                ta.getColor(R.styleable.CommentStyle_nickNameColor, defaultTextColor)
            this.replySplitColor =
                ta.getColor(R.styleable.CommentStyle_replySplitColor, defaultTextColor)
            this.textSize = Utils.px2sp(
                mContext,
                ta.getDimensionPixelSize(R.styleable.CommentStyle_textSize, 14).toFloat()
            ).toInt()
            this.replyTips = " ${(ta.getString(R.styleable.CommentStyle_replyTips)
                ?: commentLayoutParams.replyTips).trim()} "


        }
        ta.recycle()
    }

    /**
     * 填充数据
     *
     * @param itemPosition 该条动态的下标
     * @param datas        所有的动态数据源
     */
    fun setData(itemPosition: Int, datas: List<E>?) {
        removeAllViews()
        requireNotNull(commentListener) { "请先设置 FillCommentListener 监听后再添加数据" }
        requireNotNull(onPackingItemListener) { "请先设置 OnPackingItemListener 监听后再添加数据" }
        if (datas.isNullOrEmpty()) {
            return
        }
        var tempData = datas?.reversed()
        for (i in tempData.indices) {

            val viewLine = CommentWithReplyLayout(mContext)
            viewLine.setConfigAndListener(commentLayoutParams, onPackingItemListener)
            val obj = tempData[i]

            //匹配消息类型
            commentListener?.apply {
                when (this.commentType(obj)) {
                    MsgType.AUTHOR_REPLY -> {
                        authorReply(obj, viewLine)
                    }
                    MsgType.REPLY_AUTHOR -> {
                        replyAuthor(obj, viewLine)
                    }
                    MsgType.COMMENT_AUTHOR -> {
                        commentAuthor(obj, viewLine)
                    }
                    MsgType.USER_USER -> {
                        userReplyUser(obj, viewLine)
                    }
                }
            }
            //添加 每行消息的点击事件
            viewLine.setOnClickListener {
                commentItemClickListener?.onClick(
                    itemPosition,
                    i,
                    obj as Any
                )
            }
            //填充到容器
            this.addView(viewLine)
        }
    }


    /**
     * 填充数据的监听,必须设置该监听，否则不填充数据
     * @param listener
     */
    fun setFillCommentListener(listener: FillCommentListener<E>): CommentWithReplyContainer<T, E> {
        this.commentListener = listener
        return this
    }

    /**
     * 设置评论的点击事件
     * @param itemListener
     */
    fun setCommentItemClickListener(itemListener: CommentItemClickListener): CommentWithReplyContainer<T, E> {
        this.commentItemClickListener = itemListener
        return this
    }

    /**
     * 设置评论的点击事件
     * @param onPackingItemListener
     */
    fun setOnPackingItemListener(onPackingItemListener: OnPackingItemListener): CommentWithReplyContainer<T, E> {
        this.onPackingItemListener = onPackingItemListener
        return this
    }
}
