package com.redspider.pss.base;

import java.util.Date;

/**
 * 生日
 */
public class Birthday {
  private Date value;

  public Birthday(Date birthday) {
    this.value = birthday;
  }

  public Date getValue(){
    return this.value;
  }
}
