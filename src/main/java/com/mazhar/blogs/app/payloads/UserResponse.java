package com.mazhar.blogs.app.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private List<UserDto> content;

    private int pageNumber;

    private int pageSize;

    private Long totalElement;

    private int totalPages;

    private boolean lastPage;
}
