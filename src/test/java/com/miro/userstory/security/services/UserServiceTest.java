package com.miro.userstory.security.services;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.miro.userstory.security.beans.UserBean;
import com.miro.userstory.security.entities.User;
import com.miro.userstory.security.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("dev")
class UserServiceTest {

  private static final Integer SUPERHIK_USER_ID = 1;
  private static final Integer HMETAL_USER_ID = 2;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Test
  void testGetAllUsers() {
    assertThat(userService.allUsers().get().size() > 0);
  }

  @Test
  void testCreateUserWithDescription() {
    UserBean userBean =
        UserBean.builder()
            .username("Jimi")
            .firstName("Jimi")
            .lastName("Hendrix")
            .age(27)
            .description("The one and only")
            .build();
    userService.createUserWithDescription(userBean);
    assertThat(userRepository.findByUsername(userBean.getUsername()).isPresent()).isTrue();
  }

  @Test
  void testCreateUserWithoutDescription() {
    UserBean userBean =
        UserBean.builder()
            .username("Jim")
            .firstName("Jim")
            .lastName("Morrison")
            .age(20)
            .build();
    userService.createUserWithoutDescription(userBean);
    assertThat(userRepository.findByUsername(userBean.getUsername()).isPresent()).isTrue();
  }

  @Test
  void testUpdateUser() {
    UserBean userBean = UserBean.builder().age(30).build();
    userService.updateUser(userBean, SUPERHIK_USER_ID);
    Optional<User> superhik = userRepository.findById(SUPERHIK_USER_ID);
    assertThat(superhik.get().getAge()).isEqualTo(30);
  }

  @Test
  void testDeleteUser() {
    userService.deleteUser(SUPERHIK_USER_ID);
    assertThrows(NoSuchElementException.class, () -> userRepository.findById(SUPERHIK_USER_ID).get());
  }

  @Test
  void testAddNewUser() {
    UserBean userBean =
        UserBean.builder()
            .username("Charlie")
            .firstName("Charlie")
            .lastName("Parker")
            .age(36)
            .description("King of BeBop")
            .build();
    userService.createUserWithDescription(userBean);
    assertThat(userRepository.findByUsername(userBean.getUsername()).isPresent()).isTrue();
  }

  @Test
  void testUpdateSuperhikUser() {
    UserBean userBean = UserBean.builder().username("superhik the man").build();
    userService.updateUser(userBean, SUPERHIK_USER_ID);
    assertThat(userRepository.findByUsername("superhik the man").get().getFirstName()).isEqualTo("Bluff");
  }

  @Test
  void testDeletehMetalUser() {
    userService.deleteUser(HMETAL_USER_ID);
    assertThrows(NoSuchElementException.class, () -> userRepository.findById(HMETAL_USER_ID).get());
  }
}