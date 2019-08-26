package com.tuiseblog.controller.admin;
import com.tuiseblog.NotFoundException;
import com.tuiseblog.po.Blog;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.User;
import com.tuiseblog.service.AdminService;
import com.tuiseblog.service.BlogService;
import com.tuiseblog.util.MyBeanUtils;
import com.tuiseblog.vo.BlogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/18--16:54
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    BlogService blogService;
    @Autowired
    AdminService adminService;
    //  返回博客发布页面
    @GetMapping("/addblogpage")
    public String addblogPage(Long id,Model model){
        if (id!=null){
            Blog blog = blogService.findById(id);
            blog.init();
           // System.out.println("这是ids字符串"+blog.getTagIds());
            model.addAttribute("blog",blog);
        }else {
            model.addAttribute("blog",new Blog());
        }
        model.addAttribute("types",adminService.listType());
        model.addAttribute("tags",adminService.listTag());
        return "admin/addblog";
    }
    //  返回博客列表页面
    @GetMapping("/bloglist")
    public String bloglistPage(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                           Pageable pageable, Model model,BlogQuery blogQuery){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types",adminService.listType());
        return "admin/bloglist";
    }
    @RequestMapping("/bloglist/search")
    public String bloglisSearch(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model,BlogQuery blogQuery){
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return "admin/bloglist :: blogtable";
    }
    @RequestMapping("/addblog")
    public String addBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(adminService.findTypeById(blog.getType().getId()));
        blog.setTags(adminService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.addBlog(blog);
        } else {
            b = blogService.updateBlog(blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/bloglist";
    }
    @GetMapping("/deletblog")
    public String deletBlog(Long id){
        blogService.deletBlog(id);
        return "redirect:/admin/bloglist";
    }

}
