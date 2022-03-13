package com.redspider.pss.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/** @author qiushui */
public class AdminAuthenticationToken extends AbstractAuthenticationToken {

  /** username */
  private final Object principal;

  /** passwd */
  private final Object credentials;

  public AdminAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }
}
