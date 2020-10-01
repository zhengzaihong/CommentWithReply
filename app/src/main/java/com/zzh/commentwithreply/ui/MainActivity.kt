package com.zzh.commentwithreply.ui

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dz.ninegridimages.config.NineGridViewConfigure
import com.dz.ninegridimages.config.NineGridViewConfigure.Companion.MODE_FILL
import com.dz.ninegridimages.interfaces.ImageLoader
import com.dz.ninegridimages.view.NineGridView
import com.dz.utlis.*
import com.emoji.core.interfaces.EmojiViewListener
import com.emoji.core.utils.EmotionInputDetector
import com.google.gson.Gson
import com.room.core.config.MsgType
import com.room.core.interfaces.CommentItemClickListener
import com.zzh.commentwithreply.R
import com.zzh.commentwithreply.bean.ActionBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean.ReplyListBean
import com.zzh.commentwithreply.dialog.InputInfoDialog
import com.zzh.commentwithreply.utils.Constant
import com.zzh.commentwithreply.utils.DataHelper
import com.zzh.commentwithreply.utils.DataHelper.urls
import com.zzh.commentwithreply.utils.EmojiTools
import com.room.core.interfaces.FillCommentListener
import com.room.core.interfaces.OnPackingItemListener
import com.room.core.view.CommentWithReplyContainer
import com.room.core.view.CommentWithReplyLayout
import dz.solc.viewtool.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), EmojiViewListener {

    override fun bindInputEditView(): EditText {
        return  et_input_container
    }

    override fun bindEmotionView(): View {
        return ll_face_container
    }

    override fun bindEmotionButton(): CheckBox {

        return cb_smile
    }

    override fun bindSendButton(): View {

        return btn_send
    }

    override fun bindContentView(): View {
       return listViewContent
    }



    private var actionAdapter: ActionAdapter? = null

    private lateinit var eEmotionInputDetector: EmotionInputDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionAdapter = ActionAdapter(this)
        listViewContent.adapter = actionAdapter

        val personInfoBean = Gson().fromJson(Constant.testJson, ActionBean::class.java)

        var dataList = arrayListOf<DataBean>()

        dataList.addAll(personInfoBean.data)

        dataList.forEach {
            val index = Random().nextInt(urls.size)
            val index1 = Random().nextInt(urls.size)
            val data = ArrayList<String>()
            data.add(urls[index])
            data.add(urls[index1])
            it.images = data
        }

        actionAdapter?.setNewData(dataList)


        EmojiTools.initEmoji()

        eEmotionInputDetector= EmotionInputDetector
            .with(this)
            .setEmojiViewListener(this)
            .setPannelFragment(R.id.contanierFrameLayout, "0")
            .create()


        listViewContent.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
//                if (rlComment.visibility == View.VISIBLE) {
//                    rlComment.visibility = View.GONE
//                }
            }
        })



        btn_send.setOnClickListener {
            var data = DataHelper.insetComment(
                dataBean?.replyList,
                et_input_container.text.toString()
            )
            dataBean?.replyList = data
            et_input_container.setText("")
//            rlComment.visibility = View.GONE
            KeyBoardUtils.hiddenKeyBoard(this)

        }

    }


    override fun onBackPressed() {

        if(!eEmotionInputDetector.interceptBackPress()){
            super.onBackPressed()
        }
    }


    var index = 0

    var dataBean:DataBean? = null

    inner class ActionAdapter(context: Context) : CommonAdapter<DataBean>(context, R.layout.item_layout_comment), FillCommentListener<ReplyListBean> {


        init {
            val configure = NineGridViewConfigure()
            with(configure.buildBaseStyleParams()) {
                // 必须设置，否则不加载图片
                onNineGridImageListener = object : ImageLoader.OnNineGridImageListener {
                    override fun <T : Any> displayImage(
                        context: Context,
                        imageView: ImageView,
                        obj: T
                    ) {

                        //真实开发中，如果你的列表显示 全是图片并多，
                        // 列表在滑动时 不要加载图片，等待停止滑动后做加载。
                        //且应要求后端 分多套分辨图返回
                        Glide.with(context).load(obj.toString())
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.drawable.ic_default_color)
                            .override(150, 150)
                            .into(imageView)

                    }
                }
            }

            //可选参数 配置
            with(configure.buildPreImageStyleParams()) {
                //加载图片的必须设置
                this.onPreImageListener = object : ImageLoader.OnPreImageListener {
                    override fun <E : Any?> loadPreImage(
                        context: Context,
                        imageView: ImageView,
                        obj: E,
                        index: Int
                    ) {
                        Glide.with(context).load(obj.toString())
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.drawable.ic_default_color)
                            .override(150, 150)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView)

                    }
                }
            }

            with(configure.buildIndicatorStyleParams()) {
                //文本显示
                this.buildStyleText().apply {
                    preTipColor = UiCompat.getColor(resources, R.color.red) //设置指示器文本颜色
                    preTipTextSize = ScreenUtils.sp2px(this@MainActivity, 12f).toInt()
                }

            }
        }


        override fun commentType(bean: ReplyListBean): MsgType {
            return when (bean.status) {
                "1" -> MsgType.COMMENT_AUTHOR
                "2" -> MsgType.AUTHOR_REPLY
                "3" -> MsgType.USER_USER
                "4" -> MsgType.REPLY_AUTHOR
                else -> MsgType.OTHER
            }
        }

        override fun authorReply(item: ReplyListBean, view: CommentWithReplyLayout) {
            view.authorReply(
                item.authorNickName,
                item.nickName,
                item.authorReplyContent
            )
        }

        override fun replyAuthor(item: ReplyListBean, view: CommentWithReplyLayout) {
            view.replyAuthor(item.nickName, item.authorNickName, item.authorReplyContent)
        }

        override fun userReplyUser(item: ReplyListBean, view: CommentWithReplyLayout) {
            view.userReplyUser(item.nickName, item.userNickName, item.userReplyContent)
        }

        override fun commentAuthor(item: ReplyListBean, view: CommentWithReplyLayout) {
            view.commentAuthor(item.nickName, item.content)
        }


        override fun convert(holder: ViewHolder, position: Int, entity: DataBean) {

            JavaUtils.outRedPrint("position: " + position + " 内容：" + "${if (entity.replyList == null) "null" else entity.replyList.toString()}")

            var container =
                holder.getView<CommentWithReplyContainer<DataBean, ReplyListBean>>(R.id.commentContainer)

            holder.getView<ImageView>(R.id.ivComment).setOnClickListener {
                index = position
                dataBean = entity
//                rlComment.visibility = View.VISIBLE

            }

            holder.setText(R.id.tvContent, entity.content)
                .setText(R.id.tvNick, entity.nickName)
                .setText(R.id.tvTime, entity.createDate)


            container
                .setOnPackingItemListener(object : OnPackingItemListener {
                    override fun packView(
                        fromUser: String,
                        toUser: String,
                        content: String,
                        type: MsgType
                    ): View {

                        return View(mContext)
                    }

                })
                .setCommentItemClickListener(object : CommentItemClickListener {
                    override fun onClick(itemPosition: Int, commentIndex: Int, data: Any) {
                        Log.e("----------", "这是第${itemPosition}说说的第${index}条评论")
                        var dialog = InputInfoDialog(mContext)
                        dialog.initTips("回复 第${index}用户")
                            .setOutTouchside(true)
                            .setCallBack { str ->
                                run {
                                    //TODO  这里只是模拟，不惧有任何意义，真实需根据项目业务逻辑做相应处理
                                    ToastTool.showContent("评论成功")
                                    var data = DataHelper.insetReply(entity.replyList, index, str)
                                    container.setData(position, data)

                                    KeyBoardUtils.hiddenKeyBoard(mContext)
                                }
                            }
                            .showDialog()
                    }


                })

            // 这里是填充每条评论的 回调，触发频率非常高，不要再这里做任何耗时操作。
            container.setFillCommentListener(this)
            container.setData(position, entity?.replyList)
            holder.getView<NineGridView<String>>(R.id.nineGrdiView).setData()


        }
    }
}
