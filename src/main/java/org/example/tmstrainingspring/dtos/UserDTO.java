package org.example.tmstrainingspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.enums.Role;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private Role role;
    private String firstName;
    private String lastName;
    private int age;

    public UserDTO(UserModel user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.role = user.getRole();
    }

}