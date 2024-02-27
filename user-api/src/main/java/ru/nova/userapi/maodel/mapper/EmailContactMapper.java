package ru.nova.userapi.maodel.mapper;

import org.springframework.stereotype.Component;
import ru.nova.userapi.maodel.EmailContact;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.maodel.dto.EmailContactDTO;

@Component
public class EmailContactMapper {
    public EmailContactDTO toDto(EmailContact contact){
        return EmailContactDTO.builder()
                .emailId(contact.getEmailId())
                .email(contact.getEmail())
                .dateOfAddition(contact.getDateOfAddition())
                .ownerId(contact.getOwner().getUserId())
                .build();
    }

    public EmailContact toEmailContact(EmailContactDTO contactDTO){
        return EmailContact.builder()
                .emailId(contactDTO.getEmailId())
                .email(contactDTO.getEmail())
                .dateOfAddition(contactDTO.getDateOfAddition())
                .owner(User.builder()
                        .userId(contactDTO.getOwnerId())
                        .build())
                .build();
    }
}
