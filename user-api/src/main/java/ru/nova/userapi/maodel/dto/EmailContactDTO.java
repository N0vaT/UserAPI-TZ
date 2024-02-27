package ru.nova.userapi.maodel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailContactDTO {
    private Long emailId;
    private String email;
    private LocalDateTime dateOfAddition;
    private Long ownerId;
}
