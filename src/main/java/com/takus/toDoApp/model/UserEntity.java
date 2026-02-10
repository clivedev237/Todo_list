package com.takus.toDoApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserEntity {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true,nullable = false)
	private String name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<TaskEntity> tasks =new ArrayList<>();
}
