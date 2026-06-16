package pl.dmcs.jmazur.service;

import pl.dmcs.jmazur.dto.UserDto;
import pl.dmcs.jmazur.dto.UserEditDto;
import pl.dmcs.jmazur.dto.UserForAdminDto;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    void checkToken(String token);

    UserDto findUserByFirstname(String firstname);

    UserDto findUserByEmail(String email);

    UserEditDto findUserByEmailToEdit(String email);

    List<UserDto> findAll();

    void edit(UserEditDto user, String name);

    List<UserForAdminDto> findAllUsersForAdmin();

    boolean changeActivity(String uuid);

    void addAdminRole(String uuid);

    void removeAdminRole(String uuid);
}
