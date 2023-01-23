package com.mazhar.blogs.app.controllers;

import com.mazhar.blogs.app.config.AppConstants;
import com.mazhar.blogs.app.payloads.PostDto;
import com.mazhar.blogs.app.payloads.PostResponse;
import com.mazhar.blogs.app.services.FileService;
import com.mazhar.blogs.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    private ResponseEntity<PostDto> createPost(@PathVariable Integer userId,
                                               @PathVariable Integer categoryId,
                                               @RequestBody PostDto post){
        System.out.println("in controller");
        PostDto postDto = this.postService.createPost(post,userId,categoryId);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNumber",required = false,
                    defaultValue= AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value="pageSize",required = false,
                    defaultValue=AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value ="sortBy", required = false,
                    defaultValue =AppConstants.SORT_POST_BY) String sortBy,
            @RequestParam(value ="sortDir", required = false,
                    defaultValue =AppConstants.SORT_DIR) String sortDir
    ){
        final var allPost = postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }


    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDto = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }


    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        List<PostDto> postDto = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }


    @PutMapping("/update/{postId}/post")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable("postId") Integer postId){
       PostDto postDto1 = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}/post")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return ResponseEntity.ok("Post Deleted Successfully !!!!!!");
    }

    @GetMapping("/post/search/{key}")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable("key") String keyword){

        List<PostDto> result = this.postService.searchPost(keyword);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable("postId")Integer postId
            ) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String filepath = this.fileService.uploadFile(path,image);
        postDto.setImageName(filepath);
       PostDto updatedPost = this.postService.updatePost(postDto,postId);
       return new ResponseEntity<>(updatedPost,HttpStatus.OK);

    }

    @GetMapping(value = "/image/download/{image}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String image, HttpServletResponse response)
            throws IOException {
        InputStream resource = this.fileService.getResources(path,image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
