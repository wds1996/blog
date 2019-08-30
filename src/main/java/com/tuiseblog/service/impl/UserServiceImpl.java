package com.tuiseblog.service.impl;

import com.tuiseblog.NotFoundException;
import com.tuiseblog.dao.UserRepository;
import com.tuiseblog.po.User;
import com.tuiseblog.service.UserService;
import com.tuiseblog.util.MD5Utils;
import com.tuiseblog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public User updateUser(User user) {
        User u = userDao.getOne(user.getId());
        if (u==null){
            throw new NotFoundException("该用户不存在！");
        }
        BeanUtils.copyProperties(user,u, MyBeanUtils.getNullPropertyNames(user));
        return userDao.save(u);
    }

    @Transactional
    @Override
    public int updatePassword(User user) {
        return userDao.updatePassword(MD5Utils.code(user.getPassword()),user.getId());
    }

    @Override
    public User findByName(String name) {
        return userDao.findByUsername(name);
    }
}
