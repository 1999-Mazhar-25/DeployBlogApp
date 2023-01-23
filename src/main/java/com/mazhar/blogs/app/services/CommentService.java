package com.mazhar.blogs.app.services;

import com.mazhar.blogs.app.payloads.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto comment, Integer postId);
    public void deleteComment(Integer commentId);
}
