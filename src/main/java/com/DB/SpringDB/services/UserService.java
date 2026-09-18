package com.DB.SpringDB.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.DB.SpringDB.dto.CreateUserDto;
import com.DB.SpringDB.dto.UserDto;
import com.DB.SpringDB.entities.User;
import com.DB.SpringDB.exception.UserNotFoundException;
import com.DB.SpringDB.repositories.UserRepository;

import jakarta.transaction.Transactional;


@Service 
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());

        User savedUser = userRepository.save(user);

        return  map(savedUser);
    }

    public List<UserDto> fetchAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).
            orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        return map(user);
    }

    @Transactional
    public UserDto update(CreateUserDto updateUserDto) {
        User user = getLoggedInUser();
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());

        return  map(user);
        
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional 
    public UserDto patchUser(CreateUserDto patchUserDto) {
        User user = getLoggedInUser();
        if(patchUserDto.getName() != null) {
            user.setName(patchUserDto.getName());
        }

        if(patchUserDto.getEmail() != null){
            user.setEmail(patchUserDto.getEmail());
        }
        
        return map(user);
    }

    public List<UserDto> fetchUsersPaginated(int page,int size,String direction,String sortBy) {
        Sort sort;
        sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size,sort);
        Page<User> usersPage = userRepository.findAll(pageable);
        
        List<UserDto> userDtos = new ArrayList<>();

        usersPage.forEach(user -> 
        userDtos.add(map(user)));

        return userDtos;
    }

    public User getLoggedInUser(){
        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName(); 
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    public UserDto getCurrentUser() {
        User user = getLoggedInUser();
        return map(user);
    }

    private UserDto map(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }    
}
