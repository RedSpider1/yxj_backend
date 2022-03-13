package com.redspider.pss.vo.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserVO implements Serializable {

    private Long id;

    private String name;

    private String role;

    private String phone;

    private String wechatNum;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private Integer sex;

    private String avatar;

    private String slogan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
