package com.mazhar.blogs.app.services.impl;

import com.mazhar.blogs.app.entities.Category;
import com.mazhar.blogs.app.exceptions.ResourceNotFoundException;
import com.mazhar.blogs.app.payloads.CategoryDto;
import com.mazhar.blogs.app.payloads.CategoryReponse;
import com.mazhar.blogs.app.repositories.CategoryRepo;
import com.mazhar.blogs.app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("category","CategoryId",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);


    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {

      Category category =  this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Category","CategoryId",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryReponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> catPage = this.categoryRepo.findAll(p);
        List<Category> categories = catPage.getContent();
       List<CategoryDto> categoryDtoList = categories.stream()
                .map(category ->this.modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());

       CategoryReponse categoryReponse = new CategoryReponse();
       categoryReponse.setContent(categoryDtoList);
       categoryReponse.setPageNumber(catPage.getNumber());
       categoryReponse.setPageSize(catPage.getSize());
       categoryReponse.setTotalPages(catPage.getTotalPages());
       categoryReponse.setTotalElement(catPage.getTotalElements());
       categoryReponse.setLastPage(catPage.isLast());

        return categoryReponse;
    }

    @Override
    public void deleteCategoryById(Integer categoryId) {
        Category category =  this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        this.categoryRepo.delete(category);
    }


}
