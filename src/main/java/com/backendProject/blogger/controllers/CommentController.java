package com.backendProject.blogger.controllers;

import com.backendProject.blogger.payloads.ApiResponse;
import com.backendProject.blogger.payloads.CommentDto;
import com.backendProject.blogger.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,
                                                    @PathVariable Integer postId,
                                                    @PathVariable Integer userId){
        CommentDto createdComment = this.commentService.createComment(comment, postId, userId);

        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ApiResponse deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ApiResponse("Comment is deleted successfully", true);
    }
}
