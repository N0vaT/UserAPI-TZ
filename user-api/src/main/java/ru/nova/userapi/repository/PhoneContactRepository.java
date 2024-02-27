package ru.nova.userapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nova.userapi.maodel.PhoneContact;

public interface PhoneContactRepository extends JpaRepository<PhoneContact, Long> {
}
