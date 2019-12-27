package com.zzh.commentwithreply.utils;


import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dz.ninegridimages.config.NineGridViewConfigure;
import com.dz.ninegridimages.interfaces.ImageLoader;
import com.dz.ninegridimages.interfaces.PreImageOnLongClickListener;
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
    public static NineGridViewConfigure configure = new NineGridViewConfigure();

    public static List<ReplyListBean> insetReply(List<ReplyListBean> sources, int index, String content) {

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

        urls.add("http://img3.imgtn.bdimg.com/it/u=2606296522,109065689&fm=26&gp=0.jpg");
        urls.add("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=98b063ef9f45d688bc02b4a494c37dab/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg");
        urls.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4e7e3a7a8d0a19d8d403820503f882c9/34fae6cd7b899e518d7259df4fa7d933c9950d78.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=1848903733,421507316&fm=26&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=3191362215,2691745071&fm=26&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=3575515023,3250489571&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2810361144,3329888699&fm=26&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3853562523,3017244426&fm=26&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2528583926,2971147999&fm=26&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=3796196028,3741032274&fm=26&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=1464879870,3180876389&fm=26&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=471176057,2814422093&fm=26&gp=0.jpg");

        urls.add("http://img2.imgtn.bdimg.com/it/u=3195699516,585177818&fm=26&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=1324773888,2229494300&fm=200&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=2072403978,677122262&fm=26&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=4104030800,1112978188&fm=26&gp=0.jpg");
    }

    public static void initConfig(Application application) {

        configure.setSingleImageSize(250)//设置单张图片固定宽高
                .setSingleFixed(false)//设置单张图片固定宽高
                .setRectAdius(10)//设置宫格视图图片圆角度数
                .setColumnNum(3)//设置宫格视图列数
                .setMaxImageSize(9)//设置最大显示多少张
                .setGridSpacing(10)//设置宫格视图的间距
                .setMode(NineGridViewConfigure.MODE_FILL) //设置图片布局模式
                .setSingleImageRatio(1.0f)//设置单张图片的缩放比例
                .setEnablePre(true)//是否开启预览
                .setMoreTextColor(application.getResources().getColor(R.color.amber_200))//设置超过最大张数显示的文本颜色
                .setMoreTextSize(40)//设置超过最大张数显示的字体大小
                .setPreBgColor(application.getResources().getColor(R.color.amber_200)) //设置预览时的背景
                .setPreTipColor(application.getResources().getColor(R.color.red)) //设置指示器文本颜色

//               指示器新加功能支持 xml 和 代码配置两种方式

                .setEnableIndicatorDot(true) //设置开启 Indicator
                .setIndicatorMargin(10) //设置指示器间距
                //设置自定义指示器 xml 设置会覆盖 代码设置的方式
                //.setIndicator(new int[]{R.drawable.nine_view_indicator_select_dot, R.drawable.nine_view_indicator_un_select_dot}) //优先级最高，其次是代码配置
                .setIndicatorBgPadding(5) //设置Indicator小圆点 背景内距
                .setIndicatorRadiusType(NineGridViewConfigure.RadiusType.ALL_RADIUS) //边距全圆角
                .setIndicatorSize(5) //设置 小圆点指示器大小
                .setIndicatorStrokeColor(application.getResources().getColor(R.color.amber_200)) // 设置Indicator小圆点 边框颜色
                .setIndicatorStrokeWidth(0)//设置Indicator小圆点边框宽度
                .setSelectIndicatorBgColor(application.getResources().getColor(R.color.light_blue_200)) // 设置Indicator小圆点选中时的颜色
                .setUnSelectIndicatorBgColor(application.getResources().getColor(R.color.gray_cc))//设置Indicator小圆点 未选中时的颜色
                //  设置大图预览的长按监听事件
                .setOnPreLongClickListener(new PreImageOnLongClickListener() {
                    @Override
                    public void onImageLongClick(ImageView imageView, int position, Object obejct) {
                        ToastTool.get().show("长按了position=" + position);
                    }
                })
                //设置图片加载器（必须设置） 上面的都是非必须
                .setImageLoader(new ImageLoader() {
                    //九宫格图加载
                    @Override
                    public <T> void displayImage(Context context, ImageView imageView, T object) {
                        Glide.with(context).load(object.toString())
                                .fitCenter()
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.drawable.ic_default_color)
//                                .override(100, 100)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    }

                    //预览大图加载 可以在这里做加载动画等
                    @Override
                    public <T> void loadPreImage(Context context, ImageView imageView, T object) {

                        Glide.with(context).load(object.toString())
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.drawable.ic_default_color)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                    }

                });

    }


}
