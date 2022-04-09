package com.test.demo.dto;

import com.test.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity todoEntity){
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
    }

    public static TodoEntity toEntity(TodoDTO todoDTO){
        return TodoEntity.builder()
                .done(todoDTO.done)
                .id(todoDTO.id)
                .title(todoDTO.title)
                .build();
    }
}
