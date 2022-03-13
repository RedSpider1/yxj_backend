package com.redspider.pss.domain.user;

import com.redspider.pss.base.*;
import lombok.Data;

import java.util.Date;

/**
 * User Entity
 * @author dylan
 */
@Data
public class User {
  private UserId id;
  private Name name;
  private Phone phone;
  private WechatNum wechatNum;
  private Email email;
  private Birthday birthday;
  private Sex sex;
  private String avatar;
  private String slogan;
  private UserId updaterId;
  private Date createTime;
  private Date updateTime;
}
