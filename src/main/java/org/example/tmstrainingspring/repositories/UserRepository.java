package org.example.tmstrainingspring.repositories;

import org.example.tmstrainingspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query
    List<User> findByFirstName(String firstName);

    @Query
    List<User> findByLastName(String lastName);

    @Query
    List<User> findByAge(int age);

    @Query
    User findByFirstNameAndLastNameAndAge(String firstName, String lastName, int age);

    @Query("select u from User u  where u.age > :age")
    List<User> findAdultUser(@Param("age") int age);
}
