package org.example.tmstrainingspring.services;

import org.example.tmstrainingspring.entities.User;
import org.example.tmstrainingspring.exceptions.BadRequestException;
import org.example.tmstrainingspring.exceptions.ForbiddenException;
import org.example.tmstrainingspring.exceptions.NotFoundException;
import org.example.tmstrainingspring.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByAge(int age) {
        return userRepository.findByAge(age);
    }

    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public List<User> findAdultUser(int age) {
        return userRepository.findAdultUser(age);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User add(User user) {
        try {
            User savedUser = userRepository.findByFirstNameAndLastNameAndAge(user.getFirstName(), user.getLastName(), user.getAge());

            if (savedUser != null) {
                throw new ForbiddenException("User already exists");
            }

            return userRepository.save(user);

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    public User update(int id, User user) {
        User savedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

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
