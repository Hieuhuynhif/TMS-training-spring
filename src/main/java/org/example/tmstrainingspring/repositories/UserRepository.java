package org.example.tmstrainingspring.repositories;

import org.example.tmstrainingspring.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    @Query
    UserModel findByUsername(String firstName);

    @Query
    List<UserModel> findByFirstName(String firstName);

    @Query
    List<UserModel> findByLastName(String lastName);

}
