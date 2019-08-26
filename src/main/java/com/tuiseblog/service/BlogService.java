package com.tuiseblog.service;
import com.tuiseblog.po.Blog;
import com.tuiseblog.po.Type;
import com.tuiseblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/18--16:55
 */
public interface BlogService {
    Blog addBlog(Blog blog);
    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);
    Blog updateBlog(Blog blog);
    Blog findById(Long id);
    void deletBlog(Long id);
}
