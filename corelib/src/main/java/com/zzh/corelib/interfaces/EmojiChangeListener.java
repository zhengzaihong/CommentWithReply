package com.zzh.corelib.interfaces;

import java.util.LinkedHashMap;

/**
 * creat_user: zhengzaihong
 * email:1096877329@qq.com
 * creat_date: 2020/1/1 0001
 * creat_time: 15:47
 * describe: 提供外部 动态添加表情包的接口
 **/

public interface EmojiChangeListener {


    /**
     * 设置表情包
     * @param pack
     */
    void setPack(LinkedHashMap<String, LinkedHashMap<String, Integer>> pack);

    /**
     * 添加表情包
     *
     * @param packKey 表情包的唯一标识
     * @param pack    表情包
     */
    void addPack(String packKey, LinkedHashMap<String, Integer> pack);


    /**
     * 删除一个表情包
     *
     * @param packKey 表情包的唯一标识
     */
    void removePack(String packKey);


    /**
     * 删除全部表情
     */
    void removeAll();


    /**
     * 查找一个表情包
     *
     * @param key 表情包的key
     * @return 如果有则返回该表情包，否则null
     */
    LinkedHashMap<String, Integer> findPack(String key);


    /**
     * 修改某个表情包
     *
     * @param key  通过该key 值查找修改
     * @param pack 表情包
     * @return
     */
    boolean updatePack(String key, LinkedHashMap<String, Integer> pack);

}
