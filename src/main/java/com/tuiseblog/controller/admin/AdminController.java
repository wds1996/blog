package com.tuiseblog.controller.admin;

import com.tuiseblog.NotFoundException;
import com.tuiseblog.po.Tag;
import com.tuiseblog.po.Type;
import com.tuiseblog.service.AdminService;
import com.tuiseblog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author 王东升
 * @create 2019/8/15--15:57
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("/addtag")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = adminService.getTagByName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复标签");
            return "redirect:/admin/taglist";
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "请按要求提交");
            return "redirect:/admin/taglist";
        }
        Tag addtag = adminService.addtag(tag);
        if (addtag == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/taglist";
    }
    @RequestMapping("/addtype")
    public String addType(@Valid Type type,BindingResult result, RedirectAttributes attributes){
        Type type1 = adminService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复类型");
            return "redirect:/admin/typelist";
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "请按要求提交");
            return "redirect:/admin/typelist";
        }
        Type addtype = adminService.addtype(type);
        if (addtype==null){
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/typelist";
    }
    @RequestMapping("/updatetype")
    public String updateType(@Valid Type type,BindingResult result, RedirectAttributes attributes){
        Type type1 = adminService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复类型");
            return "redirect:/admin/typelist";
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "请按要求提交");
            return "redirect:/admin/typelist";
        }
        Type type2 = adminService.findTypeById(type.getId());
        if (type2==null){
            throw new  NotFoundException("该类型已不存在！");
        }
        BeanUtils.copyProperties(type,type2,MyBeanUtils.getNullPropertyNames(type));
        Type updateType = adminService.updateType(type2);
        if (updateType == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/typelist";
    }
    @RequestMapping("/updatetag")
    public String updateTag(@Valid Tag tag,BindingResult result, RedirectAttributes attributes){
        Tag tag1 = adminService.getTagByName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复标签");
            return "redirect:/admin/taglist";
        }
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "请按要求提交");
            return "redirect:/admin/taglist";
        }
        Tag tag2 = adminService.findTagById(tag.getId());
        if (tag2==null){
            throw new  NotFoundException("该标签已不存在！");
        }
        BeanUtils.copyProperties(tag,tag2);
        Tag updateTag = adminService.updateTag(tag2);
        if (updateTag == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/taglist";
    }
    //标签管理页面
    @GetMapping("/taglist")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page",adminService.listTag(pageable));
        return "admin/taglist";
    }
    //  类型管理页面
    @GetMapping("/typelist")
    public String typeList(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model) {
        model.addAttribute("page",adminService.listType(pageable));
        return "admin/typelist";
    }
    @GetMapping("/delettag")
    public String deletTag(Long id,RedirectAttributes attributes){
        adminService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/taglist";
    }
    @GetMapping("/delettype")
    public String deletType(Long id,RedirectAttributes attributes){
        adminService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/typelist";
    }

}
