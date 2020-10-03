package com.zzh.commentwithreply.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dz.ninegridimages.view.NineGridView
import com.dz.utlis.JavaUtils
import com.dz.utlis.KeyBoardUtils
import com.dz.utlis.ToastTool
import com.emoji.core.emoji.PanelController.createDefaultView
import com.emoji.core.interfaces.PanelViewListener
import com.emoji.core.interfaces.SoftInputlListener
import com.emoji.core.utils.EmotionInputDetector
import com.google.gson.Gson
import com.room.core.config.MsgType
import com.room.core.interfaces.CommentItemClickListener
import com.room.core.interfaces.FillCommentListener
import com.room.core.interfaces.OnPackingItemListener
import com.room.core.view.CommentWithReplyContainer
import com.room.core.view.CommentWithReplyLayout
import com.zzh.commentwithreply.App
import com.zzh.commentwithreply.R
import com.zzh.commentwithreply.bean.ActionBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean.ReplyListBean
import com.zzh.commentwithreply.dialog.InputInfoDialog
import com.zzh.commentwithreply.utils.Constant
import com.zzh.commentwithreply.utils.DataHelper
import com.zzh.commentwithreply.utils.DataHelper.urls
import dz.solc.viewtool.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_comment_view.*
import java.util.*


class MainActivity : AppCompatActivity(),
    PanelViewListener {

    override fun bindInputEditView(): EditText {
        return et_input_container
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

    @RequiresApi(Build.VERSION_CODES.M)
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



        eEmotionInputDetector = EmotionInputDetector
            .with(this) //传入上下文
            .setPanelViewListener(this) //设置绑定各种View的监听回调
            .setPannelFragment(R.id.contanierFrameLayout, "0") //设置表情面板View，内部采用Fragment实现，因此这里传入容器ID 即可
            .setOutTouchOffSort(true) //是否开启非键盘区域触摸关闭键盘
            .setSoftInputlListener { //添加键盘关闭的回调信息
                rlComment.visibility = View.GONE
            }
            .create()

        listViewContent.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(Math.abs(scrollY-oldScrollY)>300){
                rlComment.visibility = View.GONE
            }
        }


        btn_send.setOnClickListener {
            var data = DataHelper.insetComment(
                dataBean?.replyList,
                et_input_container.text.toString()
            )
            dataBean?.replyList = data
            et_input_container.setText("")
            rlComment.visibility = View.GONE
            KeyBoardUtils.hiddenKeyBoard(this)

        }

    }

    override fun onBackPressed() {
        if (!eEmotionInputDetector.interceptBackPress()) {
            super.onBackPressed()
        }
    }


    var index = 0
    var dataBean: DataBean? = null

    inner class ActionAdapter(context: Context) :
        CommonAdapter<DataBean>(context, R.layout.item_layout_comment),
        FillCommentListener<ReplyListBean> {



        //这里是根据消息状态转换关系 实际业务可能不是这样的，需要根据你项目的实际情况来进行关系转换
        override fun commentType(bean: ReplyListBean): MsgType {
            return when (bean.status) {
                "1" -> MsgType.COMMENT_AUTHOR
                "2" -> MsgType.AUTHOR_REPLY
                "3" -> MsgType.USER_USER
                "4" -> MsgType.REPLY_AUTHOR
                else -> MsgType.EMPTY
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
            holder.getView<NineGridView<String>>(R.id.nineGrdiView)
                .setData(entity.images, App.configure)

            var container =
                holder.getView<CommentWithReplyContainer<DataBean, ReplyListBean>>(R.id.commentContainer)

            holder.getView<ImageView>(R.id.ivComment).setOnClickListener {
                index = position
                dataBean = entity
                rlComment.visibility = View.VISIBLE
            }

            holder.setText(R.id.tvContent, entity.content)
                .setText(R.id.tvNick, entity.nickName)
                .setText(R.id.tvTime, entity.createDate)


            container.setOnPackingItemListener(object : OnPackingItemListener {
                override fun packView(
                    fromUser: String,
                    toUser: String,
                    content: String,
                    type: MsgType
                ): View? {
                    return container.createDefaultView(
                        fromUser,
                        toUser,
                        content,
                        type
                    )
                }

            })
                .setCommentItemClickListener(object : CommentItemClickListener {
                    override fun onClick(itemPosition: Int, commentIndex: Int, data: Any) {
                        //TODO  这里只是模拟，不惧有任何意义，真实需根据项目业务逻辑做相应处理
                        Log.e("----------", "这是第${itemPosition}说说的第${index}条评论")
                        var dialog = InputInfoDialog(mContext)
                        dialog.initTips("回复 第${index}用户")
                            .setOutTouchside(true)
                            .setCallBack { str ->
                                run {
                                    ToastTool.show("评论成功")
                                    var data = DataHelper.insetReply(entity.replyList, index, str)
                                    container.setData(position, data)
                                    KeyBoardUtils.hiddenKeyBoard(mContext)
                                }
                            }
                            .showDialog()
                    }

                })
                .setFillCommentListener(this)
                .setData(position, entity?.replyList)



        }
    }
}
