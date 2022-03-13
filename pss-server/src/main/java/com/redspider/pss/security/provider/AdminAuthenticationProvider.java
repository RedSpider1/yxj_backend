package com.redspider.pss.security.provider;

import com.redspider.pss.constant.enums.LoginType;
import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.AdminAuthenticationToken;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.security.exception.ParamAuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/** @author qiushui */
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

  private final String userName = "yxj";

  private final String password = "www.youxiaoju.com";

  private final String role = "admin";

  private final Long userId = 0L;

  private final UserConverter converter;

  public AdminAuthenticationProvider() {
    this.converter = UserConverter.INSTANCE;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // 判断是否由此验证器进行处理
    return (AdminAuthenticationToken.class.isAssignableFrom(authentication));
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 进行登录的验证，验证成功后返回存储对象
    UserPayload payload = verifyUser(authentication);
    return new UsernamePasswordAuthenticationToken(payload, null);
  }

  private UserPayload verifyUser(Authentication authentication) {
    // TODO: 目前是写死的手机号+密码验证，后续权限设计会调整
    if (!userName.equals(authentication.getPrincipal())
        || !password.equals(authentication.getCredentials())) {
      throw new ParamAuthenticationException(
              ResponseCode.LOGIN_OR_REGISTER_FAIL.getCode(), ResponseCode.LOGIN_OR_REGISTER_FAIL.getMessage());
    }

    UserDTO userDTO = new UserDTO();
    userDTO.setId(userId);
    userDTO.setName(userName);
    userDTO.setRole(role);
    UserPayload payload = converter.toPayload(userDTO);

    payload.setLoginPlatform(LoginType.H5.name());

    return payload;
  }
}
