package com.tuiseblog.service;

import com.tuiseblog.po.User;
import org.springframework.stereotype.Service;

/**
 * @author 王东升
 * @create 2019/8/15--10:55
 */
public interface UserService {
    User userLogin(String username,String password);
    User updateUser(User user);
    int updatePassword(User user);
    User findByName(String name);
}
