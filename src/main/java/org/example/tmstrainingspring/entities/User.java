package org.example.tmstrainingspring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Max(200)
    @Min(18)
    private int age;
}
