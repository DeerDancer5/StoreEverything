package com.projekt.projekt.Repositories;

import com.projekt.projekt.Validation.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Long> {
    Optional <User> findByUsername(String name);
}
