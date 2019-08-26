package com.tuiseblog.dao;

import com.tuiseblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王东升
 * @create 2019/8/15--10:59
 */
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username,String password);
}
