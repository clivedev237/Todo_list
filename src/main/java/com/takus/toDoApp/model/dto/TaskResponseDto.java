package com.takus.toDoApp.model.dto;

public record TaskResponseDto( long id, String title, boolean status,Long userId) {

}
