package ru.nova.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nova.userapi.maodel.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
