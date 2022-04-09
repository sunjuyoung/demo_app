package com.test.demo.controller;

import com.test.demo.dto.ResponseDTO;
import com.test.demo.dto.TodoDTO;
import com.test.demo.model.TodoEntity;
import com.test.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO, @AuthenticationPrincipal String userId){
        try{
            //String temporayUserId = "testUser";
            TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
            todoEntity.setUserId(userId);
            List<TodoEntity> todoEntityList = todoService.create(todoEntity);

            List<TodoDTO> dtos = todoEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @GetMapping
    public ResponseEntity<?> getList(@AuthenticationPrincipal String userId){
        List<TodoEntity> todoEntityList = todoService.searchById(userId);
        List<TodoDTO> dtos = todoEntityList.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TodoDTO todoDTO,@AuthenticationPrincipal String userId){
        TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
        todoEntity.setUserId(userId);
        List<TodoEntity> update = todoService.update(todoEntity);

        List<TodoDTO> dtos = update.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDTO);

    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody TodoDTO todoDTO,@AuthenticationPrincipal String userId){
        TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);
        todoEntity.setUserId(userId);

        List<TodoEntity> delete = todoService.delete(todoEntity);

        List<TodoDTO> dtos = delete.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(responseDTO);

    }



}
