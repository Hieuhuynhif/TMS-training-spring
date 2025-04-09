package org.example.tmstrainingspring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 255)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 16)
    private String username;

    @Size(max = 50)
    private String firstName;


    @Size(max = 50)
    private String lastName;

    @Max(200)
    @Min(18)
    private int age;
}
