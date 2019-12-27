package com.zzh.corelib.config;

import android.graphics.Color;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/19
 * creat_time: 19:38
 * describe 每条评论的 属性配置
 **/
public class CommentLayoutConfig {

    /**
     * 字体颜色 内容部分
     */
    private int textColor = Color.parseColor("#666666");

    /**
     * 字体大小
     */
    private int textSize = 14;

    /**
     * 标识
     */
    private String replyTips = " 回复 ";

    /**
     * 回复字体颜色
     */
    private int replySplitColor = Color.parseColor("#666666");

    /**
     * 昵称的颜色
     */
    private int nickNameColor = Color.parseColor("#81d4fa");


    public CommentLayoutConfig setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }


    public CommentLayoutConfig setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }


    public CommentLayoutConfig setReplyTips(String replyTips) {
        this.replyTips = replyTips;
        return this;
    }


    public CommentLayoutConfig setReplySplitColor(int replySplitColor) {
        this.replySplitColor = replySplitColor;
        return this;
    }


    public CommentLayoutConfig setNickNameColor(int nickNameColor) {
        this.nickNameColor = nickNameColor;
        return this;
    }


    public int getNickNameColor() {
        return nickNameColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getReplyTips() {
        return replyTips;
    }

    public int getReplySplitColor() {
        return replySplitColor;
    }
}
