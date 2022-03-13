package com.redspider.pss.service.db.spi;

import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.vo.user.UserInfoVO;

import java.util.Optional;

/**
 * @description: 用户基本信息操作
 * @author: Tony
 * @date: 2021/8/22
 */
public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    UserDO getById(Long userId);

    /**
     * 通过电话号码获取用户信息
     * @param phone 电话号码
     * @return 用户信息
     */
    UserDO getByPhone(String phone);

    /**
     * 新增用户信息
     *
     * @param record
     */
    Long add(UserDO record);

    /**
     * 更新用户信息
     *
     * @param record
     */
    void update(UserDO record);

    Optional<UserDTO> findById(Long id);

    void updatePhoneAndUpdaterIdById(Long id, String phone);
    
    UserInfoVO getUserInfoById(Long id);
}
