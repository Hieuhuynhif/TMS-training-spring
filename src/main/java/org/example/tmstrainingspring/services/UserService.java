package org.example.tmstrainingspring.services;

import org.example.tmstrainingspring.dtos.UserDTO;
import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.exceptions.BadRequestException;
import org.example.tmstrainingspring.exceptions.ForbiddenException;
import org.example.tmstrainingspring.exceptions.NotFoundException;
import org.example.tmstrainingspring.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public List<UserDTO> findByFirstName(String firstName) {

        return userRepository.findByFirstName(firstName).stream().map(UserDTO::new).toList();
    }

    public List<UserDTO> findByLastName(String lastName) {

        return userRepository.findByLastName(lastName).stream().map(UserDTO::new).toList();
    }

    public UserDTO findById(int id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    public UserModel findByUsername(String username) {
        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }
        return user;
    }

    public UserDTO add(UserModel user) {
        try {
            UserModel savedUser = userRepository.findByUsername(user.getUsername());

            if (savedUser != null) {
                throw new ForbiddenException("User already exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return new UserDTO(userRepository.save(user));

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    public UserDTO update(int id, UserModel user) {
        UserModel savedUser = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        if (user.getFirstName() != null) {
            savedUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            savedUser.setLastName(user.getLastName());
        }

        if (user.getAge() != 0) {
            savedUser.setAge(user.getAge());
        }


        userRepository.save(savedUser);
        return new UserDTO(savedUser);
    }

    public String DeleteAll() {
        userRepository.deleteAll();
        return "Deleted All Users successfully";
    }
}
