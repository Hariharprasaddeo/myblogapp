package com.myblog.service;

import com.myblog.dto.CommentDto;

public interface CommentService {
    CommentDto saveComment(CommentDto dto, long postId);

    void deleteByCommentId(long id);

    CommentDto updateByCommentId(CommentDto commentDto, long commentId);
}
