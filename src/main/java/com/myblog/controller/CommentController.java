package com.myblog.controller;

import com.myblog.dto.CommentDto;
import com.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/comments/{postId}
    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> saveComments(@RequestBody CommentDto dto, @PathVariable long postId){
        CommentDto dto1= commentService.saveComment(dto, postId);
        return new ResponseEntity<>(dto1, HttpStatus.OK);
    }
    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteByCommentId(@PathVariable long commentId){
        commentService.deleteByCommentId(commentId);
        return new ResponseEntity<>("comment is deleted", HttpStatus.OK);
    }
    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable long commentId){
        CommentDto Dto=commentService.updateByCommentId(commentDto, commentId);
        return new ResponseEntity<>(Dto, HttpStatus.OK);
    }

}
