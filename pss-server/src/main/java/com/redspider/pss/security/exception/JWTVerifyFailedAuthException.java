package com.redspider.pss.security.exception;

import org.springframework.security.core.AuthenticationException;

/** @author dylan */
public class JWTVerifyFailedAuthException extends AuthenticationException {

  public JWTVerifyFailedAuthException(String msg, Throwable t) {
    super(msg, t);
  }

  public JWTVerifyFailedAuthException(String msg) {
    super(msg);
  }
}
