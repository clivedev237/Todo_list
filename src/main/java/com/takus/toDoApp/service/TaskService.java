package com.takus.toDoApp.service;

import com.takus.toDoApp.model.dto.TaskRequestDto;
import com.takus.toDoApp.model.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {

	TaskResponseDto save(TaskRequestDto requestDto);
	
	List<TaskResponseDto> findAll();
	
	TaskResponseDto findById(long id);
	
	TaskResponseDto update(long id, TaskRequestDto requestDto);
	
	boolean delete(long id);
}
