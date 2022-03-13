package com.redspider.pss.integration.wx.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.integration.wx.MiniAppIntegration;
import com.redspider.pss.integration.wx.WxDataDTO;
import com.redspider.pss.integration.wx.WxLoginInfoDTO;
import com.redspider.pss.integration.wx.WxMaUserInfoDTO;
import com.redspider.pss.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 微信小程序集成
 * @author: Tony
 * @date: 2021/8/22
 */
@Component
@Slf4j
public class MiniAppIntegrationImpl implements MiniAppIntegration {
    @Autowired
    private WxMaService wxMaService;

    /**
     * 微信小程序登录
     *
     * @param jsCode
     * @return 登录信息
     */
    @Override
    public WxLoginInfoDTO getWxLoginInfo(String jsCode) {
        log.info("wx mini app login:{}", jsCode);
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.jsCode2SessionInfo(jsCode);
            WxLoginInfoDTO dto = new WxLoginInfoDTO();
            dto.setOpenId(sessionInfo.getOpenid());
            dto.setSessionKey(sessionInfo.getSessionKey());
            dto.setUnionId(sessionInfo.getUnionid());
            log.info("login response:{}", dto);
            return dto;
        } catch (WxErrorException e) {
            throw new PssServerException(ResponseCode.USER_DATA_NOT_FOUND);
        }
    }

    @Override
    public String getPhone(WxDataDTO dto) {
        log.info("bind phone:{}", dto);
        WxMaPhoneNumberInfo phoneNumberInfo = this.wxMaService.getUserService().getPhoneNoInfo(dto.getSessionKey(),
                dto.getEncryptedData(), dto.getIv());
        log.info("phoneNumberInfo:{}", phoneNumberInfo);
        return phoneNumberInfo.getPurePhoneNumber();
    }

    @Override
    public WxMaUserInfoDTO getUserInfo(WxDataDTO dto) {
        log.info("bindUserInfo:{}", dto);
        WxMaUserInfo userInfo = this.wxMaService.getUserService().getUserInfo(dto.getSessionKey(),
            dto.getEncryptedData(), dto.getIv());
        log.info("bindUserInfo:{}", userInfo);
        WxMaUserInfoDTO res = new WxMaUserInfoDTO();
        BeanUtils.copyProperties(userInfo, res);
        return res;
    }

}
