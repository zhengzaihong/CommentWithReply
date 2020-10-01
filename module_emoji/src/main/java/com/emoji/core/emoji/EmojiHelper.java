package com.emoji.core.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("all")
public class EmojiHelper {

    private static EmojiHelper emojiHelper;
    /**
     * 记录所有的表情包
     * Map<标记包ID, Map<标记名称, 表情文件id>>
     */
    protected LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = new LinkedHashMap<>();

    /**
     * 将所有表情名称和ID提取出来
     */
    protected List<String> emojiNames = new ArrayList<>();
    /**
     * 将emojiPack 的表情全部提取出来
     */
    protected Map<String, Integer> mapPack = new HashMap<>();

    /**
     * 私有化
     */
    private EmojiHelper() {

    }
    public static EmojiHelper getInstance() {
        if (null == emojiHelper) {
            synchronized (EmojiHelper.class) {
                if (null == emojiHelper) {
                    emojiHelper = new EmojiHelper();
                }
            }
        }
        return emojiHelper;
    }

    /**
     * 解析单个表情包
     *
     * @param context
     * @param content
     * @return
     */
    public SpannableString parseEmoji(Context context, SpannableString spannable, String content) {
        //通过标识取出单个表情包
        if (null == mapPack) {
            Log.e("<-----EmojiHelper---->", "not found this emoji");
            return null;
        }
        String reg = "\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            //获取匹配到的emoji字符串
            String regEmoJi = matcher.group();
            //匹配到字符串的开始位置
            int start = matcher.start();
            //匹配到字符串的结束位置
            int end = matcher.end();
            //通过emoji名获取对应的表情id
            Integer resId = mapPack.get(regEmoJi);
            if (resId != null) {
                Drawable drawable = context.getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable, content);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }

    /**
     * 根据有标识的类型解析EmoJi表情
     *
     * @param context 上下文
     * @param type    表情类型
     * @param content 文本
     * @return
     */
    public SpannableString parseEmojiType(Context context, String type, String content) {
        return parse(context, type, content);
    }


    /**
     * 根据不同的表情包解析表情
     *
     * @param context 上下文
     * @param type    表情类型
     * @param content 文本
     * @return
     */
    private SpannableString parse(Context context, String type, String content) {

        //通过标识取出单个表情包
        Map<String, Integer> emojiMap = emojiPack.get(type);
        if (null == emojiMap) {
            Log.e("<-----EmojiHelper---->", "not found this type(" + type + ") emojiPack");
            return null;
        }
        //校验表情正则
        SpannableString spannable = new SpannableString(content);
        String reg = "\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            //获取匹配到的emoji字符串
            String regEmoJi = matcher.group();
            //匹配到字符串的开始位置
            int start = matcher.start();
            //匹配到字符串的结束位置
            int end = matcher.end();
            //通过emoji名获取对应的表情id
            Integer resId = emojiMap.get(regEmoJi);
            if (resId != null) {
                Drawable drawable = context.getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable, content);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }


}
