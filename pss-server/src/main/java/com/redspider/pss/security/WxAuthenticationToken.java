package com.redspider.pss.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author Tony
 * @apiNote 微信免登
 * @since 2021/8/23
 */
public class WxAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    
    private final Object principal;
    private final Object credentials;
    
    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     */
    public WxAuthenticationToken(Object principal, Object credentials) {
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
