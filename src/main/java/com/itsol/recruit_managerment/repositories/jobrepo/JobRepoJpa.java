package com.itsol.recruit_managerment.repositories.jobrepo;

import com.itsol.recruit_managerment.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepoJpa extends JpaRepository<Jobs, Long> {

}
