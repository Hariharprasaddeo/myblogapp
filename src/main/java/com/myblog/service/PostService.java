package com.myblog.service;

import com.myblog.dto.PostDto;
import com.myblog.dto.PostResponse;

import java.util.List;

public interface PostService {

     PostDto createPost(PostDto postDto);

     void deleteById(Long postId);

     PostDto getPostByPostId(Long postId);

   //List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

     PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
