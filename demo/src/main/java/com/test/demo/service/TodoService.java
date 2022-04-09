package com.test.demo.service;

import com.test.demo.model.TodoEntity;
import com.test.demo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

   public List<TodoEntity> create(TodoEntity entity){
       validate(entity);
       todoRepository.save(entity);
       return todoRepository.findByUserId(entity.getUserId());
   }

   @Transactional(readOnly = true)
   public List<TodoEntity> searchById(String userId){
        return todoRepository.findByUserId(userId);
   }

   public List<TodoEntity> update(TodoEntity todoEntity){
      // validate(todoEntity);
       Optional<TodoEntity> byId = todoRepository.findById(todoEntity.getId());
       byId.ifPresent(todo->{
           todo.setTitle(todoEntity.getTitle());
           todo.setDone(todoEntity.isDone());
           todoRepository.save(todo);
       });
       return searchById(byId.get().getUserId());
   }

   public List<TodoEntity> delete(TodoEntity todoEntity){
       validate(todoEntity);
       try{
           todoRepository.delete(todoEntity);
       }catch (Exception e){
            log.error("error delete entity",todoEntity.getId(),e);
            throw new RuntimeException("error delete entity"+todoEntity.getId());
       }
       return searchById(todoEntity.getUserId());

   }


    private void validate(TodoEntity entity) {
        if(entity == null){
             log.warn("entity cannot be null");
             throw new RuntimeException("entity null");
        }
        if(entity.getUserId() == null){
            log.warn("user cannot be null");
            throw new RuntimeException("user null");
        }
    }
}
