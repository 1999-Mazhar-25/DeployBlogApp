package com.mazhar.blogs.app.services.impl;


import com.mazhar.blogs.app.entities.Comment;
import com.mazhar.blogs.app.entities.Post;
import com.mazhar.blogs.app.exceptions.ResourceNotFoundException;
import com.mazhar.blogs.app.payloads.CommentDto;
import com.mazhar.blogs.app.repositories.CommentRepo;
import com.mazhar.blogs.app.repositories.PostRepo;
import com.mazhar.blogs.app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("post","post Id",postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment updateComment = this.commentRepo.save(comment);

        return this.modelMapper.map(updateComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

     Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("comment",
                        "comment Id",commentId));
     this.commentRepo.delete(comment);
    }
}
