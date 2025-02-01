package com.backendProject.blogger.services.impl;

import com.backendProject.blogger.entities.Comment;
import com.backendProject.blogger.entities.Post;
import com.backendProject.blogger.entities.User;
import com.backendProject.blogger.exceptions.ResourceNotFoundException;
import com.backendProject.blogger.payloads.CommentDto;
import com.backendProject.blogger.repository.CommentRepo;
import com.backendProject.blogger.repository.PostRepo;
import com.backendProject.blogger.repository.UserRepo;
import com.backendProject.blogger.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post-id", postId));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user-id", userId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comm = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment-id", commentId));
        this.commentRepo.delete(comm);

    }
}
