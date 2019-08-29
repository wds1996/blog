package com.tuiseblog.po;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/15--14:21
 */
@Entity
@Table(name = "t_type")
public class Type {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //分类的名称
    @NotBlank
    private String name;
    //多个博客对应一个类型
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
