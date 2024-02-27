package ru.nova.userapi.service;

import ru.nova.userapi.maodel.User;

import java.util.List;

public interface UserService {
    List<User> findAll(int pageNumber, int pageSize, String direction, String sortByField);
    User findById(long userId);
    User create(User user);
    User patchUpdate(User user, long userId);
    User putUpdate(User user, long userId);
    void delete(long userId);
}
