package com.mazhar.blogs.app.controllers;

import com.mazhar.blogs.app.config.AppConstants;
import com.mazhar.blogs.app.payloads.CategoryDto;
import com.mazhar.blogs.app.payloads.CategoryReponse;
import com.mazhar.blogs.app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //POST - create category
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
       CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
       return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }


    //PUT - update category
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                      @PathVariable("categoryId") Integer catId){
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(categoryDto1);

    }

    //GET  - get all categories
    @GetMapping("/getAllCategories")
    public ResponseEntity<CategoryReponse> getAllCategories(
            @RequestParam(value="pageNumber",required = false,
                    defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false,
                    defaultValue =AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value ="sortBy", required = false,
                    defaultValue =AppConstants.SORT_CATEGORY_BY) String sortBy,
            @RequestParam(value ="sortDir", required = false,
                    defaultValue =AppConstants.SORT_DIR) String sortDir

            ){
        CategoryReponse categoryList = this.categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDir);
       return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }


    //GET - get category by id
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Integer catId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(catId);
        return ResponseEntity.ok(categoryDto);
    }

    //DELETE -delete category

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") Integer catId){
        this.categoryService.deleteCategoryById(catId);
        return ResponseEntity.ok("Category is deleted Successfully !!!");
    }
}
