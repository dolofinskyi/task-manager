package ua.dolofinskyi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SecurityConfig.class})
class SecurityConfigTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testSecurityConfigIsEnabled(){
        assertNotNull(applicationContext.getBean(SecurityConfig.class));
    }
}