package com.mazhar.blogs.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty(message="name cannot be empty nor blank")
    @Size(min = 2, max = 20)
    private String name;

    @Email(message = "Enter a valid email address")
    private String email;

    @NotEmpty
    @Min(4)
//    @JsonIgnore - this property will ignore the password field from userDto
    private String password;

    @NotEmpty(message="Something to be written in about")
    private String about;

    private Set<RolesDto> roles = new HashSet<>();

}
