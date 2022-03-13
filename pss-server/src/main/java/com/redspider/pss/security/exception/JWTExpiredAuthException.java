package com.redspider.pss.security.exception;

import org.springframework.security.core.AuthenticationException;

/** @author dylan */
public class JWTExpiredAuthException extends AuthenticationException {

  public JWTExpiredAuthException(String msg) {
    super(msg);
  }

  public JWTExpiredAuthException(String msg, Throwable t) {
    super(msg, t);
  }
}
