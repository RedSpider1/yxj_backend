package com.redspider.pss;

import com.redspider.pss.security.PssAuthenticationToken;
import com.redspider.pss.security.UserPayload;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PssApplication.class)
public abstract class PssApplicationTests {

    private AutoCloseable closeable;

    @Test
    void contextLoads() {

    }

    @BeforeEach
    void setUser() {
        UserPayload userPayload = new UserPayload();
        userPayload.setId(13L);
        Authentication authenticate = new PssAuthenticationToken("Authorization", userPayload);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

}
