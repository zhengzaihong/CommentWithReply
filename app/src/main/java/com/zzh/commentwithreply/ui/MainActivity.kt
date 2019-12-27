package com.zzh.commentwithreply.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dz.ninegridimages.view.NineGridView
import com.dz.utlis.ToastTool
import com.google.gson.Gson
import com.zzh.commentwithreply.R
import com.zzh.commentwithreply.bean.ActionBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean
import com.zzh.commentwithreply.bean.ActionBean.DataBean.ReplyListBean
import com.zzh.commentwithreply.dialog.InputInfoDialog
import com.zzh.commentwithreply.utils.Constant
import com.zzh.commentwithreply.utils.DataHelper
import com.zzh.commentwithreply.utils.DataHelper.urls
import com.zzh.commentwithreply.utils.EmojiTools
import com.zzh.corelib.interfaces.EmojiViewListener
import com.zzh.corelib.interfaces.FillCommentListener
import com.zzh.corelib.interfaces.FillCommentListener.*
import com.zzh.corelib.ui.UiHelper
import com.zzh.corelib.utils.EmotionInputDetector
import com.zzh.corelib.view.CommentWithReplyContainer
import com.zzh.corelib.view.CommentWithReplyLayout
import dz.solc.viewtool.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_comment_view.*
import java.util.*


class MainActivity : AppCompatActivity(), EmojiViewListener {
    override fun getInputEditView(): EditText {
        return et_input_container
    }

    private var actionAdapter: ActionAdapter? = null

    private var mDetector: EmotionInputDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionAdapter = ActionAdapter(this)
        llContent.adapter = actionAdapter

        val personInfoBean = Gson().fromJson(Constant.testJson, ActionBean::class.java)

        var dataList = arrayListOf<DataBean>()

        dataList.addAll(personInfoBean.data)

        dataList.forEach {
            val count = Random().nextInt(5)
            val data = ArrayList<String>()
            data.addAll(urls.subList(0, count))
            it.images = data
        }

        actionAdapter?.setNewData(dataList)


        EmojiTools.initEmoji()

        UiHelper.startEmojiFragment(this, R.id.contanierFrameLayout, null)


        mDetector = EmotionInputDetector.with(this)
            .bindSendButton(btn_send)
            .bindToEditText(et_input_container)
            .setEmotionView(ll_face_container)
            .bindToContent(llContent)
            .bindToEmotionButton(cb_smile)


    }


    var index = 0
    inner class ActionAdapter(context: Context) :
        CommonAdapter<DataBean>(context, R.layout.item_layout_comment) {

        override fun convert(holder: ViewHolder, position: Int, entity: DataBean) {

            var container = holder.getView<CommentWithReplyContainer>(R.id.commentContainer)

            btn_send.setOnClickListener {
                var data = DataHelper.insetComment(
                    entity.replyList,
                    et_input_container.text.toString()
                )

                getDatas()[position].replyList = data
                container.setData(index, getDatas()[position].replyList)

                ToastTool.showContent(et_input_container.text.toString())
                et_input_container.setText("")

            }

            holder.getView<ImageView>(R.id.ivComment).setOnClickListener {
                index = position
                rlComment.visibility = View.VISIBLE
            }

            holder.setText(R.id.tvContent, entity.content)
                .setText(R.id.tvNick, entity.nickName)
                .setText(R.id.tvTime, entity.createDate)

            Glide.with(mContext).load(R.drawable.user_other)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getView(R.id.rivHead))


            val builder = NineGridView.NineGridViewBuilder<String>(mContext)
                .setNineGridViewConfigure(DataHelper.configure)
                .setImageInfo(entity.images)
            try {
                holder.getView<NineGridView<String>>(R.id.nineGrdiView)
                    .setNineAdapter(builder.buildAdpter())
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }


            container.setCommentItemClickListener { itemPosition, index, data ->

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
                        }
                    }
                    .showDialog()
            }


            // 这里是填充每条评论的 回调，触发频率非常高，不要再这里做任何耗时操作。
            container.setFillCommentListener(object : FillCommentListener<ReplyListBean> {

                override fun commentType(bean: ReplyListBean): Int {
                    return when {
                        bean.status == "1" -> COMMENT_AUTHOR
                        bean.status == "2" -> AUTHOR_REPLY
                        bean.status == "3" -> USER_USER
                        bean.status == "4" -> REPLY_AUTHOR
                        else -> 0
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
            })

            container.setData(position, entity.replyList)

        }
    }

}
