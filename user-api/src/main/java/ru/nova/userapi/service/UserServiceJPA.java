package ru.nova.userapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.EmailContact;
import ru.nova.userapi.maodel.PhoneContact;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.repository.EmailContactRepository;
import ru.nova.userapi.repository.PhoneContactRepository;
import ru.nova.userapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;
    private final EmailContactRepository emailContactRepository;
    private final PhoneContactRepository phoneContactRepository;

    @Override
    public List<User> findAll(int pageNumber, int pageSize, String direction, String sortByField) {
        Sort.Direction sortDirection = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortByField);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        return userRepository.findAll(pageRequest).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
    }

    @Override
    @Transactional
    public User create(User user) {
        List<EmailContact> emails = user.getEmails();
        List<PhoneContact> phones = user.getPhones();
        user.setUserId(null);
        user.setEmails(null);
        user.setPhones(null);
        if (user.getDateOfCreation() == null) {
            user.setDateOfCreation(LocalDateTime.now());
        }
        User newUser = userRepository.save(user);
        if(emails != null){
            emails.forEach(e -> {
                e.setEmailId(null);
                e.setOwner(newUser);
            });
            emails = emailContactRepository.saveAll(emails);
        }
        if(phones != null){
            phones.forEach(p -> {
                p.setPhoneId(null);
                p.setOwner(newUser);
            });
            phones = phoneContactRepository.saveAll(phones);
        }
        newUser.setPhones(phones);
        newUser.setEmails(emails);
        return newUser;
    }

    @Override
    @Transactional
    public User patchUpdate(User user, long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        if(user.getLogin() != null){
            existingUser.setLogin(user.getLogin());
        }
        if(user.getDateOfCreation() != null){
            existingUser.setDateOfCreation(user.getDateOfCreation());
        }
        if(user.getPhones() != null){
            existingUser.getPhones().removeAll(existingUser.getPhones());
            existingUser.getPhones().addAll(user.getPhones());
        }
        if(user.getEmails() != null){
            existingUser.getEmails().removeAll(existingUser.getEmails());
            existingUser.getEmails().addAll(user.getEmails());
        }
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public User putUpdate(User user, long userId) {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        oldUser.setLogin(user.getLogin());
        oldUser.setDateOfCreation(user.getDateOfCreation());
        oldUser.getEmails().removeAll(oldUser.getEmails());
        if(user.getEmails() != null){
            oldUser.getEmails().addAll(user.getEmails());
        }
        oldUser.getPhones().removeAll(oldUser.getPhones());
        if(user.getPhones() != null){
            oldUser.getPhones().addAll(user.getPhones());
        }
        return userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void delete(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id - " + userId + " not found."));
        userRepository.deleteById(userId);
    }
}
