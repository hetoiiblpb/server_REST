package ru.hetoiiblpb.server_REST.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hetoiiblpb.server_REST.dto.UserDto;
import ru.hetoiiblpb.server_REST.model.User;
import ru.hetoiiblpb.server_REST.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/restapi/v1/admin/")
public class AdminRestControllerV1 {
    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers (){                                        //Получить всех DTO
        List<User> userList = userService.getAll();
        if (userList == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UserDto> userDtoList = userList.stream().map(UserDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
    @GetMapping("user/{id}")                                                                    //Получить DTO по Id
    public  ResponseEntity<UserDto> getUserById(@PathVariable (name = "id") Long id){
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = new UserDto(user);
        return new ResponseEntity<UserDto>(result, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")                                                                 //Удалить по Id
    public ResponseEntity deleteUserById(@PathVariable (name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userService.delete(id);
        return new ResponseEntity (HttpStatus.OK);
    }

    @PutMapping("user/")
    public ResponseEntity updateUser(@RequestBody UserDto reqUser) {                           //обновить по Юзеру
        User user = userService.findById(reqUser.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
         User user1 = userService.toUser(reqUser);
        if (user1.getPassword()==null) {user1.setPassword(user.getPassword());}
        userService.update(user1);
        return new ResponseEntity (HttpStatus.OK);
    }

    @PostMapping("user/")
    public ResponseEntity addUser(@RequestBody UserDto userDto){
        if (userDto == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        userService.register(userService.toUser(userDto));
        return new ResponseEntity(HttpStatus.OK);
    }



}
