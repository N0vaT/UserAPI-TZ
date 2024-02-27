package ru.nova.userapi.maodel.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.maodel.dto.UserDTO;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final EmailContactMapper emailContactMapper;
    private final PhoneContactMapper phoneContactMapper;
    public UserDTO toDto(User user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .login(user.getLogin())
                .dateOfCreation(user.getDateOfCreation())
                .emails(user.getEmails() == null ? null : user.getEmails().stream()
                        .map(emailContactMapper::toDto)
                        .toList())
                .phones(user.getPhones() == null ? null : user.getPhones().stream()
                        .map(phoneContactMapper::toDto)
                        .toList())
                .build();
    }

    public User toUser(UserDTO userDTO){
        return User.builder()
                .userId(userDTO.getUserId())
                .login(userDTO.getLogin())
                .dateOfCreation(userDTO.getDateOfCreation())
                .emails(userDTO.getEmails() == null ? null : userDTO.getEmails().stream()
                        .map(emailContactMapper::toEmailContact)
                        .toList())
                .phones(userDTO.getPhones() == null ? null : userDTO.getPhones().stream()
                        .map(phoneContactMapper::toPhoneContact)
                        .toList())
                .build();
    }
}
