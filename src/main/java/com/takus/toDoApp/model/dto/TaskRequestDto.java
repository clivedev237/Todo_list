package com.takus.toDoApp.model.dto;

public record TaskRequestDto(String title, boolean status, Long userId) {

}
