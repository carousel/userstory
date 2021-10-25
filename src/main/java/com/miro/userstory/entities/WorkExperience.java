package com.miro.userstory.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Data;

@Embeddable
@Data
public class WorkExperience {

  @Column(name = "status", length = 12, nullable = false)
  private String status;

  @Column(name = "date_end", length = 56, nullable = true)
  private LocalDate dateEnd;

}
