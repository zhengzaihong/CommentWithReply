
高仿QQ 新浪空间控件,完美解决开发中可能会遇到的动态列表嵌套带来的卡顿性能等问题


<img src="https://github.com/zhengzaihong/CommentWithReply/blob/master/images/pre.gif" width="300" height="500" alt="note"/>

上图gif没加载完成看着可能会卡顿，欢迎下载查看
[demo下载](https://github.com/zhengzaihong/CommentWithReply/blob/master/app-debug.apk)

依赖地址：

    //容器组件

    implementation 'com.zzh:room:0.1.0'

    //表情Lib，默认提供了微信和QQ基础表情包,可自由添加

    implementation 'com.zzh:emoji:0.1.0'

大致使用：

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

 4.如果需要表情面板请先添加上面表情包依赖库,并提供Fragment的一个容器FrameLayout布局，且必须实现 PanelViewListener接口中所需要的View



          EmotionInputDetector
            .with(this) //传入上下文
            .setPanelViewListener(this) //设置绑定各种View的监听回调
            .setPannelFragment(R.id.contanierFrameLayout, "0") //设置表情面板View，内部采用Fragment实现，因此这里传入容器ID 即可
            .setOutTouchOffSort(true) //是否开启非键盘区域触摸关闭键盘
            .setSoftInputlListener { //添加键盘关闭的回调信息
                //处理关闭软盘后相关逻辑
            }
            .create()