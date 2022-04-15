package com.itsol.recruit_managerment.repositories.jobRegisterRp;

import com.itsol.recruit_managerment.model.JobsRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRegisterRepositoryJpa extends JpaRepository<JobsRegister, Integer> {
    
    JobsRegister findById(int id);

//    @Query(value = "from JobsRegister jr join Users u on u.id = jr.user.id where u.fullName like %:fullName%")
//    List<JobsRegister> findByFullName( String fullName);

//    @Query(value = "from JobsRegister jr where to_char(jr.applicationTime,'dd-MM-yyyy') like to_char(concat(concat('%', :time), '%'))")
//    List<JobsRegister> findByApplicationTime(String time);

//    @Query(value = "from JobsRegister jr join Jobs j on j.id = jr.user.id where j.jobPosition like %:vacancies%")
//    List<JobsRegister> findByVacancies( String vacancies);

}
