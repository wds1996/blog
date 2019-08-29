package com.tuiseblog.service.impl;
import com.tuiseblog.NotFoundException;
import com.tuiseblog.dao.BlogRepository;
import com.tuiseblog.po.Blog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import com.tuiseblog.po.Type;
import com.tuiseblog.service.BlogService;
import com.tuiseblog.util.MyBeanUtils;
import com.tuiseblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/18--17:03
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Transactional
    @Override
    public Blog addBlog(Blog blog) {
        if (blog.getId() == null) {
            if (blog.getFlag()==null||"".equals(blog.getFlag())){
                blog.setFlag("原创");
            }
            if (blog.getUpdateTime()==null||"".equals(blog.getUpdateTime())){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                blog.setUpdateTime(formatter.format(new Date()));
            }
            blog.setCreateTime(blog.getUpdateTime());
            blog.setViews((int)(Math.random() * 100) + 1);
        } else {
            blog.setUpdateTime(blog.getCreateTime());
        }
        return blogRepository.save(blog);
    }
    @Override
    public Page<Blog> listBlog(Pageable pageable,BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    @Transactional
    @Override
    public Blog updateBlog(Blog blog) {
        Blog b = blogRepository.getOne(blog.getId());
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }
    @Override
    public Blog findById(Long id) {
        return blogRepository.getOne(id);
    }
    @Transactional
    @Override
    public void deletBlog(Long id) {
        blogRepository.deleteById(id);
    }

}
