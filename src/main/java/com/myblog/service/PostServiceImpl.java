package com.myblog.service;

import com.myblog.dto.PostDto;
import com.myblog.dto.PostResponse;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    private ModelMapper modelMapper;  // ModelMapper is used copying data one object to another object.
                                      // ModelMapper is a Java library that simplifies the process of mapping(copying data) one object to another object.
                                      // It reduced the boiler-plate code
    public PostServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDto createPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatePost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(updatePost.getId());
        dto.setTitle(updatePost.getTitle());
        dto.setContent(updatePost.getContent());
        dto.setDescription(updatePost.getDescription());
        return dto;
    }

    @Override
    public void deleteById(Long postId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post is not found with id: "+postId)
        );
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto getPostByPostId(Long postId) {
            Post post=postRepository.findById(postId).orElseThrow(
                    ()-> new ResourceNotFound("Post is not found with id: "+postId)
            );
        return mapToDto(post);
    }

    public PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
//
//       PostDto dto = new PostDto();
//        // using getters and setters
//       dto.setId(post.getId());
//       dto.setTitle(post.getTitle());
//       dto.setContent(post.getContent());
//       dto.setDescription(post.getDescription());
       return dto;

//        // using constructor
//       PostDto dto = new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
//        return dto;
   }

//-----------------------------------Pagination----------------------------------------------------------------------------------
 //   @Override
//    public List<PostDto> getAllPost(int pageNo, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNo,pageSize);
    //-----------------SortBy any particular type-------------------------------------------------------------
//    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy) {
//        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy);

 //   --------------sortBy any particular type with ASC or DSC Direction----------------------------------

//    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
//        Page<Post> all = postRepository.findAll(pageable);
//        List<Post> posts = all.getContent();
//        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//        return dtos;

//-----------------------------PostResponse----------------------------------------------------------------------------------------------------
        @Override
        public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
           Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
           Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
           Page<Post> all = postRepository.findAll(pageable);
           List<Post> posts = all.getContent();
     //---------using stream lambda expression-----------------------------------------------------------------------
            List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

            PostResponse postResponse = new PostResponse();
            postResponse.setContent(dtos);
            postResponse.setPageNo(all.getNumber());
            postResponse.setPageSize(all.getSize());
            postResponse.setTotalPages(all.getTotalPages());
            postResponse.setTotalElements(all.getTotalElements());
            postResponse.setLast(all.isLast());
            return postResponse;

            //--------------Mapper-------------------------------------------------------------



//--------------------------Validation---------------------------------------------------------------------------------------------------




//--------------------Using stream Method Reference--------------------------------------------------------------------------------
//        List<PostDto> dtos=posts.stream().map(this::mapToDto).collect(Collectors.toList());
//        return dtos;

//---------------------Stream API chain with loop---------------------------------------------------------------------------------
//         List<PostDto> list = new ArrayList<>();
//         for (Post post : posts) {
//         PostDto dto = mapToDto(post);
//         list.add(dto);
//         }
//         return list;

  }
}
