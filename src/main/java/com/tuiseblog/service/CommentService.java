package com.tuiseblog.service;

import com.tuiseblog.po.Comment;

import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/24--22:12
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
