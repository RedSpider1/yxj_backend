package com.redspider.pss.base;

/**
 * 手机号 DP
 */
public class Phone {
  private String number;

  public Phone(String number) {
    // 可做校验
    this.number = number;
  }

  public String getValue() {
    return number;
  }
}
