package com.redspider.pss.logic.impl;

import com.redspider.pss.constant.enums.UserContactInformationType;
import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.domain.user.UserExpand;
import com.redspider.pss.dto.user.UserContactInformationDTO;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.integration.wx.MiniAppIntegration;
import com.redspider.pss.integration.wx.WxDataDTO;
import com.redspider.pss.integration.wx.WxLoginInfoDTO;
import com.redspider.pss.logic.spi.UserLogic;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.TokenHelper;
import com.redspider.pss.service.db.spi.UserContactInformationService;
import com.redspider.pss.service.db.spi.UserExpandService;
import com.redspider.pss.service.db.spi.UserService;
import com.redspider.pss.utils.EncryptionSting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @description: 用户相关操作
 * @author: Tony
 * @date: 2021/8/22
 */
@Service
@Slf4j
public class UserLogicImpl implements UserLogic {
    @Autowired
    private MiniAppIntegration miniAppIntegration;
    @Autowired
    private UserExpandService userExpandService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserContactInformationService userContactInformationService;

    /**
     * 微信小程序免登
     *
     * @param jsCode 免登code
     * @return jwt
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String freeLoginForWx(String jsCode) {
        log.info("loginForWx: {}", jsCode);
        WxLoginInfoDTO wxLoginInfoDTO = miniAppIntegration.getWxLoginInfo(jsCode);
        if (Objects.isNull(wxLoginInfoDTO)) {
            return "";
        }
        // 检查是否注册过，如果注册过，更新session并返回jwt
        Optional<UserExpand> userExpandOptional = userExpandService.getUserByOpenId(wxLoginInfoDTO.getOpenId());
        if (userExpandOptional.isPresent()) {
            refreshWxSession(userExpandOptional.get(), wxLoginInfoDTO.getSessionKey());
            return TokenHelper.generateByUserId(userExpandOptional.get().getUserId());
        } else {
            return "";
        }
    }


    /**
     * 微信小程序登录
     *
     * @param wxDataDTO 微信小程序数据
     * @return jwt
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String loginForWx(WxDataDTO wxDataDTO) {
        log.info("loginForWx:{}", wxDataDTO);
//        // 先尝试免登，再尝试注册
//        String freeLoginRes = freeLoginForWx(wxDataDTO.getJsCode());
//        if (StringUtils.isNotBlank(freeLoginRes)) {
//            return freeLoginRes;
//        }
        WxLoginInfoDTO wxLoginInfoDTO = miniAppIntegration.getWxLoginInfo(wxDataDTO.getJsCode());
        // 检查是否注册过，如果注册过，更新session并返回jwt
        Optional<UserExpand> userExpandOptional = userExpandService.getUserByOpenId(wxLoginInfoDTO.getOpenId());
        if (userExpandOptional.isPresent()) {
            refreshWxSession(userExpandOptional.get(), wxLoginInfoDTO.getSessionKey());
            return TokenHelper.generateByUserId(userExpandOptional.get().getUserId());
        }
        // 否则，获取手机号
        wxDataDTO.setSessionKey(wxLoginInfoDTO.getSessionKey());
        String phone = miniAppIntegration.getPhone(wxDataDTO);
        // 用手机号找用户
        UserDO userDO = userService.getByPhone(phone);
        // 如果找到了，就直接返回
        if (userDO != null) {
            log.info("loginForWx success, userId {}", userDO.getId());
            return TokenHelper.generateByUserId(userDO.getId());
        }

        // 如果没找到，新建用户
        UserDO baseUser = new UserDO();
        baseUser.setName(EncryptionSting.getStringRandom(11));
        baseUser.setPhone(phone);
        baseUser.setAvatar("");
        baseUser.setSex(0);
        Long userId = userService.add(baseUser);

        UserExpand userExpand = new UserExpand();
        userExpand.setUserId(userId);
        userExpand.setOpenId(wxLoginInfoDTO.getOpenId());
        userExpand.setSessionKey(wxLoginInfoDTO.getSessionKey());
        userExpand.setUnionId(wxLoginInfoDTO.getUnionId());
        userExpand.setUpdaterId(userId);
        userExpandService.add(userExpand);
        log.info("register new user, phone is {}, id is {}", phone, baseUser.getId());

        // 初始化手机号联系方式
        UserContactInformationDTO informationDTO = new UserContactInformationDTO();
        informationDTO.setUserId(userId);
        informationDTO.setType(UserContactInformationType.PHONE.getCode());
        informationDTO.setContactInformation(phone);
        userContactInformationService.createContactInformation(informationDTO);

        return TokenHelper.generateByUserId(baseUser.getId());
    }

    @Override
    public UserDTO getUserInfo(Long userId) {
        return userService.findById(userId)
                .orElseThrow(() -> new PssValidationException(ResponseCode.ACCESS_ILLEGAL));
    }

    private void refreshWxSession(UserExpand userExpand, String sessionKey) {
        log.info("update user info");
        Long userId = userExpand.getUserId();
        userExpand.setSessionKey(sessionKey);
        userExpand.setUpdateTime(new Date());
        userExpand.setUpdaterId(userId);
        userExpandService.update(userExpand);
    }
}
