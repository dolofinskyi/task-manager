package ua.dolofinskyi.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dolofinskyi.features.user.UserDto;
import ua.dolofinskyi.features.user.UserServiceImpl;

@Controller
public class AppController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/app")
    public String app(Model model) {
        UserDto userDto = userService.getUserDtoFromSecurityContextHolder();
        model.addAttribute("user", userDto);
        return "app";
    }
}
