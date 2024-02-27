package ru.nova.userapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.userapi.exception.EmailContactNotFoundException;
import ru.nova.userapi.exception.PhoneContactNotFoundException;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.EmailContact;
import ru.nova.userapi.maodel.PhoneContact;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.repository.EmailContactRepository;
import ru.nova.userapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailContactServiceJPA implements EmailContactService {
    private final EmailContactRepository emailContactRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmailContact> findAll(long userId, int pageNumber, int pageSize, String direction, String sortByField) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        Sort.Direction sortDirection = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortByField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return emailContactRepository.findAllByOwnerUserId(userId, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailContact findById(long userId, long contactId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getEmails().stream()
                .filter(e -> e.getEmailId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new EmailContactNotFoundException("Email with id - " + contactId + " not found."));
    }

    @Override
    @Transactional
    public EmailContact create(EmailContact contact, long userId) {
        contact.setOwner(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found.")));
        contact.setEmailId(null);
        if(contact.getDateOfAddition() == null){
            contact.setDateOfAddition(LocalDateTime.now());
        }
        return emailContactRepository.save(contact);
    }

    @Override
    @Transactional
    public EmailContact patchUpdate(EmailContact contact, long userId, long contactId) {
        EmailContact existingContact = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getEmails().stream()
                .filter(e -> e.getEmailId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new EmailContactNotFoundException("Email with id - " + contactId + " not found."));
        if(contact.getEmail() != null){
            existingContact.setEmail(contact.getEmail());
        }
        if(contact.getDateOfAddition() != null){
            existingContact.setDateOfAddition(contact.getDateOfAddition());
        }
        return emailContactRepository.save(existingContact);
    }

    @Override
    @Transactional
    public EmailContact putUpdate(EmailContact contact, long userId, long contactId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."))
                .getEmails().stream()
                .filter(e -> e.getEmailId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new EmailContactNotFoundException("Email with id - " + contactId + " not found."));
        return emailContactRepository.save(contact);
    }

    @Override
    @Transactional
    public void delete(long userId, long contactId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        EmailContact contact = user.getEmails().stream()
                .filter(e -> e.getEmailId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new EmailContactNotFoundException("Email with id - " + contactId + " not found."));
        user.getEmails().remove(contact);
        userRepository.save(user);
    }
}
