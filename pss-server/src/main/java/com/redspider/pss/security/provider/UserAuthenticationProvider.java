package com.redspider.pss.security.provider;

import com.redspider.pss.constant.enums.LoginType;
import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.security.SessionCommand;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.security.exception.ParamAuthenticationException;
import com.redspider.pss.service.db.spi.UserApplicationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author dylan
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserApplicationService userApplicationService;
    private final UserConverter converter;

    public UserAuthenticationProvider(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
        this.converter = UserConverter.INSTANCE;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断是否由此验证器进行处理
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 进行登录的验证，验证成功后返回存储对象
        UserPayload payload = verifyUser(authentication);
        payload.setLoginPlatform(LoginType.H5.name());
        return new UsernamePasswordAuthenticationToken(payload, null);
    }

    private UserPayload verifyUser(Authentication authentication) {
        try {
            SessionCommand sessionCommand =
                new SessionCommand(
                    (String) authentication.getPrincipal(), (String) authentication.getCredentials());
            UserDTO userDTO = userApplicationService.session(sessionCommand);
            return converter.toPayload(userDTO);
        } catch (PssValidationException e) {
            throw new ParamAuthenticationException(e.getCode(), e.getMessage());
        }
    }
}
