package com.miro.userstory.security.beans;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBean {

  private Integer userId;

  @NotNull
  @Max(value = 50, message = "Username shoud be less than 50")
  private String username;

  @NotNull
  @Max(value = 50, message = "First name should be less than 50")
  private String firstName;

  @NotNull
  @Max(value = 500, message = "Last name should be less than 500")
  private String lastName;

  @NotNull
  @Size(min = 18, max = 100, message = "Age should be between 18 and 100")
  private Integer age;

  private String description;
}
