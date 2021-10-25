package com.miro.userstory;

import com.miro.userstory.security.entities.Role;
import com.miro.userstory.security.entities.User;
import com.miro.userstory.security.repositories.RoleRepository;
import com.miro.userstory.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class Seeder {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${properties.password}")
  private String password;

  @Value("${properties.username}")
  private String username;

  @EventListener(ApplicationStartedEvent.class)
  @Transactional
  public void seed() throws IOException {
    Optional<Role> roleUser = roleRepository.findByRoleName("ROLE_USER");
    if (roleUser.isEmpty()) {
      Role userRole = new Role();
      userRole.setRoleName("ROLE_USER");
      userRole.setDescription("general user");
      roleRepository.save(userRole);
      User user =
          User.builder()
              .username(username)
              .firstName("John")
              .lastName("Scofield")
              .age(66)
              .roles(Arrays.asList(userRole))
              .password(passwordEncoder.encode(password))
              .build();
      userRepository.save(user);
      }
  }
}
