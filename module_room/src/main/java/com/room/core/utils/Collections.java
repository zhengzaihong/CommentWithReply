package com.room.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * create_user: zhengzaihong
 * Email:1096877329@qq.com
 * create_date: 2019/12/31
 * create_time: 14:36
 * describe 集合工具
 **/
public class Collections {

    /**
     * 判断List集合是否为空
     *
     * @param data
     * @return
     */

    public static boolean isEmptyList(List<?> data) {
        return (data == null || data.size() == 0) ? true : false;
    }

    /**
     * 判断ArrayList集合是否为空
     *
     * @param data
     * @return
     */

    public static boolean isEmptyArrayList(ArrayList<?> data) {
        return (data == null || data.size() == 0) ? true : false;
    }


    /**
     * 判断 LinkedHashMap 集合是否为空
     *
     * @param linkedHashMap
     * @return
     */

    public static boolean isEmptyLinkedHashMap(LinkedHashMap<?, ?> linkedHashMap) {
        return (linkedHashMap == null || linkedHashMap.size() == 0) ? true : false;
    }


    /**
     * 判断 hashMap 集合是否为空
     *
     * @param hashMap
     * @return
     */
    public static boolean isEmptyHashMap(HashMap<?, ?> hashMap) {
        return (hashMap == null || hashMap.size() == 0) ? true : false;
    }
}
