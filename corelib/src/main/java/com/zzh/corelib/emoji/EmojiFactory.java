package com.zzh.corelib.emoji;

import com.zzh.corelib.interfaces.EmojiChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2020/1/1 0001
 * creat_time: 16:46
 * describe: 表情工厂
 **/

@SuppressWarnings("all")
public class EmojiFactory implements EmojiChangeListener {

    /**
     * 拿到表情工具类
     */
    private EmojiHelper helper = EmojiHelper.getInstance();

    /**
     * 记录所有的表情包
     * Map<标记包ID, Map<标记名称, 表情文件id>>
     */
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> emojiPack;

    /**
     * 将所有表情名称和ID提取出来
     */
    private List<String> emojiNames;
    /**
     * 将emojiPack 的表情全部提取出来
     */
    private Map<String, Integer> mapPack;


    private EmojiFactory() {
        this.emojiPack = helper.emojiPack;
        this.emojiNames = helper.emojiNames;
        this.mapPack = helper.mapPack;
    }

    public static synchronized EmojiFactory create() {
        return new EmojiFactory();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public LinkedHashMap<String, Integer> findPack(String packKey) {
        return emojiPack.get(packKey);
    }

    @Override
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
}
