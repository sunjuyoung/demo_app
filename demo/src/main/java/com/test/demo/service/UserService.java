package com.test.demo.service;

import com.test.demo.model.UserEntity;
import com.test.demo.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserEntity create(UserEntity userEntity){
        if(userEntity ==null || userEntity.getEmail() ==null){
            throw  new RuntimeException("UserEntity null");
        }
        String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("email already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(String email,String password){
        return userRepository.findByEmailAndPassword(email,password);
    }
}
