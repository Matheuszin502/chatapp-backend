package com.matheus.chatapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.chatapp.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
