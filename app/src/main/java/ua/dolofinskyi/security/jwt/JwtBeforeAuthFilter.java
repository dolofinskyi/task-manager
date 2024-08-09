package ua.dolofinskyi.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtBeforeAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.getJwtCookieToken(request, "jwtToken");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String sub = jwtService.extractSub(token);
        if (sub == null) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = userService.getUserByOAuth2Sub(sub);

        if (user == null || !jwtService.isTokenValid(token, user)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, new ArrayList<>()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
