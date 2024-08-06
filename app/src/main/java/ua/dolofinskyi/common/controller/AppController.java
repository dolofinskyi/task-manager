package ua.dolofinskyi.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dolofinskyi.features.task.TaskDto;
import ua.dolofinskyi.features.user.UserDto;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.util.ArrayList;

@Controller
public class AppController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/app")
    public String app(Model model) {
        UserDto userDto = userService.getUserDtoFromSecurityContextHolder();

        for (int i = 0; i < 5; i++) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId((long) i);
            taskDto.setTitle("Title %d".formatted(i));
            taskDto.setDescription("Description %d".formatted(i));
            taskDto.setIsDone(i % 2 == 0);
            userDto.getTasks().add(taskDto);
        }

        model.addAttribute("user", userDto);
        return "app";
    }
}
