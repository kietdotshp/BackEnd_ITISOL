package com.itsol.recruit_managerment.repositories;

import com.itsol.recruit_managerment.model.AcademicLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicLevelRepo extends JpaRepository<AcademicLevel,Long> {
//    AcademicLevel findByacAdemicName(String name);
}
