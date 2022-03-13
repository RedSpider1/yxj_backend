package com.redspider.pss.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/** @author dylan */
public class PssAuthenticationToken extends AbstractAuthenticationToken {
  private final String token;

  private final UserPayload payload;

  public PssAuthenticationToken(String credentials, UserPayload principal) {
    super(Collections.emptyList());
    this.token = credentials;
    this.payload = principal;
    setAuthenticated(payload != null);
  }

  @Override
  public String getCredentials() {
    return token;
  }

  @Override
  public UserPayload getPrincipal() {
    return payload;
  }
}
