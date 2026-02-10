package com.takus.toDoApp.service.impl;

import com.takus.toDoApp.model.TaskEntity;
import com.takus.toDoApp.model.UserEntity;
import com.takus.toDoApp.model.dto.TaskRequestDto;
import com.takus.toDoApp.model.dto.TaskResponseDto;
import com.takus.toDoApp.model.dto.UserResponseDto;
import com.takus.toDoApp.repository.TaskRepository;
import com.takus.toDoApp.repository.UserRepository;
import com.takus.toDoApp.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskImplementation implements  TaskService{
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskResponseDto save(TaskRequestDto requestDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(requestDto.title());
        taskEntity.setStatus(requestDto.status());

        UserEntity user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        taskEntity.setUser(user);

        TaskEntity savedTask = taskRepository.save(taskEntity);

        String statusTexte = savedTask.isStatus()?"déja" : "pas encore";
        return new TaskResponseDto(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.isStatus(),
                savedTask.getUser().getId()// 1. Pas de virgule ici
        );
    }

    @Override
    public List<TaskResponseDto> findAll() {
        List<TaskEntity> taskEntities= taskRepository.findAll();
        List<TaskResponseDto> reponse = new ArrayList<>();

        for(TaskEntity t:taskEntities){
            Long userId = (t.getUser() != null) ? t.getUser().getId() : null;
             reponse.add(new TaskResponseDto(t.getId(), t.getTitle(), t.isStatus(), userId));
        }
        return reponse;
    }

    @Override
    public TaskResponseDto findById(long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    Long userId = (task.getUser() != null) ? task.getUser().getId() : null;

                    return new TaskResponseDto(
                            task.getId(),
                            task.getTitle(),
                            task.isStatus(),
                            userId
                    );
                })
                .orElseThrow(() -> new EntityNotFoundException("Tâche non trouvée avec l'id : " + id));
    }

    @Override
    public TaskResponseDto update(long id, TaskRequestDto requestDto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Impossible de mettre à jour : la tache " + id + " introuvable"));

        task.setTitle(requestDto.title());
        task.setStatus(requestDto.status());
        UserEntity user = userRepository.findById(requestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'id : " + requestDto.userId()));
        task.setUser(user);
        TaskEntity updatedTask = taskRepository.save(task);
        return new TaskResponseDto(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.isStatus() ,
                updatedTask.getUser().getId()
        );

    }

    @Override
    public boolean delete(long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : la tâche avec l'ID " + id + " n'existe pas.");
        }
        try {

            taskRepository.deleteById(id);
            return !taskRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur est survenue lors de la suppression de la tâche : " + e.getMessage());
        }
    }
}
