package ru.nova.userapi.maodel.mapper;

import org.springframework.stereotype.Component;
import ru.nova.userapi.maodel.PhoneContact;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.maodel.dto.PhoneContactDTO;

@Component
public class PhoneContactMapper {
    public PhoneContactDTO toDto(PhoneContact contact){
        return PhoneContactDTO.builder()
                .phoneId(contact.getPhoneId())
                .phone(contact.getPhone())
                .dateOfAddition(contact.getDateOfAddition())
                .ownerId(contact.getOwner().getUserId())
                .build();
    }

    public PhoneContact toPhoneContact(PhoneContactDTO contactDTO){
        return PhoneContact.builder()
                .phoneId(contactDTO.getPhoneId())
                .phone(contactDTO.getPhone())
                .dateOfAddition(contactDTO.getDateOfAddition())
                .owner(User.builder()
                        .userId(contactDTO.getOwnerId())
                        .build())
                .build();
    }
}
