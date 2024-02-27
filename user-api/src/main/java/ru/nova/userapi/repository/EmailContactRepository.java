package ru.nova.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nova.userapi.maodel.EmailContact;

public interface EmailContactRepository extends JpaRepository<EmailContact, Long> {
}
