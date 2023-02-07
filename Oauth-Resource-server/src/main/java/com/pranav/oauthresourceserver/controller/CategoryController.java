package com.pranav.oauthresourceserver.controller;

import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.CategoryModel;
import com.pranav.oauthresourceserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/category")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping (value = {"/", "/list"})
    public List<Category> all() {
        return categoryService.getAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = {"/add", "/add/"})
    public String addCategory(@RequestBody @Valid CategoryModel categoryModel){
        categoryService.addCategory(categoryModel);
        return "Success";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Long id, @RequestBody @Valid CategoryModel categoryModel) {
        return categoryService.editCategory(id, categoryModel);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/remove/{id}")
    public String removeCategory(@PathVariable(name = "id") Long id) {
        return categoryService.removeCategory(id);
    }

}
