package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.User;
import pl.dmcs.jmazur.dto.UserForAdminDto;

@Component
public class UserForAdminMapper {

    public UserForAdminDto mapToDto(User user) {
        return UserForAdminDto.builder()
                .uuid(user.getUuid())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .isActive(user.isActive())
                .roles(user.getRoles())
                .build();
    }
}
