package com.mazhar.blogs.app.services;

import com.mazhar.blogs.app.payloads.CategoryDto;
import com.mazhar.blogs.app.payloads.CategoryReponse;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto categoryDto);


    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //get by id
    CategoryDto getCategoryById(Integer categoryId);

    //get all
    CategoryReponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    //delete

    void deleteCategoryById(Integer categoryId);
}
