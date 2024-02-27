package ru.nova.userapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nova.userapi.exception.PhoneContactNotFoundException;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.PhoneContact;
import ru.nova.userapi.maodel.dto.EmailContactDTO;
import ru.nova.userapi.maodel.dto.PhoneContactDTO;
import ru.nova.userapi.maodel.mapper.PhoneContactMapper;
import ru.nova.userapi.service.PhoneContactService;

import java.util.List;

@RestController
@RequestMapping("/user-api/v1/users/{userId}/phones")
@RequiredArgsConstructor
public class PhoneContactController {
    private final PhoneContactService phoneContactService;
    private final PhoneContactMapper phoneContactMapper;

    @GetMapping
    public ResponseEntity<List<PhoneContactDTO>> getPhoneContacts(@PathVariable long userId,
                                                                  @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                  @RequestParam(required = false, defaultValue = "25") int pageSize,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                  @RequestParam(required = false, defaultValue = "phone") String sortByField) {
        ResponseEntity<List<PhoneContactDTO>> response;
        try {
            List<PhoneContact> contacts = phoneContactService.findAll(userId, pageNumber, pageSize, direction, sortByField);
            response = ResponseEntity.ok(contacts.stream()
                    .map(phoneContactMapper::toDto)
                    .toList());
        } catch (UserNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<PhoneContactDTO> getPhoneContact(@PathVariable long userId,
                                                           @PathVariable long contactId) {
        ResponseEntity<PhoneContactDTO> response;
        try {
            PhoneContact contact = phoneContactService.findById(userId, contactId);
            response = new ResponseEntity<>(phoneContactMapper.toDto(contact), HttpStatus.OK);
        } catch (UserNotFoundException | PhoneContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<PhoneContactDTO> addPhoneContact(@PathVariable long userId,
                                                           @RequestBody PhoneContactDTO contactDTO) {
        ResponseEntity<PhoneContactDTO> response;
        try {
            PhoneContact contact = phoneContactMapper.toPhoneContact(contactDTO);
            response = new ResponseEntity<>(phoneContactMapper.toDto(phoneContactService.create(contact, userId)), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PatchMapping("/{contactId}")
    public ResponseEntity<PhoneContactDTO> patchUpdatePhoneContact(@PathVariable long userId,
                                                                   @PathVariable long contactId,
                                                                   @RequestBody PhoneContactDTO contactDTO) {
        ResponseEntity<PhoneContactDTO> response;
        try {
            PhoneContact contact = phoneContactMapper.toPhoneContact(contactDTO);
            response = new ResponseEntity<>(phoneContactMapper.toDto(phoneContactService.patchUpdate(contact, userId, contactId)), HttpStatus.OK);
        } catch (UserNotFoundException | PhoneContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<PhoneContactDTO> putUpdatePhoneContact(@PathVariable long userId,
                                                                 @PathVariable long contactId,
                                                                 @RequestBody PhoneContactDTO contactDTO) {
        ResponseEntity<PhoneContactDTO> response;
        try {
            PhoneContact contact = phoneContactService.putUpdate(phoneContactMapper.toPhoneContact(contactDTO), userId, contactId);
            response = new ResponseEntity<>(phoneContactMapper.toDto(contact), HttpStatus.OK);
        } catch (UserNotFoundException | PhoneContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Long> deletePhoneContact(@PathVariable long userId,
                                                   @PathVariable long contactId) {
        try {
            phoneContactService.delete(userId, contactId);
            return new ResponseEntity<>(contactId, HttpStatus.OK);
        } catch (UserNotFoundException | PhoneContactNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
