package com.mazhar.blogs.app.controllers;

import com.mazhar.blogs.app.config.AppConstants;
import com.mazhar.blogs.app.payloads.ApiResponse;
import com.mazhar.blogs.app.payloads.UserDto;
import com.mazhar.blogs.app.payloads.UserResponse;
import com.mazhar.blogs.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


   // POST - create user

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = this.userService.createUser(userDto);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //PUT - update user

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") Integer uid,
                                              @RequestBody UserDto userDto){
       UserDto userDto1 =  this.userService.updateUser(userDto,uid);
//       return new ResponseEntity<>(userDto1 ,HttpStatus.ACCEPTED)
        return ResponseEntity.ok(userDto1);
    }

    //DELETE - delete user by Id
//    Only  user has the authority to access this method,here preautorize is case sensitive
    //and also in DB u need to provide roles name as "ROLE_ADMIN"
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") Integer uid){
        this.userService.deleteUserById(uid);
        return new ResponseEntity<ApiResponse>
                (new ApiResponse("DELETED SUCCESSFULLY",true),HttpStatus.CREATED);
    }


    //GET - get user by Id

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uid){
       UserDto userDto = this.userService.getUserById(uid);
       return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    //GET - get All users

    @GetMapping("/getAllUsers")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,
                    required=false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE,
                    required=false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue=AppConstants.SORT_USER_BY,
                    required=false) String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue=AppConstants.SORT_DIR,
                    required=false) String sortDir
    ){
        UserResponse users = this.userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<UserResponse>(users,HttpStatus.OK);
    }

}
