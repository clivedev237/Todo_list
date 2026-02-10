package com.takus.toDoApp.controller;

import com.takus.toDoApp.model.dto.TaskRequestDto;
import com.takus.toDoApp.model.dto.TaskResponseDto;
import com.takus.toDoApp.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TaskRequestDto requestDto) {
        try {
            TaskResponseDto savedTask = taskService.save(requestDto);
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            // Cas où l'ID de l'utilisateur fourni dans la tâche n'existe pas
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de la tâche : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            List<TaskResponseDto> tasks = taskService.findAll();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable long id) {
        try {
            TaskResponseDto taskDto = taskService.findById(id);
            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody TaskRequestDto requestDto) {
        try {
            TaskResponseDto updatedTask = taskService.update(id, requestDto);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        try {
            boolean isDeleted = taskService.delete(id);
            if (isDeleted) {
                return new ResponseEntity<>("La tâche avec l'ID " + id + " a été supprimée avec succès.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Erreur lors de la suppression.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}