package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.User;
import pl.dmcs.jmazur.dto.UserDto;


@Component
public class UserMapper {

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();
    }

    public User mapToUser(UserDto userDto) {
        return User.builder()
                .firstname(userDto.getFirstname())
                .surname(userDto.getSurname())
                .dateOfBirth(userDto.getDateOfBirth())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
