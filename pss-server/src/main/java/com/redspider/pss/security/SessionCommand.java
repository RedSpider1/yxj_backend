package com.redspider.pss.security;

import cn.hutool.core.util.StrUtil;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;
import lombok.Data;

import java.util.Objects;

/** 用户注册 Command */
@Data
public class SessionCommand {
  private String phone;
  private String verificationCode;

  public SessionCommand(String phone, String verificationCode) {
    if (Objects.isNull(phone)) {
      throw new PssValidationException(ResponseCode.PHONE_DATA_EMPTY);
    }
    if (phone.trim().length() < 11) {
      throw new PssValidationException(ResponseCode.PHONE_DATA_FORMAT_ILLEGAL);
    }
    if (StrUtil.isEmpty(verificationCode)) {
      throw new PssValidationException(ResponseCode.VERIFICATION_CODE_EMPTY);
    }
    this.phone = phone;
    this.verificationCode = verificationCode;
  }

  public static class Builder {
    private String phone;
    private String verificationCode;

    public static Builder newBuilder() {
      return new Builder();
    }

    public Builder withPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder withVCode(String verificationCode) {
      this.verificationCode = verificationCode;
      return this;
    }

    public SessionCommand build() {
      return new SessionCommand(phone, verificationCode);
    }
  }
}
