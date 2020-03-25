package com.forestsoftware.receipe.controller;


import com.forestsoftware.receipe.model.User;
import com.forestsoftware.receipe.model.bookModel;
import com.forestsoftware.receipe.repository.UserRepository;
import com.forestsoftware.receipe.service.UserService;
import com.forestsoftware.receipe.service.bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @Autowired
    UserService userService;
    @Autowired
    bookService bookService;
@Autowired
    UserRepository userRepository;

    @GetMapping("/allUsers")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> fetchAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping ("deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> changeActiveStatus(@PathVariable Long id,@RequestBody User user){
        Map<String, Object>responseMap = new HashMap<>();

        Optional<User> thisUser = userService.findById(id);

        if(!userService.findById(id).isPresent()){
            responseMap.put("error",false);
            responseMap.put("message",String.format("User with id %s is not found", id));
            return new ResponseEntity<>(responseMap,HttpStatus.NOT_FOUND);
        }

        User user1 =new User();

        user1.setActive(user.getIsActive());
        user1.setRoles(thisUser.get().getRoles());
        user1.setEmail(thisUser.get().getEmail());
        user1.setId(thisUser.get().getId());
        user1.setUsername(thisUser.get().getUsername());
        user1.setPassword(thisUser.get().getPassword());
        responseMap.put("error", false);

        userRepository.save(user1);
        if(user.getIsActive() == false) {
            responseMap.put("message", String.format("User with the id %s is set inactive", id));
        }else {
            responseMap.put("message", String.format("User with the id %s is set active", id));
        }
        return new ResponseEntity<>(responseMap,HttpStatus.OK);
    }

    @GetMapping ("/getUser/{id}")
    public ResponseEntity getUserDetails(@PathVariable Long id){
        Map<String, Object> response =new HashMap<>();

        if(!userRepository.findById(id).isPresent()){
            response.put("error",false);
            response.put("message",String.format("User with Id %s is not found",id));
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        Optional user = userRepository.findById(id);
        response.put("error",false);
        response.put("user",user);

        return new ResponseEntity(user, HttpStatus.OK);

    }
}
