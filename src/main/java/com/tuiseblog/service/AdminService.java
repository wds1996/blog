package com.tuiseblog.service;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/17--17:19
 */
public interface AdminService {
    Tag addtag(Tag tag);
    Type addtype(Type type);
    Type updateType(Type type);
    Type findTypeById(Long id);
    Tag findTagById(Long id);
    Tag updateTag(Tag tag);
    Page<Type> listType(Pageable pageable);
    void deleteType(Long id);
    Page<Tag> listTag(Pageable pageable);
    void deleteTag(Long id);
    List<Type> listType();
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    Tag getTagByName(String name);
    Type getTypeByName(String name);

}
