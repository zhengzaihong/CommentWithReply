package com.zzh.corelib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("all")
public class EmojiUtils {

    /**
     * 记录所有的表情包
     * Map<标记包ID, Map<标记名称, 表情文件id>>
     */
    private static LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = new LinkedHashMap<>();

    /**
     * 将所有表情名称和ID提取出来
     */
    public static List<String> allEmojiName = new ArrayList<>();
    /**
     * 将emojiPack 的表情全部提取出来
     */

    public static Map<String, Integer> allEmojis = new HashMap<>();

    public static void appendEmoji(LinkedHashMap<String, LinkedHashMap<String, Integer>> pack) {

        emojiPack.putAll(pack);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<Map.Entry<String, LinkedHashMap<String, Integer>>> entrySet = emojiPack.entrySet();
                Iterator<Map.Entry<String, LinkedHashMap<String, Integer>>> iterator = entrySet.iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, LinkedHashMap<String, Integer>> mapEntry = iterator.next();
                    LinkedHashMap<String, Integer> linkedHashMap = mapEntry.getValue();
                    Set<Map.Entry<String, Integer>> set = linkedHashMap.entrySet();
                    Iterator<Map.Entry<String, Integer>> entryIterator = set.iterator();
                    while (entryIterator.hasNext()) {
                        allEmojiName.add(entryIterator.next().getKey());
                    }
                    // 提取表情包
                    allEmojis.putAll(linkedHashMap);
                }
            }
        }).start();

    }

    public static void clearEmoji(Map<String, Map<String, Integer>> emojiPack) {
        emojiPack.clear();
    }

    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> getEmojiData() {
        return emojiPack;

    }


    /**
     * 解析单个表情包
     *
     * @param context
     * @param content
     * @return
     */
    public static SpannableString parseEmoji(Context context, SpannableString spannable,String content) {
        //通过标识取出单个表情包
        if (null == allEmojis) {
            Log.e("<-----EmojiUtils---->", "not found this emoji");
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
            Integer resId = allEmojis.get(regEmoJi);
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
    public static SpannableString parseEmojiType(Context context, String type, String content) {
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
    private static SpannableString parse(Context context, String type, String content) {

        //通过标识取出单个表情包
        Map<String, Integer> emojiMap = emojiPack.get(type);
        if (null == emojiMap) {
            Log.e("<-----EmojiUtils---->", "not found this type(" + type + ") emojiPack");
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
