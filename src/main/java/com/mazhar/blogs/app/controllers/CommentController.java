package com.mazhar.blogs.app.controllers;

import com.mazhar.blogs.app.payloads.ApiResponse;
import com.mazhar.blogs.app.payloads.CommentDto;
import com.mazhar.blogs.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/create/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Integer postId)
    {
     CommentDto commentDto1 = this.commentService.createComment(commentDto, postId);
     return new ResponseEntity<>(commentDto1, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse
                ("Comment is deleted successfully !!!",true),HttpStatus.OK);
    }
}
