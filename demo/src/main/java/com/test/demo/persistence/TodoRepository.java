package com.test.demo.persistence;

import com.test.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    List<TodoEntity> findByUserId(String userId);
}
