package com.sop.api.bo.persistence;

import com.sop.api.bo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
  List<TodoEntity> findByUserId(String userId);
}
