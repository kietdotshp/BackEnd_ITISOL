package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.AcademicLevel;

public interface AcademicLevelService {
    AcademicLevel findById(Long id);

    AcademicLevel findAcademic_nameById(String academicName);

    public AcademicLevel save(AcademicLevel academicLevel);
}
