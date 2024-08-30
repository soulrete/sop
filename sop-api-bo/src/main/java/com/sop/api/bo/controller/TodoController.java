package com.sop.api.bo.controller;

import com.sop.api.bo.dto.ResponseDTO;
import com.sop.api.bo.dto.TestRequestBodyDTO;
import com.sop.api.bo.dto.TodoDTO;
import com.sop.api.bo.model.TodoEntity;
import com.sop.api.bo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

  @Autowired
  private TodoService service;

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
    try {
      String temporaryUserId = "temp-user";
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity.setId(null);
      entity.setUserId(temporaryUserId);
      List<TodoEntity> entities = service.create(entity);
      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
  }

  @GetMapping
  public ResponseEntity<?> retrieveTodoList() {
    String temporaryUserId = "temp-user";

    List<TodoEntity> entities = service.retrieve(temporaryUserId);
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

    return ResponseEntity.ok().body(response);
  }

  @PutMapping
  public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
    String temporaryUserId = "temp-user";
    TodoEntity entity = TodoDTO.toEntity(dto);
    entity.setUserId(temporaryUserId);
    List<TodoEntity> entities = service.update(entity);
    List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

    ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
    try {
      String temporaryUserId = "temp-user";
      TodoEntity entity = TodoDTO.toEntity(dto);
      entity.setUserId(temporaryUserId);
      List<TodoEntity> entities = service.delete(entity);

      List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }
  }
}
