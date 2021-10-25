package com.miro.userstory.beans;

import java.time.LocalDate;
import lombok.Data;

@Data
public class WorkExperienceBean {
  private String status;
  private LocalDate dateEnd;
}
