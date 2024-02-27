package ru.nova.userapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.userapi.exception.PhoneContactNotFoundException;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.PhoneContact;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.repository.PhoneContactRepository;
import ru.nova.userapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneContactServiceJPA implements PhoneContactService {
    private final PhoneContactRepository phoneContactRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PhoneContact> findAll(long userId, int pageNumber, int pageSize, String direction, String sortByField) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        ;
        Sort.Direction sortDirection = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortByField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return phoneContactRepository.findAllByOwnerUserId(userId, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public PhoneContact findById(long userId, long contactId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getPhones().stream()
                .filter(p -> p.getPhoneId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new PhoneContactNotFoundException("Phone with id - " + contactId + " not found."));
    }

    @Override
    @Transactional
    public PhoneContact create(PhoneContact contact, long userId) {
        contact.setOwner(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found.")));
        contact.setPhoneId(null);
        if (contact.getDateOfAddition() == null) {
            contact.setDateOfAddition(LocalDateTime.now());
        }
        return phoneContactRepository.save(contact);
    }

    @Override
    @Transactional
    public PhoneContact patchUpdate(PhoneContact contact, long userId, long contactId) {
        PhoneContact existingContact = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getPhones().stream()
                .filter(p -> p.getPhoneId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new PhoneContactNotFoundException("Phone with id - " + contactId + " not found."));
        if (contact.getPhone() != null) {
            existingContact.setPhone(contact.getPhone());
        }
        if (contact.getDateOfAddition() != null) {
            existingContact.setDateOfAddition(contact.getDateOfAddition());
        }
        return phoneContactRepository.save(existingContact);
    }

    @Override
    @Transactional
    public PhoneContact putUpdate(PhoneContact contact, long userId, long contactId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getPhones().stream()
                .filter(p -> p.getPhoneId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new PhoneContactNotFoundException("Phone with id - " + contactId + " not found."));
        return phoneContactRepository.save(contact);
    }

    @Override
    @Transactional
    public void delete(long userId, long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        PhoneContact contact = user.getPhones().stream()
                .filter(p -> p.getPhoneId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new PhoneContactNotFoundException("Phone with id - " + contactId + " not found."));
        user.getPhones().remove(contact);
        userRepository.save(user);
    }
}
