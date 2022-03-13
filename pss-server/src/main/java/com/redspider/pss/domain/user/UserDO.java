package com.redspider.pss.domain.user;

import lombok.Data;

import java.util.Date;

/** @author dylan */
@Data
public class UserDO {
  private Long id;
  private String name;
  private String password;
  private String phone;
  private String wechatNum;
  private String email;
  private String slogan;
  private Date birthday;
  private Integer sex;
  private String avatar;
  private Date createTime;
  private Long updaterId;
  private Date updateTime;
}
