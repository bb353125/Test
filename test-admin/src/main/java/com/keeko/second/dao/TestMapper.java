package com.keeko.second.dao;

import com.keeko.second.entity.SUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 第二数据库 用户相关
 *
 * @author chensq
 */
@Repository
public interface TestMapper {
    /**
     * 批量新增
     *
     * @param sUserList 用户列表
     */
    void batchInsert(List<SUser> sUserList);

    /**
     * 批量更新
     *
     * @param idList 设备列表
     * @param device 设备信息
     */
    void batchUpdate(@Param("idList") String[] idList, @Param("device") SUser device);

    /**
     * 批量删除
     *
     * @param ids ids
     * @return 结果行数
     */
    int batchDelete(String[] ids);
    /**
     * 批量删除
     *
     * @param ids ids
     * @return 结果行数
     */
    int batchRemove(String ids);

    /**
     * 批量查询
     *
     * @param ids ids
     * @return 订单信息列表
     */
    List<SUser> selectByIds(List<String> ids);

    /**
     * 批量查询
     *
     * @param sUsers SUsers
     * @return 订单信息列表
     */
    List<SUser> selectListByIds(List<SUser> sUsers);

    /**
     * 课堂记录列表
     *
     * @param vo 课堂记录vo
     * @return 课堂记录
     */
    List<SUser> exportFile(SUser vo);



    SUser selectByOpenId(String openId);
    int insert(SUser user);
    void updateById(SUser user);
    List<SUser> sUserList(SUser vo);
    SUser selectMaxVersion();
    List<SUser> selectByVersion(Long version);
    void insertY(SUser sUser);
    int insert1(SUser sUser);

    SUser selectById(String id);

}
