package ua.dolofinskyi.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.dolofinskyi.common.mapper.Mapper;
import ua.dolofinskyi.features.task.TaskMapper;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setTasks(taskMapper.entitiesToDtos(user.getTasks()));
        return userDto;
    }
}
