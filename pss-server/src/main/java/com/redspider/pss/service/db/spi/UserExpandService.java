package com.redspider.pss.service.db.spi;

import com.redspider.pss.domain.user.UserExpand;

import java.util.Optional;

/**
 * @description: 用户扩展信息操作
 * @author: Tony
 * @date: 2021/8/22
 */
public interface UserExpandService {
    /** 通过openId获取扩展信息
     * @param openId
     * @return
     */
    Optional<UserExpand> getUserByOpenId(String openId);

    /** 新增用户信息
     * @param record
     */
    Long add(UserExpand record);

    /** 更新用户信息
     * @param record
     */
    void update(UserExpand record);

    /** 根据userId获取用户信息
     * @param userId
     * @return
     */
    Optional<UserExpand> getUserByUserId(Long userId);
}
