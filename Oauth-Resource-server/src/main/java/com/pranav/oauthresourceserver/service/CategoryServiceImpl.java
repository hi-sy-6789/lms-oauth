package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.CategoryModel;
import com.pranav.oauthresourceserver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${spring.message.personal.success}")
    String successMessage;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category addCategory(CategoryModel categoryModel) {
        Category category= new Category();
        category.setName(categoryModel.getName());
        category.setNotes(categoryModel.getNotes());
        category.setShortName(categoryModel.getShortName());
        category.setCreateDate(new Date());
        categoryRepository.save(category);
        return category;

    }

    @Override
    public String editCategory(Long id, CategoryModel categoryModel) {
        Optional<Category> optionalCategory= categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
           return "Invalid Category Id";
        }
        Category category
                = optionalCategory.get();
        category.setName(categoryModel.getName());
        category.setNotes(categoryModel.getNotes());
        category.setShortName(categoryModel.getShortName());
        categoryRepository.save(category);
        return successMessage;
    }

    @Override
    public String removeCategory(Long id) {
        Optional<Category> optionalCategory= categoryRepository.findById(id);
        if(!optionalCategory.isPresent())return "Invalid Category Id";
        if(optionalCategory.get().getBooks().size()>0)return "Category in use";
        categoryRepository.delete(optionalCategory.get());
        return successMessage;
    }

    @Override
    public Long getTotalCount() {
        return categoryRepository.count();
    }

}
