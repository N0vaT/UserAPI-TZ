package ru.nova.userapi.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nova.userapi.maodel.PhoneContact;

import java.util.List;

public interface PhoneContactRepository extends JpaRepository<PhoneContact, Long> {
    List<PhoneContact> findAllByOwnerUserId(long userId, PageRequest pageRequest);
}
