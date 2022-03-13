package com.redspider.pss.service.db.spi;

import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.SessionCommand;
import com.redspider.pss.dto.user.UserDTO;

import java.util.List;

/** @author dylan */
public interface UserApplicationService {

    UserDTO session(SessionCommand sessionCommand);

    /**
     * 查询用户信息, 用于展示
     *
     * @author xiaoA
     */
    ResponseResult<UserDTO> getUserInfo();

    /**
     * 用户注册短信
     *
     * @param phoneNumber 用户手机号
     * @return java.lang.Boolean @Date 2021/6/20 18:43 @Author wangyl @Version V1.0
     */
    void signSMS(String phoneNumber) throws Exception;

    /**
     * 用户组队成功/失败
     *
     * @param phoneNumber 用户手机号列表
     * @param success     true 组队成功 / false 组队失败
     * @param teamId     组队单id
     * @return java.lang.Boolean @Date 2021/8/12 23:09 @Author Lucyvictor @Version V1.0
     */
    void groupSMS(List<String> phoneNumber, Boolean success, Long teamId) throws Exception;

    /**
     * 更新用户信息
     *
     * @author xiaoA
     */
    ResponseResult updateUserInfo(UserDTO userInfo);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    ResponseResult<UserDTO> getUserInfoById(Long userId);
}
