package org.example.tmstrainingspring.services;

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

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public List<UserModel> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<UserModel> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public UserModel findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserModel add(UserModel user) {
        try {
            UserModel savedUser = userRepository.findByUsername(user.getUsername());

            if (savedUser != null) {
                throw new ForbiddenException("User already exists");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    public UserModel update(int id, UserModel user) {
        UserModel savedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

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
        return savedUser;
    }

    public String DeleteAll() {
        userRepository.deleteAll();
        return "Deleted All Users successfully";
    }
}
