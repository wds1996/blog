package com.tuiseblog.dao;

import com.tuiseblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 王东升
 * @create 2019/8/15--10:59
 */
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username,String password);

    @Modifying
    @Query("update User u set u.password=?1 where u.id=?2")
    int updatePassword(String password,Long id);
    User findByUsername(String name);
}
