package com.github.mickeydluffy.repository;

import com.github.mickeydluffy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

}
