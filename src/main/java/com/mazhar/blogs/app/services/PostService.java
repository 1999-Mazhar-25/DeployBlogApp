package com.mazhar.blogs.app.services;

import com.mazhar.blogs.app.payloads.PostDto;
import com.mazhar.blogs.app.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create-post
    PostDto createPost(PostDto postdto,Integer userId,Integer categoryId);

    //update-post

    PostDto updatePost(PostDto postdto, Integer postId);

    //delete-post
    void deletePost(Integer postId);

    //get-all post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    //get-post by id
    PostDto getPostById(Integer postId);

    //get-post-by CATEGORY
    List<PostDto> getPostByCategory(Integer categoryId);

    //get-post-by USER
    List<PostDto> getPostByUser(Integer userId);

    //search post
    List<PostDto> searchPost(String keyword);



}
