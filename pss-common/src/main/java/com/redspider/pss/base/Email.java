package com.redspider.pss.base;

import lombok.Data;

/**
 * Email 邮箱
 */
@Data
public class Email {
  private String value;

  public Email(String email) {
    // TODO: 校验规则
    this.value = email;
  }

  public String getValue() {
    return this.value;
  }
}
