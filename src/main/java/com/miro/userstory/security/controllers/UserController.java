package com.miro.userstory.security.controllers;

import javax.validation.Valid;
import com.miro.userstory.beans.WorkExperienceBean;
import com.miro.userstory.security.beans.LoginBean;
import com.miro.userstory.security.beans.SignupBean;
import com.miro.userstory.security.beans.UserBean;
import com.miro.userstory.security.entities.User;
import com.miro.userstory.security.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired private UserService userService;

  @PostMapping("/signin")
  public String signin(@RequestBody @Valid LoginBean loginBean) {
    return userService.signin(loginBean.getUsername(), loginBean.getPassword()).orElseThrow(() ->
        new HttpServerErrorException(HttpStatus.FORBIDDEN, "login failed, please try again"));
  }

  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public User signup(@Valid @RequestBody SignupBean signupBean) {
    return userService.signup(signupBean.getUsername(), signupBean.getPassword(), signupBean.getFirstName(),
        signupBean.getLastName()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
  }


  @PostMapping("/add-work-experience/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> postAddWorkExperience(@RequestBody WorkExperienceBean workExperienceBean, @PathVariable String userId) {
    Integer id = Integer.parseInt(userId);
    return  ResponseEntity.ok(userService.addWorkExperience(workExperienceBean, id));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.of(userService.allUsers());
  }

  @PostMapping("with-description")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> postCreateUserWithDescription(@RequestBody UserBean userBean) {
    User user = userService.createUserWithDescription(userBean);
    return ResponseEntity.ok(user);
  }

  @PostMapping("without-description")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> postCreateUserWithoutDescription(@RequestBody UserBean userBean) {
    User user = userService.createUserWithDescription(userBean);
    return ResponseEntity.ok(user);
  }

  @PutMapping("{userId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> putUpdateUser(
      @RequestBody UserBean userBean, @PathVariable String userId) {
    User user = userService.updateUser(userBean, Integer.parseInt(userId));
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("{userId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<String> deleteUser(@PathVariable String userId) {
    userService.deleteUser(Integer.parseInt(userId));
    return ResponseEntity.ok("User deleted!");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> postCreateUser(@RequestBody UserBean userBean) {
    User user = userService.addNewUser(userBean);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/superhik")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<User> putUpdateSuperhikUser(@RequestBody UserBean userBean) {
    User user = userService.updateSuperhikUser(userBean);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/hmetal")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<String> deleteHmetalUser() {
    userService.deletehMetalUser();
    return ResponseEntity.ok("Hmetal user deleted!");
  }
}
