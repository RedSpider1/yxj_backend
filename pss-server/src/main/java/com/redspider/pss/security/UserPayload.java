package com.redspider.pss.security;

import lombok.Data;

/** @author dylan */
@Data
public class UserPayload {

  public static final String USER_ID = "userId";
  public static final UserPayload EMPTY = new UserPayload();

  private Long id;
  private String name;
  private String email;
  private String phone;
  private String role;
  private String loginPlatform;
  private Boolean newUser = false;
  private Boolean hasPhone = false;
}
