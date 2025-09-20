package com.app.ecom_application.controller;

import com.app.ecom_application.dto.UserDtoRequest;
import com.app.ecom_application.dto.UserDtoResponse;
import com.app.ecom_application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
private final UserService userService;
  //  private List<UserEntity> userEntityList = new ArrayList<>();
  private Long counter = 1L;
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/api/users")
//    public List<UserEntity> getUserEntityList() {
//        return userService.userEntityList();
//    }

   @PostMapping("/create")
    public UserDtoRequest createUser(@RequestBody UserDtoRequest userDtoRequest) {
        return userService.createUser(userDtoRequest);
   }
   @GetMapping("/api/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
      return    new ResponseEntity<>(userService.findById(id), HttpStatus.OK);

   }
   @GetMapping("/all")
   public ResponseEntity<List<UserDtoResponse>> getAllUserEntity() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
   }

   @PutMapping("/update/{id}")
   public ResponseEntity<UserDtoResponse> updateUser(@PathVariable Long id, @RequestBody UserDtoRequest userEntity) {
 boolean updatedUser = userService.updateUser(userEntity, id);
 if(updatedUser) {
     return new ResponseEntity<>(HttpStatus.OK);
 }
 else{
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }
   }
}
