package com.takus.toDoApp.service;

import com.takus.toDoApp.model.dto.UserRequestDto;
import com.takus.toDoApp.model.dto.UserResponseDto;

import java.util.List;

public interface UserService {
	
	UserResponseDto save(UserRequestDto requestDto);
	
	List<UserResponseDto> findAll();
	
	UserResponseDto findById(long id);
	
	UserResponseDto update(long id, UserRequestDto requestDto);
	
	boolean delete(long id);
}
