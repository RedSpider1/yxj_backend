package com.redspider.pss.integration.wx;

import java.io.Serializable;
import lombok.Data;

@Data
public class WxMaUserInfoDTO implements Serializable {
    private static final long serialVersionUID = 6719822331555402137L;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
}