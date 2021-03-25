package com.keeko.utils;


import com.keeko.master.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {

    /**
     * 遍历list 获取菜单列表 ps:pcode=1 这里是表中id是1 所以才这么用
     *
     * @param list 菜单列表
     * @return 处理好的菜单列表
     */
    public static List<Menu> getChildPerms(List<Menu> list){
        //父节点编号
        String pcode = "1";
        List<Menu> returnList = new ArrayList<>();
        for (Menu menu : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (menu.getParentId().equals(pcode)) {
                recursionFn(list, menu);
                returnList.add(menu);
            }
        }
        return returnList;
    }

    /**
     * 递归列表 得到子节点列表
     */
    private static void recursionFn(List<Menu> list, Menu menu) {
        //得到子节点列表
        List<Menu> childList = getChildList(list, menu);
        menu.setMenuList(childList);
        for (Menu m : childList) {
            if (hasChild(list, m)) {
                // 判断是否有子节点
                for (Menu n : childList) {
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<Menu> list, Menu menu) {
        return getChildList(list, menu).size() > 0;
    }

    /**
     * 得到子节点列表
     */
    /**
     * 得到子节点列表
     */
    private static List<Menu> getChildList(List<Menu> list, Menu menu) {
        List<Menu> menuList = new ArrayList<>();
        for (Menu m : list) {
            if (m.getParentId().equals(menu.getId())) {
                menuList.add(m);
            }
        }
        return menuList;
    }

}
