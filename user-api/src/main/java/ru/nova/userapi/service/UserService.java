package ru.nova.userapi.service;

import ru.nova.userapi.maodel.User;

import java.util.List;

public interface UserService {
    List<User> findAll(int pageNumber, int pageSize, String direction, String sortByField);
}
