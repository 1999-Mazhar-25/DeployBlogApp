package com.mazhar.blogs.app.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class CategoryDto {

    private Integer categoryId;

    @NotEmpty(message ="Category title cannot be empty")
    private String categoryTitle;

    @NotEmpty(message ="Category title cannot be empty")
    @Size(min = 4 , message = "Enter minimum 4 charachters")
    private String categoryDescription;
}
