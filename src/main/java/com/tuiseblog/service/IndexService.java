package com.tuiseblog.service;

import com.tuiseblog.po.Blog;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.Type;
import com.tuiseblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 王东升
 * @create 2019/8/21--18:56
 */
public interface IndexService {
    Page<Blog> listBlog(Pageable pageable);
    Blog findById(Long id);
    Page<Blog> findByType(Type type);
    Page<Blog> findByTag(Tag tag);
    List<Blog> findNewBlog();
    List<Type> listTypeTop(Integer size);
    List<Tag> listTagTop(Integer size);
    List<Blog> listRecommendBlogTop(Integer size);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(String query,Pageable pageable);
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    Map<String,List<Blog>> archiveBlog();
    Long countBlog();
}
