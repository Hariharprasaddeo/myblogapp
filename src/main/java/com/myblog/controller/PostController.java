package com.myblog.controller;

import com.myblog.dto.PostDto;
import com.myblog.dto.PostResponse;
import com.myblog.entity.Post;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    // http://localhost:8080/api/posts/

//------------------Validation----@Valid---------04/10/23-----------------------------------------------------------------------

    // BindingResult ---> Binds the error to the postman
    // ResponseEntity<?> ---> Dynamic Generics return type--------------------------------------------------------------------------
    @PreAuthorize("hasRole('ADMIN')")  // can only be accessed by admin with "ADMIN" role
    @PostMapping
    // ResponseEntity<?> or ResponseEntity<Object>
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
         PostDto dto = postService.createPost(postDto);
         return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //  http://localhost:8080/api/posts/{postId}  ( http://localhost:8080/api/posts/1 )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deleteById(@PathVariable Long postId){

        postService.deleteById(postId);
        return new ResponseEntity<>("Post is deleted successfully", HttpStatus.OK);
    }

//    @DeleteMapping("{postId}")
//    public ResponseEntity<String> deleteById(@PathVariable Long postId){
//        Optional<Post> id = postRepository.findById(postId);
//        if (id.isPresent()) {
//            postService.deleteById(postId);
//            return new ResponseEntity<>("Post is deleted successfully", HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>("Given Id doesn't Exist : "+postId, HttpStatus.OK);
//        }
//    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Long postId){

        PostDto dto = postService.getPostByPostId(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    // http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=desc

//------------------------------Pagination----------------------------------------------------------------------------
//    @GetMapping
//    public List<PostDto> getAllPost(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
//            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
//            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
//    ){
//        List<PostDto> dto = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
//        return dto;
//    }
 //--------------------------------  PostResponse  getAllPost()---------------------------------------------------------------------------
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PostResponse postResponse = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }

}
