package com.takus.toDoApp.service.impl;
import com.takus.toDoApp.model.UserEntity;
import com.takus.toDoApp.model.dto.UserRequestDto;
import com.takus.toDoApp.model.dto.UserResponseDto;
import com.takus.toDoApp.repository.UserRepository;
import com.takus.toDoApp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImplementation implements UserService {
   private final UserRepository userRepository;

    @Override
    public UserResponseDto save(UserRequestDto requestDto) {
        if(requestDto.name()==null  || requestDto.name().trim().isEmpty()){
            throw new IllegalArgumentException("Le nom de l'utilisateur ne peut pas être vide !");
        }
        if(userRepository.existsByName(requestDto.name())){
             throw new RuntimeException("Erreur : L'utilisateur '" + requestDto.name() + "' existe déjà.");
        }
        UserEntity userEntity= new UserEntity();
        userEntity.setName(requestDto.name());

        UserEntity userEnregistre = userRepository.save(userEntity);
        return new UserResponseDto(userEnregistre.getId(),userEnregistre.getName());
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<UserEntity> users = userRepository.findAll();
        List<UserResponseDto> reponse = new ArrayList<>();

        for (UserEntity u : users) {
            reponse.add(new UserResponseDto(u.getId(), u.getName()));
        }

        return reponse;
    }

    @Override
    public UserResponseDto findById(long id) {
        return userRepository.findById(id)
                .map(user -> new UserResponseDto(user.getId(), user.getName()))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
    }

    @Override
    public UserResponseDto update(long id, UserRequestDto requestDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Impossible de mettre à jour : Utilisateur " + id + " introuvable"));

         user.setName(requestDto.name());
        UserEntity updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser.getId(), updatedUser.getName());
    }

    @Override
    public boolean delete(long id) {

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : l'utilisateur avec l'ID " + id + " n'existe pas.");
        }

        try {
            userRepository.deleteById(id);

            return !userRepository.existsById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur technique lors de la suppression : " + e.getMessage());
        }
    }
}
