package ru.nova.userapi.maodel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nova.userapi.maodel.EmailContact;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
    private Long userId;
    private String login;
    private List<EmailContact> emails;
}
