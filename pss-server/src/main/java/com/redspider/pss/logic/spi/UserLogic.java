package com.redspider.pss.logic.spi;

import com.redspider.pss.integration.wx.WxDataDTO;
import com.redspider.pss.dto.user.UserDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户相关操作
 * @author: Tony
 * @date: 2021年8月22日17:08:56
 */
@Validated
public interface UserLogic {
    /** 微信小程序登录
     * @param jsCode 登录代码
     * @return
     */
    String freeLoginForWx(@NotBlank String jsCode);

    String loginForWx(WxDataDTO wxDataDTO);

    UserDTO getUserInfo(Long userId);
}
