package com.forestsoftware.receipe.service;

import com.forestsoftware.receipe.model.User;
import com.forestsoftware.receipe.model.bookModel;
import com.forestsoftware.receipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
//    public Optional<User>save(User user){
//        return userRepository.save(user);
//    }
}
