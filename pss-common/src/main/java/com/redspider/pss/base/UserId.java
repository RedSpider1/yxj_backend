package com.redspider.pss.base;

import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;

import java.util.Objects;

public class UserId implements Identifier<Long> {
  public Long id;

  public UserId() {}

  public UserId(Long id) {
    if (Objects.isNull(id) || id.longValue() < 1L) {
      throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER,"user id is invalid");
    }
    this.id = id;
  }

  @Override
  public Long getValue() {
    return id;
  }

  @Override
  public String toString() {
    return "UserId{" + "id=" + id + '}';
  }
}
