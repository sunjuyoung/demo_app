package com.test.demo.controller;

import com.test.demo.dto.ResponseDTO;
import com.test.demo.dto.TodoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {


    @GetMapping
    public String testController(@RequestBody(required = false) TodoDTO todoDTO){
        System.out.println(todoDTO.getId());
        System.out.println(todoDTO.getTitle());
        return "hello";
    }

    @GetMapping("/testResponse")
    public ResponseDTO<String> testController1(){
        List<String> list = new ArrayList<>();
        list.add("HELLO WORLD");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).error(new IllegalArgumentException().getMessage()).build();
        return responseDTO;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<ResponseDTO<String>> testController2(){
        List<String> list = new ArrayList<>();
        list.add("HELLO WORLD");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).error(new IllegalArgumentException().getMessage()).build();
        return ResponseEntity.ok().body(responseDTO);
    }
}
