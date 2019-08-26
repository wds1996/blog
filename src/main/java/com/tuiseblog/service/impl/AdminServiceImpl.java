package com.tuiseblog.service.impl;
import com.tuiseblog.dao.TagRepository;
import com.tuiseblog.dao.TypeRepository;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.Type;
import com.tuiseblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王东升
 * @create 2019/8/17--17:21
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    TagRepository tagRepository;
    @Transactional
    @Override
    public Tag addtag(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Type addtype(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type updateType(Type type) {
        return typeRepository.save(type);
    }
    @Override
    public Type findTypeById(Long id) {
        return typeRepository.findById(id).orElse(null);
    }
    @Override
    public Tag findTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

}
