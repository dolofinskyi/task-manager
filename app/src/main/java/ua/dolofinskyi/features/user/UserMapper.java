package ua.dolofinskyi.features.user;

import org.springframework.stereotype.Component;
import ua.dolofinskyi.common.mapper.Mapper;

@Component
public class UserMapper implements Mapper<User, UserDto> {

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
        userDto.setUsername(userDto.getUsername());
        userDto.setEmail(userDto.getEmail());
        userDto.setTasks(userDto.getTasks());
        return userDto;
    }
}
