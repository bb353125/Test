package com.keeko.master.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 菜单相关
 *
 * @author chensq
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Menu {
    /**
     * 主键id
     */
    private String id;
    /**
     * 父级编号
     */
    private String parentId;
    /**
     * 父级名称
     */
    private String parentName;
    /**
     * 所有父级编号
     */
    private String parentIds;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单链接
     */
    private String href;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 权限状态
     */
    private String status;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 子菜单
     */
    private List<Menu> menuList;

}
