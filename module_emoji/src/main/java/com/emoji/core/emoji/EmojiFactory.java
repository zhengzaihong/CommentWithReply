package com.emoji.core.emoji;


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

/**
 * create_user: zhengzaihong
 * email:1096877329@qq.com
 * create_date: 2020/1/1 0001
 * create_time: 16:46
 * describe: 表情工厂
 **/

@SuppressWarnings("all")
public class EmojiFactory {


    private static EmojiFactory factory;
    /**
     * 记录所有的表情包
     * Map<标记包ID, Map<标记名称, 表情文件id>>
     */
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack = new LinkedHashMap<>();

    /**
     * 将所有表情名称和ID提取出来
     */
    private List<String> emojiNames = new ArrayList<>();
    /**
     * 将emojiPack 的表情全部提取出来
     */
    private Map<String, Integer> mapPack = new HashMap<>();


    public static EmojiFactory getInstance() {
        if (null == factory) {
            synchronized (EmojiFactory.class) {
                if (null == factory) {
                    factory = new EmojiFactory();
                }
            }
        }
        return factory;

    }

    /**
     * 设置表情包
     *
     * @param pack
     */
    public void setPack(final LinkedHashMap<String, LinkedHashMap<String, Integer>> pack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                emojiPack.putAll(pack);
                Set<Map.Entry<String, LinkedHashMap<String, Integer>>> entrySet = emojiPack.entrySet();
                Iterator<Map.Entry<String, LinkedHashMap<String, Integer>>> iterator = entrySet.iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, LinkedHashMap<String, Integer>> mapEntry = iterator.next();
                    LinkedHashMap<String, Integer> linkedHashMap = mapEntry.getValue();
                    Set<Map.Entry<String, Integer>> set = linkedHashMap.entrySet();
                    Iterator<Map.Entry<String, Integer>> entryIterator = set.iterator();
                    while (entryIterator.hasNext()) {
                        emojiNames.add(entryIterator.next().getKey());
                    }
                    // 提取表情包
                    mapPack.putAll(linkedHashMap);
                }
            }
        }).start();
    }

    /**
     * 添加表情包
     *
     * @param packKey 表情包的唯一标识
     * @param pack    表情包
     */
    public void addPack(final String packKey, final LinkedHashMap<String, Integer> pack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                emojiPack.put(packKey, pack);
                Set<Map.Entry<String, Integer>> entrySet = pack.entrySet();
                Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    emojiNames.add(iterator.next().getKey());
                }
                mapPack.putAll(pack);
            }
        }).start();

    }

    /**
     * 删除一个表情包
     *
     * @param packKey 表情包的唯一标识
     */
    public void removePack(final String packKey) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                emojiPack.remove(packKey);

                LinkedHashMap<String, Integer> linkedHashMap = findPack(packKey);
                if (null != linkedHashMap) {
                    List<String> names = new ArrayList<>();
                    Set<Map.Entry<String, Integer>> set = linkedHashMap.entrySet();
                    Iterator<Map.Entry<String, Integer>> entryIterator = set.iterator();
                    while (entryIterator.hasNext()) {
                        String key = entryIterator.next().getKey();
                        names.add(key);
                        mapPack.remove(key);
                    }
                    emojiNames.removeAll(names);
                }
            }
        }).start();
    }

    /**
     * 删除全部表情
     */

    public void removeAll() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                emojiPack.clear();
                emojiNames.clear();
                mapPack.clear();
            }
        }).start();
    }

    /**
     * 查找一个表情包
     *
     * @param key 表情包的key
     * @return 如果有则返回该表情包，否则null
     */

    public LinkedHashMap<String, Integer> findPack(String packKey) {
        return emojiPack.get(packKey);
    }

    /**
     * 修改某个表情包
     *
     * @param key  通过该key 值查找修改
     * @param pack 表情包
     * @return
     */
    public boolean updatePack(String packKey, LinkedHashMap<String, Integer> pack) {

        emojiPack.put(packKey, pack);
        LinkedHashMap<String, Integer> linkedHashMap = findPack(packKey);

        if (null != linkedHashMap) {
            List<String> names = new ArrayList<>();
            Set<Map.Entry<String, Integer>> set = linkedHashMap.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().getKey();
                names.add(key);
                mapPack.remove(key);
            }
            emojiNames.removeAll(names);
        }

        if (null != pack) {
            List<String> names = new ArrayList<>();
            Set<Map.Entry<String, Integer>> set = pack.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
            while (iterator.hasNext()) {
                names.add(iterator.next().getKey());
            }
            mapPack.putAll(linkedHashMap);
            emojiNames.addAll(names);
        }
        return true;
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


    public LinkedHashMap<String, LinkedHashMap<String, Integer>> getEmojiPack() {
        return emojiPack;
    }

    public List<String> getEmojiNames() {
        return emojiNames;
    }

    public Map<String, Integer> getMapPack() {
        return mapPack;
    }
}
