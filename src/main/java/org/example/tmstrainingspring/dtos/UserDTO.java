package org.example.tmstrainingspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tmstrainingspring.entities.UserModel;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private int age;

    public UserDTO(UserModel user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
    }

    public UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }
}