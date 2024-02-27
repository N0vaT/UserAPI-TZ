package ru.nova.userapi.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nova.userapi.exception.UserNotFoundException;
import ru.nova.userapi.maodel.User;
import ru.nova.userapi.maodel.dto.UserDTO;
import ru.nova.userapi.maodel.mapper.UserMapper;
import ru.nova.userapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user-api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                  @RequestParam(required = false, defaultValue = "25") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "ASC") String direction,
                                                  @RequestParam(required = false, defaultValue = "login") String sortByField)
    {
        List<User> users = userService.findAll(pageNumber, pageSize, direction, sortByField);
        return ResponseEntity.ok(users.stream()
                .map(userMapper::toDto)
                .toList());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long userId){
        ResponseEntity<UserDTO> response;
        try {
            User user = userService.findById(userId);
            response = new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }catch (UserNotFoundException e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        User user = userMapper.toUser(userDTO);
        return new ResponseEntity<>(userMapper.toDto(userService.create(user)), HttpStatus.OK);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> patchUpdateUser(@PathVariable long userId,
                                                   @RequestBody UserDTO userDTO){
        ResponseEntity<UserDTO> response;
        try {
            User user = userMapper.toUser(userDTO);
            response = new ResponseEntity<>(userMapper.toDto(userService.patchUpdate(user, userId)), HttpStatus.OK);
        }catch (UserNotFoundException e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> putUpdateUser(@PathVariable long userId,
                                                 @RequestBody UserDTO userDTO){
        ResponseEntity<UserDTO> response;
        try {
            User user = userService.putUpdate(userMapper.toUser(userDTO), userId);
            response = new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
        }catch (UserNotFoundException e){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable long userId){
        try {
            userService.delete(userId);
            return new ResponseEntity<>(userId, HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
