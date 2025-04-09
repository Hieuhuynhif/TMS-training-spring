package org.example.tmstrainingspring.controllers;

import org.example.tmstrainingspring.dtos.UserDTO;
import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.exceptions.NotFoundException;
import org.example.tmstrainingspring.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDTO>
    findAll(@RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName) {

        if (firstName != null) {
            return userService.findByFirstName(firstName);
        }
        if (lastName != null) {
            return userService.findByLastName(lastName);
        }

        return userService.findAll();
    }

    @GetMapping("{id}")
    public UserDTO findById(@PathVariable int id) {

        UserDTO user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

    @PostMapping("")
    public UserDTO add(@RequestBody UserModel user) {
        return userService.add(user);
    }

    @PutMapping("{id}")
    public UserDTO update(@PathVariable int id, @RequestBody UserModel user) {
        return userService.update(id, user);
    }

    @DeleteMapping("")
    public String deleteAll() {
        return userService.DeleteAll();
    }

}
