package com.redspider.pss.security;

import org.junit.jupiter.api.Test;

class TokenHelperTest {

    @Test
    void generate() {
        UserPayload payload = new UserPayload();
        payload.setId(7L);
        System.out.println(TokenHelper.generate(payload));
    }
}
