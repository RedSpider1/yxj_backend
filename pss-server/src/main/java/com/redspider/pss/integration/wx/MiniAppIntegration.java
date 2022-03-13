package com.redspider.pss.integration.wx;

/**
 * @description: 微信小程序集成
 * @author: Tony
 * @date: 2021/8/22
 */
public interface MiniAppIntegration {
    /** 微信小程序登录
     * @param jsCode
     * @return 登录信息
     */
    WxLoginInfoDTO getWxLoginInfo(String jsCode);

    /** 获取手机号
     * @param dto
     * @return 手机号
     */
    String getPhone(WxDataDTO dto);

    WxMaUserInfoDTO getUserInfo(WxDataDTO dto);
}
