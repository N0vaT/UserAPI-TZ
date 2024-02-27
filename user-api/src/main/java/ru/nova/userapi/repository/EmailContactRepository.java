package ru.nova.userapi.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nova.userapi.maodel.EmailContact;

import java.util.List;

public interface EmailContactRepository extends JpaRepository<EmailContact, Long> {
    List<EmailContact> findAllByOwnerUserId(long userId, PageRequest pageRequest);
}
