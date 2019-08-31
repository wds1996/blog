package com.tuiseblog.controller;

import com.tuiseblog.po.Blog;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.Type;
import com.tuiseblog.po.User;
import com.tuiseblog.service.AdminService;
import com.tuiseblog.service.BlogService;
import com.tuiseblog.service.IndexService;
import com.tuiseblog.service.UserService;
import com.tuiseblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/14--13:03
 */
@Controller
public class IndexController {
    @Autowired
    IndexService indexService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @GetMapping("/")
    public String index(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable,Model model){
        //System.out.println("111111");
        Page<Blog> page = indexService.listBlog(pageable);
        model.addAttribute("page",page);
        model.addAttribute("types",indexService.listTypeTop(6));
        model.addAttribute("tags",indexService.listTagTop(12));
        model.addAttribute("recommends",indexService.listRecommendBlogTop(6));
        return "index";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", indexService.getAndConvert(id));
        return "blog";
    }
    @RequestMapping("/search")
    public String blog(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                           @RequestParam String query,Pageable pageable, Model model){
        model.addAttribute("page", indexService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model) {
        List<Tag> tags = indexService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", indexService.listBlog(id,pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Type> types = indexService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", indexService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
    @GetMapping("/history")
    public String archives(Model model) {
        model.addAttribute("archiveMap", indexService.archiveBlog());
        model.addAttribute("blogCount", indexService.countBlog());
        return "history";
    }
    @GetMapping("/about")
    public String about(Model model){
        User tuise = userService.findByName("tuise");
        if (tuise==null){
            tuise = new User();
        }
        model.addAttribute("picture",tuise.getPicture());
        String[]  userlike=tuise.getUserlike().split(" ");
        model.addAttribute("userlike",userlike);
        String[]  usertag=tuise.getUsertag().split(" ");
        model.addAttribute("usertag",usertag);
        String[]  userinfo=tuise.getUserinfo().split("\n");
        model.addAttribute("userinfo",userinfo);
        return "about";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", indexService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
