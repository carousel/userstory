package com.miro.userstory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "properties")
@Data
@Component
public class Properties {
  private String jwtSecret;
  private Long jwtExpiration;
  private String password;
  private String username;
}
