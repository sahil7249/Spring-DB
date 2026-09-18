package com.DB.SpringDB.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.UserDto;
import com.DB.SpringDB.services.UserService;


@RestController 
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUser());
    }

    @GetMapping    
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }


    // ADMIN - pagination and sorting
    @GetMapping("/paginated")
    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam(defaultValue="asc") String direction,
                                                           @RequestParam(defaultValue="name") String sortBy){
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUsersPaginated(page,size,direction,sortBy));
    }   


    // ADMIN - get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // USER - update user 
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody CreateUserDto updateUserDto) {
        UserDto updatedUser = userService.update(updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }
    
    // USER - patch user
    @PatchMapping("/me")
    public ResponseEntity<UserDto> patchUser(@RequestBody CreateUserDto patchUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(patchUserDto));
    }

    // ADMIN - delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    

}
