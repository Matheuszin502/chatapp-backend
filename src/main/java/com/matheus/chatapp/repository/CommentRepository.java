package com.matheus.chatapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.chatapp.domain.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>{
}
