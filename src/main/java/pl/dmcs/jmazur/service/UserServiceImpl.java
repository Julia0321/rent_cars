package pl.dmcs.jmazur.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.domain.ActivationToken;
import pl.dmcs.jmazur.domain.Role;
import pl.dmcs.jmazur.domain.User;
import pl.dmcs.jmazur.dto.UserDto;
import pl.dmcs.jmazur.dto.UserEditDto;
import pl.dmcs.jmazur.dto.UserForAdminDto;
import pl.dmcs.jmazur.enums.RoleEnum;
import pl.dmcs.jmazur.mapper.UserEditMapper;
import pl.dmcs.jmazur.mapper.UserForAdminMapper;
import pl.dmcs.jmazur.mapper.UserMapper;
import pl.dmcs.jmazur.repository.ActivationTokenRepository;
import pl.dmcs.jmazur.repository.RoleRepository;
import pl.dmcs.jmazur.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ActivationTokenRepository activationTokenRepository;
    private final EmailServiceImpl emailService;
    private final UserEditMapper userEditMapper;
    private final UserForAdminMapper userForAdminMapper;

    @Value("${mail.username}")
    private String mailUsername;

    private static final String LINK = "http://localhost:8080/rentcar_war_exploded/users/activate?token=";

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail()) && userDto.isActive()) {
            throw new IllegalArgumentException("Email taken");
        }

        User user = userMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (user.getRoles() == null) {
            assignDefaultRole(user);
        }

        userRepository.save(user);

        createTokenAndSendActivationEmail(user);
    }


    private void assignDefaultRole(User user) {

        user.setRoles(new HashSet<>());

        Role roleUser = roleRepository.findByRoleName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Role 'user' not found"));

        user.getRoles().add(roleUser);
    }

    private void createTokenAndSendActivationEmail(User user) {

        ActivationToken activationToken = new ActivationToken();
        activationToken.setUser(user);

        activationTokenRepository.save(activationToken);

        String link = LINK + activationToken.getToken();

        emailService.sendMail(mailUsername, link, "Activation link");
    }

    @Override
    public void checkToken(String token) {

        ActivationToken repositoryToken = activationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (repositoryToken == null) {
            throw new RuntimeException("Token exist");
        }

        if (repositoryToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepository.findById(repositoryToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);
        userRepository.saveAndFlush(user);

    }

    @Override
    public UserDto findUserByFirstname(String firstname) {

        return userMapper.mapToDto(
                userRepository.findUserByFirstname(firstname)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    @Override
    public UserDto findUserByEmail(String email) {

        return userMapper.mapToDto(
                userRepository.findUserByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    @Override
    public UserEditDto findUserByEmailToEdit(String email) {

        return userEditMapper.mapToDto(
                userRepository.findUserByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    @Override
    public List<UserDto> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void edit(UserEditDto user, String email) {

        User userDB = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() != null) {
            userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDB.setFirstname(user.getFirstname());
        userDB.setSurname(user.getSurname());
        userDB.setPhoneNumber(user.getPhoneNumber());

        userRepository.saveAndFlush(userDB);
    }

    @Override
    public List<UserForAdminDto> findAllUsersForAdmin() {

        return userRepository.findAll()
                .stream()
                .map((u) -> userForAdminMapper.mapToDto(u)).collect(Collectors.toList());
    }

    @Override
    public boolean changeActivity(String uuid) {

        User userDB = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDB.isActive()) {
            userDB.setActive(false);
            userRepository.saveAndFlush(userDB);

            return false;
        } else {
            userDB.setActive(true);
            userRepository.saveAndFlush(userDB);

            return true;
        }
    }

    @Override
    public void addAdminRole(String uuid) {

        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByRoleName(RoleEnum.ADMIN)
                .orElseThrow(() -> new RuntimeException("Role 'user' not found"));

        user.getRoles().add(adminRole);
        userRepository.save(user);
    }

    @Override
    public void removeAdminRole(String uuid) {

        User user = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getEmail())) return;

        user.getRoles().removeIf((r) -> r.getRoleName() == RoleEnum.ADMIN);
        userRepository.save(user);
    }
}
