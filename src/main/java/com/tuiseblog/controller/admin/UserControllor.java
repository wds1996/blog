package com.tuiseblog.controller.admin;

import com.tuiseblog.po.User;
import com.tuiseblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 王东升
 * @create 2019/8/15--11:08
 */
@Controller
@RequestMapping("/admin")
public class UserControllor {
    @Autowired
    private UserService userService;
    //  返回登陆页面
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    //    用户登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes redirectAttributes){
        User user = userService.userLogin(username, password);
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/admin/bloglist";
        }else {
            redirectAttributes.addFlashAttribute("errormessage","对不起，你输入的账户或密码不正确，请重新输入！");
            return "redirect:/admin";
        }
    }
    //    用户注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
