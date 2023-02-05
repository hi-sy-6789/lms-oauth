package com.pranav.oauthresourceserver.service;

import com.pranav.oauthresourceserver.entity.Category;
import com.pranav.oauthresourceserver.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category get(Long id);

    Category addCategory(CategoryModel categoryModel);

    String editCategory(Long id, CategoryModel categoryModel);

    String removeCategory(Long id);

    Long getTotalCount();
}
