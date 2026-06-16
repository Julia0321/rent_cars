package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.User;
import pl.dmcs.jmazur.dto.UserEditDto;

@Component
public class UserEditMapper {

    public UserEditDto mapToDto(User user) {
        return UserEditDto.builder()
                .uuid(user.getUuid())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
