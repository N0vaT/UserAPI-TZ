package ru.nova.userapi.service;

import ru.nova.userapi.maodel.PhoneContact;

import java.util.List;

public interface PhoneContactService {
    List<PhoneContact> findAll(long userId, int pageNumber, int pageSize, String direction, String sortByField);
    PhoneContact findById(long userId, long contactId);
    PhoneContact create(PhoneContact contact, long userId);
    PhoneContact patchUpdate(PhoneContact contact, long userId, long contactId);
    PhoneContact putUpdate(PhoneContact contact, long userId, long contactId);
    void delete(long userId, long contactId);
}
