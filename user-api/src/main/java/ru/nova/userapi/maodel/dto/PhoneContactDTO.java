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
public class PhoneContactDTO {
    private Long phoneId;
    private String phone;
    private LocalDateTime dateOfAddition;
    private Long ownerId;
}
