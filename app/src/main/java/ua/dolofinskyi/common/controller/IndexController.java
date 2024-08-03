package ua.dolofinskyi.common.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("user", oAuth2User.getUser());
        return "index";
    }
}
