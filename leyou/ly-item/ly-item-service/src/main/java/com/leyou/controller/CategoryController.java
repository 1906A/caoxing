package com.leyou.controller;

import com.leyou.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> list(@RequestParam Long pid){
        Category category=new Category();
        category.setParentId(pid);
        List<Category> list = categoryService.findAll(category);
        return list;
    }

    @RequestMapping("addCategory")
    public String addCategory(@RequestBody Category category){
        String res="SUCC";
        try {
            categoryService.addCategory(category);
        }catch(Exception e) {
            e.printStackTrace();
            res="FAIL";
        }
        return res;
    }

    @RequestMapping("updateCategory")
    public String updateCategory(@RequestBody Category category){
        String res="SUCC";
        try {
            categoryService.updateCategory(category);
        }catch(Exception e) {
            e.printStackTrace();
            res="FAIL";
        }
        return res;
    }

    @RequestMapping(method = RequestMethod.GET,value = "deleteCategory")
    public String deleteCategory(@RequestParam Integer id){
        String res="SUCC";
        try {
            categoryService.deleteCategory(id);
        }catch(Exception e) {
            e.printStackTrace();
            res="FAIL";
        }
        return res;
    }

    @RequestMapping("findCategoryById")
    public Category findCategoryById(@RequestParam("id") long id){
        return categoryService.findCategoryById(id);
    }

}
