package com.keeko.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestUtil {






    /**
     * List 分组 1.8特性
     */
   /* public Map<String, List<Teacher>> groupList(List<Teacher> teachers) {
        Map<String, List<Teacher>> map = teachers.stream().collect(Collectors.groupingBy(Teacher::getName));
        //遍历map
        for (Object o : map.keySet()) {
            //取出key
            String key = o.toString();
            //通过key拿到value
            List<Teacher> teacherList = map.get(key);
        }
        return map;
    }*/

    /**
     * List 分组
     */
   /* public static Map<String, List<Teacher>> groupList1(List<Teacher> teachers) {
        Map<String, List<Teacher>> map = new HashMap<>();
        for (Teacher teacher : teachers) {
            List<Teacher> tmpList = map.get(teacher.getName());
            if (tmpList == null) {
                tmpList = new ArrayList<>();
                tmpList.add(teacher);
                map.put(teacher.getName(), tmpList);
            } else {
                tmpList.add(teacher);
            }
        }
        return map;
    }*/
}
