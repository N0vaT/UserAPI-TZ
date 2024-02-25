package ru.nova.userapi.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.maodel.dto.UserDTO;
import ru.nova.userapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user-api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                  @RequestParam(required = false, defaultValue = "25") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                  @RequestParam(required = false, defaultValue = "login") String sortByField)
    {
        List<User> users = userService.findAll(pageNumber, pageSize, direction, sortByField);
        return ResponseEntity.ok(users.stream()
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList());
    }
}
