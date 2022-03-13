package com.redspider.pss.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author dylan
 */
public class UserUtil {

    private UserUtil() {
    }

    public static String getToken() {
        return (String) getAuthenticationToken().getCredentials();
    }

    public static Long getUserId() {
        return getPayload().getId();
    }

    public static String getPhone() {
        return getPayload().getPhone();
    }

    public static String getUserName() {
        return getPayload().getName();
    }

    private static UserPayload getPayload() {
        if (getAuthenticationToken() instanceof PssAuthenticationToken) {
            PssAuthenticationToken authentication = (PssAuthenticationToken) getAuthenticationToken();
            return authentication.getPrincipal();
        }
        return UserPayload.EMPTY;
    }

    public static boolean isAnonyRequest() {
        return getAuthenticationToken() instanceof AnonymousAuthenticationToken;
    }

    /**
     * 未鉴权返回：AnonymousAuthenticationToken 鉴权返回： PssAuthenticationToken
     */
    private static Authentication getAuthenticationToken() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
