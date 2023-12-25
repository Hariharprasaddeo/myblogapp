package com.myblog.service;

import com.myblog.dto.CommentDto;
import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

// Pagination is used to display a large number of records in different parts.
// In such case, we display 10, 20 or 50 records in one page. For remaining records, we provide links.
// Page number values start with 0. So in UI, if you are displaying page number from 1,
// then do not forget to subtracting '1' while fetching records.
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private CommentRepository commentRepository;
    PostRepository postRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentDto saveComment(CommentDto dto, long postId) {

       Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFound("Post not found with id: "+postId)
        );
        Comment comment = mapToDto(dto);
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setBody(dto.getBody());
//        comment.setEmail(dto.getEmail());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setBody(savedComment.getBody());
        commentDto.setEmail(savedComment.getEmail());

        return commentDto;
    }
//---------------ModelMapper----> used to get and set all the entity or dto class data member in a single line
    public Comment mapToDto(CommentDto dto){
        Comment comment = modelMapper.map(dto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setBody(dto.getBody());
//        comment.setEmail(dto.getEmail());
          return comment;
    }

    @Override
    public void deleteByCommentId(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateByCommentId(CommentDto commentDto, long commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Comment is not found for id: "+commentId)
        );

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = new CommentDto();
        dto.setName(savedComment.getName());
        dto.setBody(savedComment.getBody());
        dto.setEmail(savedComment.getEmail());
        return dto;
    }
}
