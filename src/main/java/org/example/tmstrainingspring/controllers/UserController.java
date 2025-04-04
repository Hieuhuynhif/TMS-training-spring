package org.example.tmstrainingspring.controllers;

import org.example.tmstrainingspring.entities.User;
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
    public List<User> findAll(@RequestParam(value = "firstName", required = false) String firstName,
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
    public User findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping("")
    public User add(@RequestBody User user) {
        return userService.add(user);
    }

    @PutMapping("{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("")
    public String deleteAll() {
        return userService.DeleteAll();
    }

}
