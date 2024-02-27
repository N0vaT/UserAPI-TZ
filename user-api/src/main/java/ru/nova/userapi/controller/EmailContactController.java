package ru.nova.userapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nova.userapi.exception.EmailContactNotFoundException;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.EmailContact;
import ru.nova.userapi.maodel.dto.EmailContactDTO;
import ru.nova.userapi.maodel.mapper.EmailContactMapper;
import ru.nova.userapi.service.EmailContactService;

import java.util.List;

@RestController
@RequestMapping("/user-api/v1/users/{userId}/emails")
@RequiredArgsConstructor
public class EmailContactController {
    private final EmailContactService emailContactService;
    private final EmailContactMapper emailContactMapper;

    @GetMapping
    public ResponseEntity<List<EmailContactDTO>> getEmailContacts(@PathVariable long userId,
                                                                  @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                                  @RequestParam(required = false, defaultValue = "25") int pageSize,
                                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                                  @RequestParam(required = false, defaultValue = "email") String sortByField) {
        ResponseEntity<List<EmailContactDTO>> response;
        try {
            List<EmailContact> contacts = emailContactService.findAll(userId, pageNumber, pageSize, direction, sortByField);
            response = ResponseEntity.ok(contacts.stream()
                    .map(emailContactMapper::toDto)
                    .toList());
        } catch (UserNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<EmailContactDTO> getEmailContact(@PathVariable long userId,
                                                           @PathVariable long contactId) {
        ResponseEntity<EmailContactDTO> response;
        try {
            EmailContact contact = emailContactService.findById(userId, contactId);
            response = new ResponseEntity<>(emailContactMapper.toDto(contact), HttpStatus.OK);
        } catch (UserNotFoundException | EmailContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<EmailContactDTO> addEmailContact(@PathVariable long userId,
                                                           @RequestBody EmailContactDTO contactDTO) {
        ResponseEntity<EmailContactDTO> response;
        try {
            EmailContact contact = emailContactMapper.toEmailContact(contactDTO);
            response = new ResponseEntity<>(emailContactMapper.toDto(emailContactService.create(contact, userId)), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PatchMapping("/{contactId}")
    public ResponseEntity<EmailContactDTO> patchUpdateEmailContact(@PathVariable long userId,
                                                                   @PathVariable long contactId,
                                                                   @RequestBody EmailContactDTO contactDTO) {
        ResponseEntity<EmailContactDTO> response;
        try {
            EmailContact contact = emailContactMapper.toEmailContact(contactDTO);
            response = new ResponseEntity<>(emailContactMapper.toDto(emailContactService.patchUpdate(contact, userId, contactId)), HttpStatus.OK);
        } catch (UserNotFoundException | EmailContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<EmailContactDTO> putUpdateEmailContact(@PathVariable long userId,
                                                                 @PathVariable long contactId,
                                                                 @RequestBody EmailContactDTO contactDTO) {
        ResponseEntity<EmailContactDTO> response;
        try {
            EmailContact contact = emailContactService.putUpdate(emailContactMapper.toEmailContact(contactDTO), userId, contactId);
            response = new ResponseEntity<>(emailContactMapper.toDto(contact), HttpStatus.OK);
        } catch (UserNotFoundException | EmailContactNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Long> deleteEmailContact(@PathVariable long userId,
                                                   @PathVariable long contactId) {
        try {
            emailContactService.delete(userId, contactId);
            return new ResponseEntity<>(contactId, HttpStatus.OK);
        } catch (UserNotFoundException | EmailContactNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
