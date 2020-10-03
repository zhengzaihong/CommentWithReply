package com.zzh.commentwithreply.utils;


import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dz.ninegridimages.config.NineGridViewConfigure;
import com.dz.ninegridimages.interfaces.ImageLoader;
import com.dz.utlis.ToastTool;
import com.zzh.commentwithreply.R;

import java.util.ArrayList;
import java.util.List;

import static com.zzh.commentwithreply.bean.ActionBean.DataBean.ReplyListBean;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/20
 * creat_time: 10:32
 * describe 数据模拟处理
 **/
public class DataHelper {
    public static List<ReplyListBean> insetReply(List<ReplyListBean> sources, int index, String content) {

        if(null== sources){
            sources = new ArrayList<>();
        }
        ReplyListBean replyListBean = new ReplyListBean();
        replyListBean.setAuthorNickName("独自悲伤");
        replyListBean.setNickName("吴庚");
        replyListBean.setStatus("2");
        replyListBean.setAuthorReplyContent(content);
        sources.add(index + 1, replyListBean);

        return sources;
    }

    public static List<ReplyListBean> insetComment(List<ReplyListBean> sources, String content) {

        if (sources == null) {
            sources = new ArrayList<>();
        }
        ReplyListBean replyListBean = new ReplyListBean();
        replyListBean.setNickName("吴庚");
        replyListBean.setStatus("1");
        replyListBean.setContent(content);
        sources.add(replyListBean);

        return sources;
    }


    public static List<String> urls = new ArrayList<>();


    static {

        urls.add("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=98b063ef9f45d688bc02b4a494c37dab/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg");
        urls.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4e7e3a7a8d0a19d8d403820503f882c9/34fae6cd7b899e518d7259df4fa7d933c9950d78.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601638315571&di=fd786ceffe2ded5f4a4aba3381be5476&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F27%2F81%2F01200000194677136358818023076.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601638315584&di=577cf5be1a74fd68e7a9b9222994972c&imgtype=0&src=http%3A%2F%2Fa1.att.hudong.com%2F05%2F00%2F01300000194285122188000535877.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601638315584&di=cf9eaee0194433de7d074200d225f830&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F22%2F59%2F19300001325156131228593878903.jpg");

    }



}
