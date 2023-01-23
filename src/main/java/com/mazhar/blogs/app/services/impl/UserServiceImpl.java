package com.mazhar.blogs.app.services.impl;

import com.mazhar.blogs.app.config.AppConstants;
import com.mazhar.blogs.app.entities.Roles;
import com.mazhar.blogs.app.entities.User;
import com.mazhar.blogs.app.exceptions.ResourceNotFoundException;
import com.mazhar.blogs.app.payloads.UserDto;
import com.mazhar.blogs.app.payloads.UserResponse;
import com.mazhar.blogs.app.repositories.RolesRepo;
import com.mazhar.blogs.app.repositories.UserRepo;
import com.mazhar.blogs.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepo rolesRepo;


    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user =this.modelMapper.map(userDto,User.class);
        //encode the password
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        //set roles by default there are only two roles
        //setting all user havin a user role by default
        Roles roles =this.rolesRepo.findById(AppConstants.ROLE_NORMAL).get();
        //adding role to a set method(we cannot add directly using setRoles method

        user.getRoles().add(roles);
       User newUser = this.userRepo.save(user);

       return this.modelMapper.map(newUser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = userRepo.save(user);
         return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = userRepo.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);

    }

    @Override
    public UserDto getUserById(Integer userId) {
      User user =  this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
      return this.userToDto(user);

    }

    @Override
    public UserResponse getAllUser(Integer pageNumber, Integer pageSize,
                                   String sortBy, String sortDir) {

        /*Sort sort =null;
        if(sortDir.equalsIgnoreCase("asc")){
             sort = Sort.by(sortBy).ascending();
        }
        else{
             sort = Sort.by(sortBy).descending();
        }*/
        //By using ternary operator
        Sort sort= (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
       Page<User> usersPage = this.userRepo.findAll(p);
        List<User> users = usersPage.getContent();
        List<UserDto> userDto = users.stream()
                .map(user -> this.userToDto(user)).collect(Collectors.toList());
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDto);
        userResponse.setPageNumber(usersPage.getNumber());
        userResponse.setPageSize(usersPage.getSize());
        userResponse.setTotalPages(usersPage.getTotalPages());
        userResponse.setTotalElement(usersPage.getTotalElements());
        userResponse.setLastPage(usersPage.isLast());
        return userResponse;
    }

    @Override
    public void deleteUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("user","Id",userId));
        this.userRepo.delete(user);

    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);

        /*user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());*/
        return user;
    }

    private UserDto userToDto(User user){

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        /*userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());*/
        return userDto;
    }
}
