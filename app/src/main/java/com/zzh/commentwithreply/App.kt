package com.zzh.commentwithreply

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dz.ninegridimages.config.NineGridViewConfigure
import com.dz.ninegridimages.interfaces.ImageLoader
import com.dz.utlis.JavaUtils
import com.dz.utlis.ScreenUtils
import com.dz.utlis.ToastTool
import com.dz.utlis.UiCompat
import com.zzh.commentwithreply.utils.EmojiTools


class App : Application() {

companion object{
    @JvmField
    open val configure = NineGridViewConfigure()
}


    override fun onCreate() {
        super.onCreate()

        JavaUtils.isdebug = true


        EmojiTools.initEmoji()
        // Toast 配置
        ToastTool.options()
            .setInterval(2000)
            .setRadius(ScreenUtils.dip2px(this, 30f).toInt())
            .setTextColor(UiCompat.getColor(resources, R.color.light_blue_200))
            .setBackGroundColor(Color.WHITE)
            .setTextSize(16)
            .setPadding(ScreenUtils.dip2px(this, 15f).toInt())
            .setLongTime(false)
            .setStrokeWidth(0)
            .setRadiusType(ToastTool.RadiusType.ALL_RADIUS)
            .setStrokeColor(Color.GREEN)
            .build(this)



        with(configure.buildBaseStyleParams()) {
            // 必须设置，否则不加载图片
            onNineGridImageListener = object : ImageLoader.OnNineGridImageListener {
                override fun <T : Any> displayImage(
                    context: Context,
                    imageView: ImageView,
                    obj: T
                ) {
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
                preTipTextSize = ScreenUtils.sp2px(this@App, 12f).toInt()
            }

        }
    }


}