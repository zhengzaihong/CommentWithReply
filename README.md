# CommentWithReply
高仿QQ 新浪空间控件

##打造类似QQ 微信空间无限动态列表层级控件


依赖地址：

 implementation 'com.zzh:roomView:0.1.0'

控件使用：

1.布局中使用 CommentWithReplyContainer容器,该控件往往需要结合ListView或者RecyleView一起使用

    <com.room.core.view.CommentWithReplyContainer
        android:layout_marginBottom="@dimen/len_10"
        android:layout_marginTop="@dimen/len_5"
        android:layout_marginLeft="@dimen/len_50"
        android:id="@+id/commentContainer"
        android:orientation="vertical"
        app:nickNameColor="@color/light_blue_200"
        app:replySplitColor="#666666"
        app:textColor="#e0a0d0"
        app:textSize="@dimen/font_14"
        app:replyTips="@string/comment_reply"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.room.core.view.CommentWithReplyContainer>


 2.在列表中即ListView或者RecyleView的adapter中实现 FillCommentListener 该接口进行关系数据填充，且务必实现该接口。

 3.动态创建每条动态View，通过CommentWithReplyContainer 对象打包每条动态View到该容器中


        container.setOnPackingItemListener(object : OnPackingItemListener {
            override fun packView(
                fromUser: String,
                toUser: String,
                content: String,
                type: MsgType
            ): View? {
               //这里是提供的默认方式 你完全可通过xml布局方式实现
                return container.createDefaultView(
                    fromUser,
                    toUser,
                    content,
                    type
                )
            }
           })
             //点击每一条动态的监听
            .setCommentItemClickListener(object : CommentItemClickListener {
                override fun onClick(itemPosition: Int, commentIndex: Int, data: Any) {
                }
            })
            .setFillCommentListener(this)
            .setData(position, "数据集合")
