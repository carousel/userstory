package com.miro.userstory.security.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.miro.userstory.beans.WorkExperienceBean;
import com.miro.userstory.entities.WorkExperience;
import com.miro.userstory.security.beans.UserBean;
import com.miro.userstory.security.entities.Role;
import com.miro.userstory.security.entities.User;
import com.miro.userstory.security.repositories.RoleRepository;
import com.miro.userstory.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
  private static final Integer SUPERHIK_USER_ID = 1;
  private static final Integer HMETAL_USER_ID = 2;

  private UserRepository userRepository;

  private AuthenticationManager authenticationManager;

  private RoleRepository roleRepository;

  private PasswordEncoder passwordEncoder;

  private JwtProvider jwtProvider;

  /**
   * Sign in a user into the application, with JWT-enabled authentication
   *
   * @param username username
   * @param password password
   * @return Optional of the Java Web Token, empty otherwise
   */
  public Optional<String> signin(String username, String password) {
    LOGGER.info("New user attempting to sign in");
    Optional<String> token = Optional.empty();
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
      } catch (AuthenticationException e) {
        LOGGER.info("Log in failed for user {}", username);
      }
    }
    return token;
  }

  /**
   * Create a new user in the database.
   *
   * @param username username
   * @param password password
   * @param firstName first name
   * @param lastName last name
   * @return Optional of user, empty if the user already exists.
   */
  public Optional<User> signup(
      String username, String password, String firstName, String lastName) {
    LOGGER.info("New user attempting to sign in");
    Optional<User> user = Optional.empty();
    if (!userRepository.findByUsername(username).isPresent()) {
      Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
      user =
          Optional.of(
              userRepository.save(
                  User.builder()
                      .firstName(firstName)
                      .lastName(lastName)
                      .password(passwordEncoder.encode(password))
                      .roles(Arrays.asList(role.get()))
                      .build()));
    }
    return user;
  }

  public Optional<List<User>> allUsers() {
    return Optional.of(userRepository.findAll());
  }

  @Transactional
  public User addWorkExperience(WorkExperienceBean workExperienceBean, Integer userId) {
    WorkExperience newWorkExperience = new WorkExperience();
    newWorkExperience.setStatus(workExperienceBean.getStatus());
    newWorkExperience.setDateEnd(workExperienceBean.getDateEnd());

    User user = userRepository.findById(userId).get();
    Set<WorkExperience> experiences = user.getWorkExperience();
    experiences.add(newWorkExperience);
    user.setWorkExperience(experiences);
    return userRepository.save(user);
  }

  /**
   * Eager loaded user with work experience
   * added here in security layer, but should be refactored to follow application logic
   *
   * @param userId
   * @return
   */
  public Optional<User> withWorkExperience(Integer userId) {
    return userRepository.findById(userId);
  }

  @Transactional
  public User createUserWithDescription(UserBean userBean) {
    User user =
        User.builder()
            .username(userBean.getUsername())
            .firstName(userBean.getFirstName())
            .lastName(userBean.getLastName())
            .age(userBean.getAge())
            .description(userBean.getDescription())
            .build();
    return userRepository.save(user);
  }

  @Transactional
  public User createUserWithoutDescription(UserBean userBean) {
    User user =
        User.builder()
            .username(userBean.getUsername())
            .firstName(userBean.getFirstName())
            .lastName(userBean.getLastName())
            .age(userBean.getAge())
            .build();
    return userRepository.save(user);
  }

  @Transactional
  public User updateUser(UserBean userBean, Integer userId) {
    User user = mapUser(userBean, userId);
    return userRepository.save(user);
  }

  private User mapUser(UserBean userBean, Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      if (userBean.getUsername() != null) {
        user.get().setUsername(userBean.getUsername());
      } else {
        user.get().setUsername(user.get().getUsername());
      }
      if (userBean.getAge() != null) {
        user.get().setAge(userBean.getAge());
      } else {
        user.get().setAge(user.get().getAge());
      }
      if (userBean.getFirstName() != null) {
        user.get().setFirstName(userBean.getFirstName());
      } else {
        user.get().setFirstName(user.get().getFirstName());
      }
      if (userBean.getLastName() != null) {
        user.get().setLastName(userBean.getLastName());
      } else {
        user.get().setLastName(user.get().getLastName());
      }
      if (userBean.getDescription() != null) {
        user.get().setDescription(userBean.getDescription());
      } else {
        user.get().setDescription(user.get().getDescription());
      }
    }
    return user.get();
  }

  @Transactional
  public void deleteUser(Integer userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }
  }

  @Transactional
  public User addNewUser(UserBean userBean) {
    User user =
        User.builder()
            .username(userBean.getUsername())
            .firstName(userBean.getFirstName())
            .lastName(userBean.getLastName())
            .age(userBean.getAge())
            .description(userBean.getDescription())
            .build();
    return userRepository.save(user);
  }

  @Transactional
  public User updateSuperhikUser(UserBean userBean) {
    Integer superhikId = userRepository.findById(SUPERHIK_USER_ID).get().getId();
    return userRepository.save(mapUser(userBean, superhikId));
  }

  @Transactional
  public List<User> deletehMetalUser() {
    User hmetalUser = userRepository.findById(HMETAL_USER_ID).get();
    userRepository.delete(hmetalUser);
    return userRepository.findAll();
  }
}
