package com.test.demo.controller;

import com.test.demo.dto.ResponseDTO;
import com.test.demo.dto.UserDTO;
import com.test.demo.model.UserEntity;
import com.test.demo.security.TokenProvider;
import com.test.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){

        try{
            UserEntity userEntity = getUserEntity(userDTO);
            UserEntity saveUser = userService.create(userEntity);

            UserDTO responseDTO = getUserDTO(saveUser);

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());

        if(user != null){
            //토큰생성
            String token = tokenProvider.create(user);
            UserDTO responseDto = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseDto);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login faild").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }










    private UserEntity getUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                        .email(userDTO.getEmail())
                        .password(userDTO.getPassword())
                        .username(userDTO.getUsername())
                        .build();
    }

    private UserDTO getUserDTO(UserEntity saveUser) {
        return UserDTO.builder().email(saveUser.getEmail())
                        .id(saveUser.getId())
                        .password(saveUser.getPassword())
                        .username(saveUser.getUsername())
                        .build();
    }


}
