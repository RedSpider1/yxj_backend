package com.redspider.pss.vo.user;

import java.io.Serializable;

import com.redspider.pss.vo.team.UserVO;
import lombok.Data;

/**
 * @description: 登录返回对象
 * @author: tony
 * @date: 2021年9月14日23:34:04
 */
@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    private String loginPlatform;

    private Boolean newUser = false;

    private Boolean hasPhone = false;

    private UserVO userInfo;
}
