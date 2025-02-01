package com.backendProject.blogger.repository;

import com.backendProject.blogger.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
