package ua.dolofinskyi.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        User user = userService.getUserFromSecurityContextHolder();
        String token = jwtService.generateToken(user);

        Cookie cookie = new Cookie("jwtToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        response.sendRedirect("/app");
    }
}
