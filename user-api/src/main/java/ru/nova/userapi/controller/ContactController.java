package ru.nova.userapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-api/v1/users/{userId}/contacts")
@RequiredArgsConstructor
public class ContactController {
}
