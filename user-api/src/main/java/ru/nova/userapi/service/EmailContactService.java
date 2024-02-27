package ru.nova.userapi.service;

import ru.nova.userapi.maodel.EmailContact;
import ru.nova.userapi.maodel.PhoneContact;

import java.util.List;

public interface EmailContactService {
    List<EmailContact> findAll(long userId, int pageNumber, int pageSize, String direction, String sortByField);
    EmailContact findById(long userId, long contactId);
    EmailContact create(EmailContact contact, long userId);
    EmailContact patchUpdate(EmailContact contact, long userId, long contactId);
    EmailContact putUpdate(EmailContact contact, long userId, long contactId);
    void delete(long userId, long contactId);
}
