package com.miro.userstory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperience extends JpaRepository<WorkExperience, Integer> {

}
