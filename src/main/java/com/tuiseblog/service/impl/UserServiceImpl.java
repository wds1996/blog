package com.tuiseblog.service.impl;

import com.tuiseblog.dao.UserRepository;
import com.tuiseblog.po.User;
import com.tuiseblog.service.UserService;
import com.tuiseblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王东升
 * @create 2019/8/15--10:58
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userDao;
    @Override
    public User userLogin(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
